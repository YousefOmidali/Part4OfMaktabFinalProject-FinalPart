package com.maktabProject.Part4MaktabFinalProject.Final.part.repository;

import com.maktabProject.Part4MaktabFinalProject.Final.part.entity.SubService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SubServiceRepository extends JpaRepository<SubService,Long> {
}
