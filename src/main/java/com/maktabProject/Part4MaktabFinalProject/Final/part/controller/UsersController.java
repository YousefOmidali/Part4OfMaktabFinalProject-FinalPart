package com.maktabProject.Part4MaktabFinalProject.Final.part.controller;

import com.maktabProject.Part4MaktabFinalProject.Final.part.entity.UI.PayForOrder;
import com.maktabProject.Part4MaktabFinalProject.Final.part.entity.UI.PayForOrderByCard;
import com.maktabProject.Part4MaktabFinalProject.Final.part.entity.Users;
import com.maktabProject.Part4MaktabFinalProject.Final.part.entity.Wallet;
import com.maktabProject.Part4MaktabFinalProject.Final.part.entity.enums.RolesInEnum;
import com.maktabProject.Part4MaktabFinalProject.Final.part.entity.enums.UserStatus;
import com.maktabProject.Part4MaktabFinalProject.Final.part.services.OrderService;
import com.maktabProject.Part4MaktabFinalProject.Final.part.services.RoleService;
import com.maktabProject.Part4MaktabFinalProject.Final.part.services.UserDetailsServiceImpl;
import com.maktabProject.Part4MaktabFinalProject.Final.part.services.WalletService;
import net.bytebuddy.utility.RandomString;
import org.hibernate.engine.jdbc.BlobProxy;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.stream.Collectors;

@Controller
@RequestMapping({"users", "Users"})
public class UsersController {
    private final UserDetailsServiceImpl userDetailsService;
    private final RoleService roleService;
    private final WalletService walletService;
    private final OrderService orderService;

    public UsersController(UserDetailsServiceImpl userDetailsService, RoleService roleService, WalletService walletService, OrderService orderService) {
        this.userDetailsService = userDetailsService;
        this.roleService = roleService;
        this.walletService = walletService;
        this.orderService = orderService;
    }

    @GetMapping("saveAdmin")
    public String saveOrUpdateAdmin(Model model) {
        model.addAttribute("admin", new Users());
        return "adminRegisterPage";
    }

    @PostMapping("saveAdmin")
    public String saveOrUpdateAdmin(@ModelAttribute("admin") Users users) {
        users.setSignUpTime(String.valueOf(LocalDateTime.now()));
        users.setRolesInEnum(RolesInEnum.ADMIN);
        var role = roleService.findByName("ADMIN");
        users.getRoles().add(role);
        users.setUserStatus(UserStatus.NEW);
        var user = userDetailsService.saveOrUpdate(users);
        role.getUsers().add(user);
        roleService.saveOrUpdate(role);
        return "success";
    }

    @GetMapping("success")
    public String success(Model model) {
        return "success";
    }

    @GetMapping("customerRegister")
    public String saveOrUpdateCustomer(Model model) {
        model.addAttribute("user", new Users());
        return "customerRegisterPage";
    }

    @PostMapping("customerRegister")
    public String saveOrUpdateCustomer(@ModelAttribute("customer") Users users, HttpServletRequest request) {
        users.setSignUpTime(String.valueOf(LocalDateTime.now()));
        users.setRolesInEnum(RolesInEnum.CUSTOMER);
        walletService.saveOrUpdate(users.getWallet());
        users.setUserStatus(UserStatus.NEW);
        users.setVerified(false);
        users.setSignUpTime(String.valueOf(LocalDateTime.now()));
        var role = roleService.findByName("CUSTOMER");
        users.getRoles().add(role);
        users.setVerificationCode(RandomString.make(64));

        var user = userDetailsService.saveOrUpdate(users);
        userDetailsService.sendVerificationEmail(user, userDetailsService.getSiteURL(request));
        role.getUsers().add(user);
        roleService.saveOrUpdate(role);
        return "successfulRegistration";
    }

    @GetMapping("expertRegister")
    public String saveOrUpdateExpert(Model model) {
        model.addAttribute("user", new Users());
        return "expertRegisterPage";
    }

    @PostMapping("expertRegister")
    public String saveOrUpdateExpert(@ModelAttribute("user") Users users, HttpServletRequest request) {
        System.out.println(users);
        users.setSignUpTime(String.valueOf(LocalDateTime.now()));
        users.setRolesInEnum(RolesInEnum.EXPERT);
        users.setVerified(false);
        users.setWallet(new Wallet(0L));
        walletService.saveOrUpdate(users.getWallet());
        users.setImage(BlobProxy.generateProxy(userDetailsService.getImage(users.getImageLink())));
        users.setImageLink("Image is connected");
        users.setUserStatus(UserStatus.NEW);
        users.setLikes(0L);
        users.setVerificationCode(RandomString.make(64));
        var role = roleService.findByName("EXPERT");
        users.getRoles().add(role);
        var user = userDetailsService.saveOrUpdate(users);
        userDetailsService.sendVerificationEmail(user, userDetailsService.getSiteURL(request));
        role.getUsers().add(user);
        roleService.saveOrUpdate(role);
        return "successfulRegistration";
    }


