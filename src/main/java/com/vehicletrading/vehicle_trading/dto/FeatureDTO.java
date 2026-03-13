package com.vehicletrading.vehicle_trading.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Data Transfer Object for Feature.
 * Represents a vehicle feature (e.g. "Sunroof", "GPS").
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FeatureDTO {

    private Long id;
    private String name;
    private String description;
}
