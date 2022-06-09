package com.maktabProject.Part4MaktabFinalProject.Final.part.controller;

import com.maktabProject.Part4MaktabFinalProject.Final.part.entity.Order;
import com.maktabProject.Part4MaktabFinalProject.Final.part.entity.SubService;
import com.maktabProject.Part4MaktabFinalProject.Final.part.entity.UI.NumberOfOrders;
import com.maktabProject.Part4MaktabFinalProject.Final.part.entity.Users;
import com.maktabProject.Part4MaktabFinalProject.Final.part.entity.enums.OrderStatus;
import com.maktabProject.Part4MaktabFinalProject.Final.part.services.OrderService;
import com.maktabProject.Part4MaktabFinalProject.Final.part.services.SubServiceService;
import com.maktabProject.Part4MaktabFinalProject.Final.part.services.UserDetailsServiceImpl;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.print.attribute.standard.NumberOfDocuments;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("order")
public class OrderController {
    private final OrderService orderService;
    private final UserDetailsServiceImpl userDetailsService;
    private final SubServiceService subServiceService;

    public OrderController(OrderService orderService, UserDetailsServiceImpl userDetailsService, SubServiceService subServiceService) {
        this.orderService = orderService;
        this.userDetailsService = userDetailsService;
        this.subServiceService = subServiceService;
    }

    @GetMapping("saveOrder")
    public String saveOrUpdate(Model model) {
        model.addAttribute("order", new Order());
        return "orderRegisterPage";
    }

    @PostMapping("saveOrder")
    public String saveOrUpdate(@ModelAttribute("order") Order order) {
        var customer = userDetailsService.findById(order.getUsers().getId());
        var subService = subServiceService.findById(order.getSubService().getId());
        orderService.saveOrUpdate(new Order(customer, subService, String.valueOf(LocalDateTime.now())
                , order.getSuggestedPrice(), order.getWorkDescription(), order.getWorkDate()
                , order.getAddress(), Boolean.FALSE,Boolean.FALSE, OrderStatus.WaitingForExpertsSuggestion));
        return "success";
    }

    @GetMapping("acceptOrder")
    public String acceptOrder(Model model) {
        model.addAttribute("order", new Order());
        return "acceptOrder";
    }

    @PostMapping("acceptOrder")
    public String acceptOrder(@ModelAttribute("order") Order order) {
        var mainOrder = orderService.findById(order.getId());
        mainOrder.setAccepted(Boolean.TRUE);
        mainOrder.setOrderStatus(OrderStatus.ThisOrderIsChooseByAnExpert);
        orderService.saveOrUpdate(mainOrder);
        return "success";
    }
    @GetMapping("finishedOrder")
    public String finishedOrder(Model model) {
        model.addAttribute("order", new Order());
        return "finishedOrder";
    }

    @PostMapping("finishedOrder")
    public String finishedOrder(@ModelAttribute("order") Order order) {
        var mainOrder = orderService.findById(order.getId());
        mainOrder.setOrderStatus(OrderStatus.OrderIsFinishedSuccessFully);
        orderService.saveOrUpdate(mainOrder);
        return "success";
    }

    @GetMapping("allOrdersAnExpertCanSee")
    public String allOrdersAnExpertCanSee(Model model) {
        model.addAttribute("users", new Users());
        return "allOrdersAnExpertCanSee";
    }


    @PostMapping("allOrdersAnExpertCanSee")
    public String allOrdersAnExpertCanSee(@ModelAttribute Users users, Model model) {
        var expert = userDetailsService.findById(users.getId());
        List<Order> ordersOfAnExpert = new ArrayList<>();
        for (SubService s : expert.getSubService()) {
            if (orderService.allOrdersOfASubService(s).isEmpty()) {
                System.out.println("is empty");
            } else {
                for (Order order : orderService.allOrdersOfASubService(s)) {
                    if (order.getOrderStatus().equals(OrderStatus.WaitingForExpertsSuggestion))
                        ordersOfAnExpert.add(order);
                }
            }
        }
        model.addAttribute("orders", ordersOfAnExpert);
        return "allOrdersAnExpertCanSeeResult";
    }

    @GetMapping("customerOrder")
    public String allOrdersOfACustomer(Model model) {
        model.addAttribute("users", new Users());
        return "allOrdersOfACustomer";
    }

    @PostMapping("customerOrder")
    public String allOrdersOfACustomer(@ModelAttribute Users users, Model model) {
        var allOrdersOfACustomer = orderService.allOrdersOfACustomer(users.getId());
        model.addAttribute("orders", allOrdersOfACustomer);
        return "allOrdersOfACustomerResult";
    }

    @GetMapping("customerMustPayOrders")
    public String allNotPayedOrdersOfACustomer(Model model) {
        model.addAttribute("users", new Users());
        return "allNotPayedOrdersOfACustomer";
    }

    @PostMapping("customerMustPayOrders")
    public String allNotPayedOrdersOfACustomer(@ModelAttribute Users users, Model model) {
        var allOrdersOfACustomer = orderService.allOrdersOfACustomer(users.getId());
        var listAfterFilters = allOrdersOfACustomer.stream()
                .filter(a -> a.getPayed().equals(Boolean.FALSE))
                .filter(a -> a.getOrderStatus() == OrderStatus.OrderIsFinishedSuccessFully)
                .collect(Collectors.toList());
        model.addAttribute("orders", listAfterFilters);
        return "allOrdersOfACustomerResult";
    }

    @GetMapping("numberOfCustomerOrder")
    public String numberOfCustomerOrders(Model model) {
        model.addAttribute("users", new Users());
        return "numberOfCustomerOrders";
    }
    @PostMapping("numberOfCustomerOrder")
    public String numberOfCustomerOrders(@ModelAttribute Users users, Model model) {
        var list = orderService.allOrdersOfACustomer(users.getId());
        NumberOfOrders number = new NumberOfOrders(list.size());
        model.addAttribute("number", number);
        return "numberOfCustomerOrdersResultPage";
    }
}
