package com.maktabProject.Part4MaktabFinalProject.Final.part.controller;

import com.maktabProject.Part4MaktabFinalProject.Final.part.entity.SubService;
import com.maktabProject.Part4MaktabFinalProject.Final.part.entity.UI.ExpertIdAndSubServiceId;
import com.maktabProject.Part4MaktabFinalProject.Final.part.services.ServiceService;
import com.maktabProject.Part4MaktabFinalProject.Final.part.services.SubServiceService;
import com.maktabProject.Part4MaktabFinalProject.Final.part.services.UserDetailsServiceImpl;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping( "subService")
public class SubServiceController {
    private final SubServiceService subServiceService;
    private final ServiceService serviceService;
    private final UserDetailsServiceImpl userDetailsService;

    public SubServiceController(SubServiceService subServiceService, ServiceService serviceService, UserDetailsServiceImpl userDetailsService) {
        this.subServiceService = subServiceService;
        this.serviceService = serviceService;
        this.userDetailsService = userDetailsService;
    }

    @GetMapping("subServiceRegister")
    public String saveOrUpdate(Model model) {
        model.addAttribute("subService", new SubService());
        return "subServiceRegisterPage";
    }

    @PostMapping("subServiceRegister")
    public String saveOrUpdate(@ModelAttribute("subService") SubService subService) {
        var service = serviceService.findById(subService.getService().getId());
        subService.setService(service);
        subServiceService.saveOrUpdate(subService);
        return "success";
    }

    @GetMapping("findAll")
    public String findAll(Model model) {
        model.addAttribute("subServiceList", subServiceService.findAll());
        return "findAllSubServices";
    }

    @GetMapping("takeASubService")
    public String takeASubService(Model model) {
        model.addAttribute("expertIdAndSubServiceId", new ExpertIdAndSubServiceId());
        return "takeASubServiceForAnExpert";
    }

    @PostMapping("takeASubService")
    public String takeASubService(@ModelAttribute("subService") ExpertIdAndSubServiceId expertIdAndSubServiceId) {
        var users = userDetailsService.findById(expertIdAndSubServiceId.getExpertId());
        System.out.println(users);
        var subService = subServiceService.findById(expertIdAndSubServiceId.getSubServiceId());
        users.getSubService().add(subService);
        System.out.println(users);
        userDetailsService.saveOrUpdate(users);
        System.out.println(users);
        subService.getUsers().add(users);
        System.out.println(users);
        subServiceService.saveOrUpdate(subService);
        System.out.println(users);
        return "success";
    }

}
