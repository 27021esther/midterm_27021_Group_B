package com.vehicletrading.vehicle_trading.controller;

import com.vehicletrading.vehicle_trading.dto.UserDTO;
import com.vehicletrading.vehicle_trading.entity.User;
import com.vehicletrading.vehicle_trading.entity.UserProfile;
import com.vehicletrading.vehicle_trading.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping
    public ResponseEntity<User> create(@RequestBody UserDTO userDTO) {
        User user = User.builder()
            .firstName(userDTO.getFirstName())
            .lastName(userDTO.getLastName())
            .email(userDTO.getEmail())
            .username(userDTO.getUsername())
            .password(userDTO.getPassword())
            .build();

        UserProfile profile = null;
        if (userDTO.getProfile() != null) {
            profile = UserProfile.builder()
                .phoneNumber(userDTO.getProfile().getPhoneNumber())
                .nationalId(userDTO.getProfile().getNationalId())
                .bio(userDTO.getProfile().getBio())
                .build();
        }

        User savedUser = userService.saveUser(user, userDTO.getLocationCode(), userDTO.getRoleNames(), profile);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedUser);
    }

    @GetMapping
    public ResponseEntity<Page<User>> getAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(defaultValue = "firstName") String sortBy,
            @RequestParam(defaultValue = "asc") String sortDir) {
        
        Sort sort = sortDir.equalsIgnoreCase("desc") 
            ? Sort.by(sortBy).descending() 
            : Sort.by(sortBy).ascending();
        
        Pageable pageable = PageRequest.of(page, size, sort);
        return ResponseEntity.ok(userService.getAllUsers(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getById(@PathVariable Long id) {
        return ResponseEntity.ok(userService.getById(id));
    }

    @GetMapping("/exists")
    public ResponseEntity<Boolean> existsByEmail(@RequestParam String email) {
        return ResponseEntity.ok(userService.existsByEmail(email));
    }
}