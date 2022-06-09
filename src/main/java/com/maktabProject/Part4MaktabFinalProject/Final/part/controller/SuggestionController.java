package com.maktabProject.Part4MaktabFinalProject.Final.part.controller;

import com.maktabProject.Part4MaktabFinalProject.Final.part.entity.Order;
import com.maktabProject.Part4MaktabFinalProject.Final.part.entity.Suggestion;
import com.maktabProject.Part4MaktabFinalProject.Final.part.entity.enums.OrderStatus;
import com.maktabProject.Part4MaktabFinalProject.Final.part.services.OrderService;
import com.maktabProject.Part4MaktabFinalProject.Final.part.services.SuggestionService;
import com.maktabProject.Part4MaktabFinalProject.Final.part.services.UserDetailsServiceImpl;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.stream.Collectors;

@Controller
@RequestMapping( "suggestion")
public class SuggestionController {
    private final SuggestionService suggestionService;
    private final OrderService orderService;
    private final UserDetailsServiceImpl userDetailsService;

    public SuggestionController(SuggestionService suggestionService, OrderService orderService, UserDetailsServiceImpl userDetailsService) {
        this.suggestionService = suggestionService;
        this.orderService = orderService;
        this.userDetailsService = userDetailsService;
    }

    @GetMapping("save")
    public String saveOrUpdate(Model model) {
        model.addAttribute("suggestion", new Suggestion());
        return "suggestionRegisterPage";
    }

    @PostMapping("save")
    public String saveOrUpdate(@ModelAttribute("suggestion") Suggestion suggestion) {
        var order = orderService.findById(suggestion.getOrder().getId());
        order.setOrderStatus(OrderStatus.WaitingForCustomerToChooseASuggestion);
        orderService.saveOrUpdate(order);
        suggestion.setOrder(order);
        var users = userDetailsService.findById(suggestion.getUsers().getId());
        suggestion.setUsers(users);
        suggestionService.saveOrUpdate(suggestion);
        return "success";
    }

    @GetMapping("ofAnOrder")
    public String suggestionsOfAnOrder(Model model) {
        model.addAttribute("order", new Order());
        return "suggestionsOfAnOrder";
    }

    @PostMapping("ofAnOrder")
    public String suggestionsOfAnOrder(@ModelAttribute("order") Order order, Model model) {
        var suggestionList = suggestionService.suggestionsOfAnOrder(order.getId())
                .stream()
                .filter(c -> c.getOrder().getOrderStatus().equals(OrderStatus.WaitingForCustomerToChooseASuggestion));
        model.addAttribute("suggestionList", suggestionList.collect(Collectors.toList()));
        return "suggestionsOfAnOrderResultPage";
    }
}
