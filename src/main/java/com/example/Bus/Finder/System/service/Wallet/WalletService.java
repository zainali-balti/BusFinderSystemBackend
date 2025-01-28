package com.example.Bus.Finder.System.service.Wallet;

import com.example.Bus.Finder.System.dto.WalletDto;
import com.example.Bus.Finder.System.dto.WalletTransactionDto;

import java.math.BigDecimal;
import java.util.List;

public interface WalletService {
    List<WalletTransactionDto> getTransactionHistory(Long userId);
    WalletDto deductMoney(Long userId, BigDecimal amount);
    WalletDto addMoney(Long userId, BigDecimal amount);
    WalletDto getWalletByUserId(Long userId);
}
