package com.example.Bus.Finder.System.dto;

import com.example.Bus.Finder.System.entity.Wallet;
import com.example.Bus.Finder.System.enums.TransactionType;
import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class WalletTransactionDto {
    private Long transactionId;
    private Long walletId;
    private BigDecimal amount;
    private TransactionType transactionType;
    private String description;
    private LocalDateTime transactionTime = LocalDateTime.now();
}
