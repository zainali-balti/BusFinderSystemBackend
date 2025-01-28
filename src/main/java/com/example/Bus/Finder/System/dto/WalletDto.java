package com.example.Bus.Finder.System.dto;

import com.example.Bus.Finder.System.entity.User;
import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class WalletDto {
    private Long walletId;
    private Long userId;
    private BigDecimal balance = BigDecimal.ZERO;
}
