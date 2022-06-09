package com.maktabProject.Part4MaktabFinalProject.Final.part.controller;

import com.maktabProject.Part4MaktabFinalProject.Final.part.entity.Role;
import com.maktabProject.Part4MaktabFinalProject.Final.part.services.RoleService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("role")
public class RoleController {
    private final RoleService roleService;

    public RoleController(RoleService roleService) {
        this.roleService = roleService;
    }

    @GetMapping("save")
    public String saveOrUpdate(Model model) {
        model.addAttribute("role", new Role());
        return "roleRegisterPage";
    }

    @PostMapping("save")
    public String saveOrUpdate(@ModelAttribute("role") Role role) {
        roleService.saveOrUpdate(role);
        return "success";
    }
}
