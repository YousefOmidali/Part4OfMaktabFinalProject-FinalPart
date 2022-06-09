package com.maktabProject.Part4MaktabFinalProject.Final.part.controller;

import com.maktabProject.Part4MaktabFinalProject.Final.part.entity.Service;
import com.maktabProject.Part4MaktabFinalProject.Final.part.services.ServiceService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping({"Service", "service"})
public class ServiceController {
    private final ServiceService serviceService;

    public ServiceController(ServiceService serviceService) {
        this.serviceService = serviceService;
    }


    @GetMapping("serviceRegister")
    public String saveOrUpdate(Model model) {
        model.addAttribute("service", new Service());
        return "serviceRegisterPage";
    }

    @PostMapping("serviceRegister")
    public String saveOrUpdate(@ModelAttribute("service") Service service) {
        System.out.println(service);
        serviceService.saveOrUpdate(service);
        return "success";
    }


    @GetMapping("findAll")
    public String findAll(Model model) {
        model.addAttribute("serviceList", serviceService.findAll());
        return "findAllService";
    }
}
