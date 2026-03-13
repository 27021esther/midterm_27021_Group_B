package com.vehicletrading.vehicle_trading.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

/**
 * UserProfile entity stores additional profile information for a User.
 * One-to-One relationship with User (inverse side).
 */
@Entity
@Table(name = "user_profiles")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserProfile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "phone_number", length = 20)
    private String phoneNumber;

    @Column(name = "national_id", unique = true, length = 20)
    private String nationalId;

    @Column(length = 500)
    private String bio;

    /**
     * One-to-One: a User has one UserProfile.
     * The foreign key "user_id" is stored in the user_profiles table.
     * mappedBy = "userProfile" indicates User is the owning side.
     */
    @OneToOne(mappedBy = "userProfile", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnore
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private User user;
}