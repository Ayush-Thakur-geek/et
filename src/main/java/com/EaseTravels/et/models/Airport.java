package com.EaseTravels.et.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Airport {
    private String iataCode;
    private String name;
    private String city;
    private String country;
    private double latitude;
    private double longitude;
}
