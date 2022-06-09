package com.maktabProject.Part4MaktabFinalProject.Final.part.controller;

import com.maktabProject.Part4MaktabFinalProject.Final.part.entity.UI.ChargeWallet;
import com.maktabProject.Part4MaktabFinalProject.Final.part.services.UserDetailsServiceImpl;
import com.maktabProject.Part4MaktabFinalProject.Final.part.services.WalletService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("wallet")
public class WalletController {
    private final WalletService walletService;
    private final UserDetailsServiceImpl userDetailsService;

    public WalletController(WalletService walletService, UserDetailsServiceImpl userDetailsService) {
        this.walletService = walletService;
        this.userDetailsService = userDetailsService;
    }

    @GetMapping( "save")
    public String saveOrUpdate(Model model) {
        model.addAttribute("chargeWallet", new ChargeWallet());
        return "chargeWallet";
    }

    @PostMapping("save")
    public String saveOrUpdate(@ModelAttribute("chargeWallet") ChargeWallet chargeWallet) {
        var customer = userDetailsService.findById(chargeWallet.getCustomerId());
        var wallet = customer.getWallet();
        var currentAmount = wallet.getAmount();
        currentAmount += chargeWallet.getAmount();
        wallet.setAmount(currentAmount);
        walletService.saveOrUpdate(wallet);
        return "success";
    }
}
