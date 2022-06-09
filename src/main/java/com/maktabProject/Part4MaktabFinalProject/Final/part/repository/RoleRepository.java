package com.maktabProject.Part4MaktabFinalProject.Final.part.repository;

import com.maktabProject.Part4MaktabFinalProject.Final.part.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findByName(String name);

}
