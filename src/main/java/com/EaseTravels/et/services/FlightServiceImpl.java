package com.EaseTravels.et.services;

import com.EaseTravels.et.forms.FlightForm;
import com.amadeus.Amadeus;
import com.amadeus.Params;
import com.amadeus.exceptions.ResponseException;
import com.amadeus.resources.FlightOfferSearch;
import com.amadeus.resources.FlightPrice;
import com.amadeus.resources.Location;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Service
@Log4j2
public class FlightServiceImpl implements FlightService {

//    @Autowired
//    private RedisTemplate<String, Object> redisTemplate;

    private final Amadeus amadeus;

    public FlightServiceImpl(@Value("${amadeus.apikey}") String apiKey,
                             @Value("${amadeus.apisecret}") String apiSecret) {
        // Initialize the Amadeus SDK with the API key and secret
        this.amadeus = Amadeus.builder(apiKey, apiSecret).build();
    }

    @Override
    @Cacheable(value = "airportSearchCache", key = "#search")
    public List<Map<String, String>> searchAirport(String search) {
        List<Map<String, String>> responseList = new ArrayList<>();

        try {
            // Use the Amadeus SDK to search for locations
            Location[] locations = amadeus.referenceData.locations.get(Params.with("keyword", search)
                    .and("subType", "AIRPORT,CITY"));

            // Convert the response to a List of Maps
            for (Location location : locations) {
                Map<String, String> responseItem = new HashMap<>();
                responseItem.put("iataCode", location.getIataCode()); // Add IATA code to the map

                // Assuming the location object contains a method to get the city name
                String cityName = location.getAddress().getCityName(); // Adjust according to your Location class structure
                responseItem.put("cityName", cityName); // Add city name to the map

                responseList.add(responseItem); // Add the map to the response list
            }

            return responseList; // Return the response as a List of Maps
        } catch (ResponseException e) {
            log.error("Error occurred while searching for airports: ", e);
            return new ArrayList<>(); // Return an empty list on error
        }
    }

    @Override
    public Page<FlightOfferSearch> flights(FlightForm flightForm, int page, int size) throws RuntimeException {
        try {
            LocalDate departureDate = LocalDate.parse(flightForm.getDepartureDate(), DateTimeFormatter.ISO_LOCAL_DATE);
            log.info("Requesting flights with parameters: origin={}, destination={}, date={}, adults={}, class={}, nonStop={}",
                    flightForm.getFrom(), flightForm.getTo(), departureDate, flightForm.getPassengerCount(),
                    flightForm.getTravelClass().name(), flightForm.isNonStop());

            FlightOfferSearch[] flightOfferSearches = amadeus.shopping.flightOffersSearch.get(
                    Params.with("originLocationCode", flightForm.getFrom())
                            .and("destinationLocationCode", flightForm.getTo())
                            .and("departureDate", departureDate.toString())
                            .and("adults", flightForm.getPassengerCount())
                            .and("travelClass", flightForm.getTravelClass().name())
                            .and("nonStop", flightForm.isNonStop())
                            .and("currencyCode", "USD")
                            .and("max", 100)
            );

            log.info("Flight offers: {}", (Object) flightOfferSearches);
            List<FlightOfferSearch> flightOfferSearchList = Arrays.asList(flightOfferSearches);

            // Set up pagination
            Pageable pageable = PageRequest.of(page, size);
            int start = Math.min((int) pageable.getOffset(), flightOfferSearchList.size());
            int end = Math.min((start + size), flightOfferSearchList.size());
            List<FlightOfferSearch> paginatedList = flightOfferSearchList.subList(start, end);

            return new PageImpl<>(paginatedList, pageable, flightOfferSearchList.size());
        } catch (ResponseException e) {
            log.error("Error occurred while fetching flight offers: ", e);
            return Page.empty();
        }
    }

    @Override
    public boolean confirmPrice(FlightOfferSearch flightOffer) {
        log.info("Entering confirmPrice method");
        try {
            log.info("Confirming price");
            FlightPrice confirmedOffer = amadeus.shopping.flightOffersSearch.pricing.post(flightOffer);
            return confirmedOffer != null;
        } catch (ResponseException e) {
            log.error("Error confirming price: ", e);
            return false;
        } finally {
            log.info("Exiting confirmPrice method");
        }
    }
}