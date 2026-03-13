package com.vehicletrading.vehicle_trading.service;

import com.vehicletrading.vehicle_trading.entity.Customer;
import com.vehicletrading.vehicle_trading.entity.Location;
import com.vehicletrading.vehicle_trading.repository.CustomerRepository;
import com.vehicletrading.vehicle_trading.repository.LocationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CustomerService {

    private final CustomerRepository customerRepository;
    private final LocationRepository locationRepository;

    public Customer saveCustomer(Customer customer, UUID locationId) {
        if (customerRepository.existsByEmail(customer.getEmail())) {
            throw new IllegalArgumentException(
                "Customer with email '" + customer.getEmail() + "' already exists.");
        }
        Location location = locationRepository.findById(locationId)
            .orElseThrow(() -> new RuntimeException("Location not found: " + locationId));
        customer.setLocation(location);
        return customerRepository.save(customer);
    }

    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }

    public Customer getById(Long id) {
        return customerRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Customer not found: " + id));
    }
    
    public Customer updateCustomer(Long id, Customer customer, UUID locationId) {
        Customer existing = getById(id);
        existing.setFirstName(customer.getFirstName());
        existing.setLastName(customer.getLastName());
        existing.setEmail(customer.getEmail());
        existing.setPhone(customer.getPhone());
        
        if (locationId != null) {
            Location location = locationRepository.findById(locationId)
                .orElseThrow(() -> new RuntimeException("Location not found: " + locationId));
            existing.setLocation(location);
        }
        
        return customerRepository.save(existing);
    }
    
    public void deleteCustomer(Long id) {
        customerRepository.deleteById(id);
    }
}
