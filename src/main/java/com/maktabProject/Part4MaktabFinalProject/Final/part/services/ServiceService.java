package com.maktabProject.Part4MaktabFinalProject.Final.part.services;



import com.maktabProject.Part4MaktabFinalProject.Final.part.entity.Service;
import com.maktabProject.Part4MaktabFinalProject.Final.part.repository.ServiceRepository;
import com.maktabProject.Part4MaktabFinalProject.Final.part.services.base.BaseService;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@org.springframework.stereotype.Service
public class ServiceService implements BaseService<Service, Long> {
    private final ServiceRepository serviceRepository;

    public ServiceService(ServiceRepository serviceRepository) {
        this.serviceRepository = serviceRepository;
    }

    @Override
    @Transactional
    public Service saveOrUpdate(Service service) {
        return serviceRepository.save(service);
    }

    @Override
    @Transactional
    public List<Service> findAll() {
        return serviceRepository.findAll();
    }

    @Override
    @Transactional
    public Service findById(Long id) {
        return serviceRepository.findById(id).orElse(null);
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        serviceRepository.deleteById(id);
    }
}
