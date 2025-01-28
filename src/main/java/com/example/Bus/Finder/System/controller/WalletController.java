package com.example.Bus.Finder.System.controller;

import com.example.Bus.Finder.System.dto.WalletDto;
import com.example.Bus.Finder.System.dto.WalletTransactionDto;
import com.example.Bus.Finder.System.entity.Wallet;
import com.example.Bus.Finder.System.service.Wallet.WalletService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/wallet")
public class WalletController {
    @Autowired
    private WalletService walletService;

    @PostMapping("/add-wallet/{userId}")
    public ResponseEntity<Wallet> addWallet(@PathVariable Long userId, @RequestBody BigDecimal initialBalance) {
        Wallet wallet = walletService.addWallet(userId, initialBalance);
        return ResponseEntity.ok(wallet);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<WalletDto> getWallet(@PathVariable Long userId) {
        WalletDto wallet = walletService.getWalletByUserId(userId);
        return ResponseEntity.ok(wallet);
    }

    @PostMapping("/add-money/{userId}")
    public ResponseEntity<WalletDto> addMoney(@PathVariable Long userId, @RequestBody BigDecimal amount) {
        WalletDto wallet = walletService.addMoney(userId, amount);
        return ResponseEntity.ok(wallet);
    }

    @PostMapping("/deduct-money/{userId}")
    public ResponseEntity<?> deductMoney(@PathVariable Long userId, @RequestBody BigDecimal amount) {
        try {
            WalletDto wallet = walletService.deductMoney(userId, amount);
            return ResponseEntity.ok(wallet);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/transactions/{userId}")
    public ResponseEntity<List<WalletTransactionDto>> getTransactionHistory(@PathVariable Long userId) {
        List<WalletTransactionDto> transactions = walletService.getTransactionHistory(userId);
        return ResponseEntity.ok(transactions);
    }
}
