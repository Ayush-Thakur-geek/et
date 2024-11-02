package com.EaseTravels.et.controller;

import com.EaseTravels.et.forms.FlightForm;
import com.EaseTravels.et.services.FlightService;
import com.amadeus.Amadeus;
import com.amadeus.exceptions.ResponseException;
import com.amadeus.resources.FlightOfferSearch;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
@SessionAttributes("flightForm")
@RequestMapping("/user/dashboard")
public class FlightController {
    private Amadeus amadeus;

    @ModelAttribute("flightForm")
    public FlightForm populateFlightForm() {
        // Initialize FlightForm if it does not exist in the session
        return new FlightForm();
    }


    @Autowired
    private FlightService flightService;

    @GetMapping("/airportSearch")
    @ResponseBody
    public List<Map<String, String>> searchAirport(@RequestParam("term") String searchTerm) {
        return flightService.searchAirport(searchTerm);
    }


    @RequestMapping(value = "/flightOffers", method = {RequestMethod.GET, RequestMethod.POST})
    public String searchFlights(@RequestParam(value = "page", defaultValue = "0") int page,
                                @RequestParam(value = "size", defaultValue = "10") int size,
                                Model model,
                                @ModelAttribute("flightForm") FlightForm flightForm) throws JsonProcessingException {
        Page<FlightOfferSearch> flightOffers = flightService.flights(flightForm, page, size);
        if (flightOffers != null) {
            ObjectMapper mapper = new ObjectMapper();
            List<String> flightOffersJson = flightOffers.stream()
                    .map(flight -> {
                        try {
                            return mapper.writeValueAsString(flight);
                        } catch (JsonProcessingException e) {
                            e.printStackTrace();
                            return null;
                        }
                    })
                    .collect(Collectors.toList());
            model.addAttribute("flightOffers", flightOffers);
            model.addAttribute("flightOffersJson", flightOffersJson);
            model.addAttribute("pageSize", "10");
        } else {
            System.out.println("No flight offers found.");
        }

        return "user/dashboard";
    }

    @PostMapping(value = "/confirmPrice", consumes = "application/x-www-form-urlencoded; charset=UTF-8")
    public ResponseEntity<Boolean> confirmPrice(@RequestBody FlightOfferSearch flightOfferSearch) throws ResponseException {
        System.out.println("Confirming price for flight offer search: " + flightOfferSearch);
        boolean isConfirmed = flightService.confirmPrice(flightOfferSearch);
        return ResponseEntity.status(HttpStatus.OK).body(isConfirmed);
    }

}