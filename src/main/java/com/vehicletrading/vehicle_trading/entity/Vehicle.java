package com.vehicletrading.vehicle_trading.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

/**
 * Vehicle entity stores details about vehicles for sale.
 * Many-to-Many with Feature through join table "vehicle_features".
 * One-to-Many with Transaction.
 */
@Entity
@Table(name = "vehicles")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Vehicle {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String make;   // e.g. Toyota

    @Column(nullable = false, length = 100)
    private String model;  // e.g. Corolla

    @Column(name = "manufacture_year", nullable = false)
    private Integer year;

    @Column(nullable = false, precision = 15, scale = 2)
    private BigDecimal price;

    /** Vehicle Identification Number – unique per vehicle */
    @Column(name = "vin", unique = true, nullable = false, length = 50)
    private String vin;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private VehicleStatus status;

    public enum VehicleStatus { AVAILABLE, SOLD, RESERVED }

    /**
     * Many-to-Many: a Vehicle can have many Features,
     * and a Feature can belong to many Vehicles.
     * The join table "vehicle_features" holds the foreign keys.
     */
    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(
        name = "vehicle_features",
        joinColumns = @JoinColumn(name = "vehicle_id"),
        inverseJoinColumns = @JoinColumn(name = "feature_id")
    )
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Set<Feature> features;

    /** One Vehicle → Many Transactions */
    @OneToMany(mappedBy = "vehicle", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnore
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private List<Transaction> transactions;
}
