package com.vehicletrading.vehicle_trading.dto;

import com.vehicletrading.vehicle_trading.entity.UserProfile;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * User request DTO for creating a new user.
 * Contains locationCode and roleNames for resolving relationships.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDTO {

    private String firstName;
    private String lastName;
    private String email;
    private String username;
    private String password;
    private String locationCode;
    private List<String> roleNames;
    private UserProfileDTO profile;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class UserProfileDTO {
        private String phoneNumber;
        private String nationalId;
        private String bio;
    }
}