    @GetMapping({"search", "gridSearch", "gridsearch"})
    public String gridSearch(Model model) {
        model.addAttribute("users", new Users());
        return "usersSearchPage";
    }

    @PostMapping({"search", "gridSearch", "gridsearch"})
    public String gridSearch(@ModelAttribute("users") Users users, Model model) {
        var list = userDetailsService.gridSearch(users.getFirstname(), users.getLastname()
                , users.getEmail(), users.getUsername());
        model.addAttribute("usersList", list);
        return "usersSearchResultPage";
    }

    @GetMapping({"payOrder", "PayOrder"})
    public String payOrder(Model model) {
        model.addAttribute("payForOrder", new PayForOrder());
        return "payOrder";
    }

    @PostMapping({"payOrder", "PayOrder"})
    public String payOrder(@ModelAttribute("payForOrder") PayForOrder payForOrder) {
        var customer = userDetailsService.findById(payForOrder.getCustomerId());
        var customerWallet = customer.getWallet();
        var order = orderService.findById(payForOrder.getOrderId());
        if (order.getPayed())
            return "alreadyPayed";
        var expert = userDetailsService.findById(payForOrder.getExpertId());
        if (payForOrder.getAmount() >= order.getSuggestedPrice()
                && customerWallet.getAmount() >= payForOrder.getAmount()) {
            var x = customerWallet.getAmount() - payForOrder.getAmount();
            customerWallet.setAmount(x);
            walletService.saveOrUpdate(customerWallet);
            var expertWallet = expert.getWallet();
            var expertWalletAmount = expertWallet.getAmount() + (x * 7 / 10);
            expertWallet.setAmount(expertWalletAmount);
            walletService.saveOrUpdate(expertWallet);
            order.setPayed(Boolean.TRUE);
            orderService.saveOrUpdate(order);
            return "success";
        } else
            return "payError";
    }

    @GetMapping("payOrderByCard")
    public String payOrderByCard(Model model) {
        model.addAttribute("payForOrderByCard", new PayForOrderByCard());
        return "payOrderByCard";
    }

    @PostMapping("payOrderByCard")
    public String payOrderByCard(@ModelAttribute("payForOrderByCard") PayForOrderByCard payForOrderByCard) {
        var order = orderService.findById(payForOrderByCard.getOrderId());
        if (order.getPayed())
            return "alreadyPayed";
        var expert = userDetailsService.findById(payForOrderByCard.getExpertId());
        if (payForOrderByCard.getAmount() >= order.getSuggestedPrice()) {
            var expertWallet = expert.getWallet();
            var expertWalletAmount = expertWallet.getAmount() + (payForOrderByCard.getAmount() * 7 / 10);
            expertWallet.setAmount(expertWalletAmount);
            walletService.saveOrUpdate(expertWallet);
            order.setPayed(Boolean.TRUE);
            orderService.saveOrUpdate(order);
            return "success";
        } else
            return "payError";
    }

    @GetMapping("allAwaitingApprovalUsers")
    public String allAwaitingApprovalUsers(Model model) {
        var list = userDetailsService.findAll()
                .stream()
                .filter(a -> a.getUserStatus() == UserStatus.AwaitingApproval);
        model.addAttribute("usersList", list.collect(Collectors.toList()));
        return "usersSearchResultPage";
    }

    @GetMapping("acceptUser")
    public String acceptUser(Model model) {
        model.addAttribute("user", new Users());
        return "acceptUser";
    }

    @PostMapping("acceptUser")
    public String acceptUser(@ModelAttribute("user") Users users) {
        var user = userDetailsService.findById(users.getId());
        if (user.getUserStatus() != UserStatus.AwaitingApproval)
            return "wrongUserStatus";
        user.setUserStatus(UserStatus.Accepted);
        userDetailsService.saveOrUpdate(user);
        return "success";
    }

    @GetMapping("/signUpTime")
    public String signUpTime(Model model) {
        model.addAttribute("usersList", userDetailsService.findAll());
        return "usersSingUpTimeResultPage";
    }

    @GetMapping("/verify")
    public String verifyUser(@Param("code") String code) {
        if (userDetailsService.verify(code)) {
            return "verify_success";
        } else {
            return "verify_fail";
        }
    }

    @GetMapping("adminMenu")
    public String adminMenu() {
        return "adminMenu";
    }

    @GetMapping("expertMenu")
    public String expertMenu() {
        return "expertMenu";
    }

    @GetMapping("customerMenu")
    public String customerMenu() {
        return "customerMenu";
    }

    @GetMapping("mainMenu")
    public String mainMenu() {
        return "mainMenu";
    }

    @GetMapping("404")
    public String NotAccessError() {
        return "404";
    }

    @GetMapping("timesUp")
    public String timesUp() {
        return "timesUp";
    }
}
