package com.vehicletrading.vehicle_trading.dto;

import com.vehicletrading.vehicle_trading.entity.Vehicle.VehicleStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Set;

/**
 * Data Transfer Object for Vehicle.
 * Contains vehicle details and the list of associated feature IDs/names.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class VehicleDTO {

    private Long id;
    private String make;
    private String model;
    private Integer year;
    private BigDecimal price;
    private String vin;
    private VehicleStatus status;

    /** Set of features associated with this vehicle */
    private Set<FeatureDTO> features;
}
