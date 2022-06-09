package com.maktabProject.Part4MaktabFinalProject.Final.part.services;

import com.maktabProject.Part4MaktabFinalProject.Final.part.entity.Role;
import com.maktabProject.Part4MaktabFinalProject.Final.part.repository.RoleRepository;
import com.maktabProject.Part4MaktabFinalProject.Final.part.services.base.BaseService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleService implements BaseService<Role, Long> {
    private final RoleRepository roleRepository;

    public RoleService(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public Role saveOrUpdate(Role role) {
        return roleRepository.save(role);
    }

    @Override
    public List<Role> findAll() {
        return roleRepository.findAll();
    }

    @Override
    public Role findById(Long id) {
        return roleRepository.findById(id).orElse(null);
    }

    @Override
    public void deleteById(Long id) {
        roleRepository.deleteById(id);
    }

    public Role findByName(String roleName) {
        return roleRepository.findByName(roleName);
    }
}
