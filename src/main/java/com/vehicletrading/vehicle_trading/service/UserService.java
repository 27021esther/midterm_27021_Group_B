package com.vehicletrading.vehicle_trading.service;

import com.vehicletrading.vehicle_trading.entity.Location;
import com.vehicletrading.vehicle_trading.entity.Role;
import com.vehicletrading.vehicle_trading.entity.User;
import com.vehicletrading.vehicle_trading.entity.UserProfile;
import com.vehicletrading.vehicle_trading.repository.LocationRepository;
import com.vehicletrading.vehicle_trading.repository.RoleRepository;
import com.vehicletrading.vehicle_trading.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final LocationRepository locationRepository;
    private final RoleRepository roleRepository;

    @Transactional
    public User saveUser(User user, String locationCode, List<String> roleNames, UserProfile profile) {
        if (userRepository.existsByEmail(user.getEmail())) {
            throw new IllegalArgumentException(
                "User with email '" + user.getEmail() + "' already exists.");
        }

        Location location = locationRepository.findByLocationType("VILLAGE").stream()
            .filter(loc -> loc.getCode().equals(locationCode))
            .findFirst()
            .orElseThrow(() -> new RuntimeException("Location not found with code: " + locationCode));
        user.setLocation(location);

        if (profile != null) {
            profile.setUser(user);
            user.setUserProfile(profile);
        }

        if (roleNames != null && !roleNames.isEmpty()) {
            Set<Role> roles = new HashSet<>();
            for (String roleName : roleNames) {
                Role role = roleRepository.findByRoleName(roleName)
                    .orElseThrow(() -> new RuntimeException("Role not found: " + roleName));
                roles.add(role);
            }
            user.setRoles(roles);
        }

        return userRepository.save(user);
    }

    public Page<User> getAllUsers(Pageable pageable) {
        return userRepository.findAll(pageable);
    }

    public User getById(Long id) {
        return userRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("User not found with id: " + id));
    }

    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }
}