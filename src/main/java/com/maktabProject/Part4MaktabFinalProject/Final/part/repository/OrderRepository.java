package com.maktabProject.Part4MaktabFinalProject.Final.part.repository;

import com.maktabProject.Part4MaktabFinalProject.Final.part.entity.Order;
import com.maktabProject.Part4MaktabFinalProject.Final.part.entity.SubService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface OrderRepository extends JpaRepository<Order,Long> {
    List<Order> findAllBySubServiceEquals(SubService subService);

    List<Order> findAllByUsers_Id(Long id);
}
