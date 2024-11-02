package com.EaseTravels.et.services;

import com.EaseTravels.et.forms.FlightForm;
import com.amadeus.resources.FlightOfferSearch;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Map;

public interface FlightService {
    List<Map<String, String>> searchAirport(String search);
    Page<FlightOfferSearch> flights(FlightForm flightForm, int page, int size);
    boolean confirmPrice(FlightOfferSearch flightOfferSearch);
}