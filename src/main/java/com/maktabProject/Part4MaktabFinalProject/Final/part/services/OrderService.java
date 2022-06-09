package com.maktabProject.Part4MaktabFinalProject.Final.part.services;

import com.maktabProject.Part4MaktabFinalProject.Final.part.entity.Order;
import com.maktabProject.Part4MaktabFinalProject.Final.part.entity.SubService;
import com.maktabProject.Part4MaktabFinalProject.Final.part.repository.OrderRepository;
import com.maktabProject.Part4MaktabFinalProject.Final.part.services.base.BaseService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class OrderService implements BaseService<Order, Long> {
    private final OrderRepository orderRepository;

    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @Override
    @Transactional
    public Order saveOrUpdate(Order order) {
        return orderRepository.save(order);
    }

    @Override
    @Transactional
    public List<Order> findAll() {
        return orderRepository.findAll();
    }

    @Override
    @Transactional
    public Order findById(Long id) {
        return orderRepository.findById(id).orElse(null);
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        orderRepository.deleteById(id);
    }

    @Transactional
    public List<Order> allOrdersOfASubService(SubService subService) {
        return orderRepository.findAllBySubServiceEquals(subService);
    }

    @Transactional
    public List<Order> allOrdersOfACustomer(Long id) {
        return orderRepository.findAllByUsers_Id(id);
    }
}
