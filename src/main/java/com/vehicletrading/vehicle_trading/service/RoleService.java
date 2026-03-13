package com.vehicletrading.vehicle_trading.service;

import com.vehicletrading.vehicle_trading.entity.Role;
import com.vehicletrading.vehicle_trading.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RoleService {

    private final RoleRepository roleRepository;

    public Role saveRole(Role role) {
        if (roleRepository.existsByRoleName(role.getRoleName())) {
            throw new IllegalArgumentException(
                "Role with name '" + role.getRoleName() + "' already exists.");
        }
        return roleRepository.save(role);
    }

    public List<Role> getAllRoles() {
        return roleRepository.findAll();
    }

    public Role getById(Long id) {
        return roleRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Role not found with id: " + id));
    }

    public Role findByRoleName(String roleName) {
        return roleRepository.findByRoleName(roleName)
            .orElseThrow(() -> new RuntimeException("Role not found with name: " + roleName));
    }
    
    public Role updateRole(Long id, Role role) {
        Role existing = getById(id);
        existing.setRoleName(role.getRoleName());
        existing.setDescription(role.getDescription());
        return roleRepository.save(existing);
    }
    
    public void deleteRole(Long id) {
        roleRepository.deleteById(id);
    }
}