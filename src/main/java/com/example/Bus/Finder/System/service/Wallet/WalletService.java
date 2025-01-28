package com.example.Bus.Finder.System.service.Wallet;

import com.example.Bus.Finder.System.dto.WalletDto;
import com.example.Bus.Finder.System.dto.WalletTransactionDto;
import com.example.Bus.Finder.System.entity.Wallet;

import java.math.BigDecimal;
import java.util.List;

public interface WalletService {
    List<WalletTransactionDto> getTransactionHistory(Long userId);
    WalletDto deductMoney(Long userId, BigDecimal amount);
    WalletDto addMoney(Long userId, BigDecimal amount);
    WalletDto getWalletByUserId(Long userId);
    Wallet addWallet(Long userId, BigDecimal initialBalance);
}
