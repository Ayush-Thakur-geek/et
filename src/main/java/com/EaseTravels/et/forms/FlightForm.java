package com.EaseTravels.et.forms;

import com.EaseTravels.et.models.types.TravelClass;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FlightForm {
    private String from;
    private String to;
    private String departureDate;
    private TravelClass travelClass;
    private int passengerCount;
    private boolean oneWay = true;
    private boolean nonStop = false;
}
