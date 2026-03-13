package com.vehicletrading.vehicle_trading.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

/**
 * Data Transfer Object for hierarchical Location.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LocationDTO {

    private UUID id;
    private String code;
    private String locationType;
    private String name;
    private UUID locationId;
}
