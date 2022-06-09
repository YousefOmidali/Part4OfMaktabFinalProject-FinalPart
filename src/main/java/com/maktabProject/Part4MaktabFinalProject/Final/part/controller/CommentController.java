package com.maktabProject.Part4MaktabFinalProject.Final.part.controller;

import com.maktabProject.Part4MaktabFinalProject.Final.part.entity.Comment;
import com.maktabProject.Part4MaktabFinalProject.Final.part.entity.Users;
import com.maktabProject.Part4MaktabFinalProject.Final.part.entity.enums.OrderStatus;
import com.maktabProject.Part4MaktabFinalProject.Final.part.services.CommentService;
import com.maktabProject.Part4MaktabFinalProject.Final.part.services.OrderService;
import com.maktabProject.Part4MaktabFinalProject.Final.part.services.UserDetailsServiceImpl;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("comment")
public class CommentController {
    private final CommentService commentService;
    private final UserDetailsServiceImpl userDetailsService;
    private final OrderService orderService;

    public CommentController(CommentService commentService, UserDetailsServiceImpl userDetailsService, OrderService orderService) {
        this.commentService = commentService;
        this.userDetailsService = userDetailsService;
        this.orderService = orderService;
    }

    @GetMapping("save")
    public String saveOrUpdate(Model model) {
        model.addAttribute("comment", new Comment());
        return "commentRegister";
    }

    @PostMapping("save")
    public String saveOrUpdate(@ModelAttribute("comment") Comment comment) {
        var order = orderService.findById(comment.getOrder().getId());
        comment.setOrder(order);
        var users = userDetailsService.findById(comment.getUsers().getId());
        comment.setUsers(users);
        if (order.getOrderStatus().equals(OrderStatus.OrderIsFinishedSuccessFully)) {
            commentService.saveOrUpdate(comment);
            return "success";
        } else
            return "commentError";
    }


    @GetMapping("findAll")
    public String findAll(Model model) {
        model.addAttribute("users", new Users());
        return "commentsOfACustomer";
    }
    @PostMapping({"findAll", "findall", "FindAll", "findALL"})
    public String findAll(@ModelAttribute Users users, Model model) {
        var commentsOfACustomer = commentService.commentsOfACustomer(users.getId());
        model.addAttribute("comments", commentsOfACustomer);
        return "commentsOfACustomerResultPage";
    }
}
