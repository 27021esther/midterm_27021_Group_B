package com.vehicletrading.vehicle_trading.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

/**
 * Feature entity stores optional vehicle features like "Sunroof", "GPS", "Leather Seats".
 * Many-to-Many with Vehicle (inverse side).
 */
@Entity
@Table(name = "features")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Feature {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false, length = 100)
    private String name;

    @Column(length = 255)
    private String description;

    /**
     * Inverse side of the Many-to-Many relationship.
     * mappedBy = "features" refers to the owning side in Vehicle.
     */
    @ManyToMany(mappedBy = "features")
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Set<Vehicle> vehicles;
}
