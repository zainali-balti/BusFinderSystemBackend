package com.example.Bus.Finder.System.service.Wallet;

import com.example.Bus.Finder.System.dto.WalletDto;
import com.example.Bus.Finder.System.dto.WalletTransactionDto;
import com.example.Bus.Finder.System.entity.BusBooking;
import com.example.Bus.Finder.System.entity.User;
import com.example.Bus.Finder.System.entity.Wallet;
import com.example.Bus.Finder.System.entity.WalletTransaction;
import com.example.Bus.Finder.System.enums.TransactionType;
import com.example.Bus.Finder.System.repository.BusBookingRepository;
import com.example.Bus.Finder.System.repository.UserRepository;
import com.example.Bus.Finder.System.repository.WalletRepository;
import com.example.Bus.Finder.System.repository.WalletTransactionRepository;
import com.example.Bus.Finder.System.service.BusBooking.BusBookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class WalletServiceImplementation implements WalletService{
    @Autowired
    private WalletRepository walletRepository;

    @Autowired
    private WalletTransactionRepository transactionRepository;

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private BusBookingService busBookingService;

    public WalletDto getWalletByUserId(Long userId) {
        Wallet wallet = walletRepository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("Wallet not found for user ID: " + userId));
        return toWalletDto(wallet);
    }

    public WalletDto addMoney(Long userId, BigDecimal amount) {
        Wallet wallet = walletRepository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("Wallet not found for user ID: " + userId));
        wallet.setBalance(wallet.getBalance().add(amount));

        WalletTransaction transaction = new WalletTransaction();
        transaction.setWallet(wallet);
        transaction.setAmount(amount);
        transaction.setTransactionType(TransactionType.CREDIT);
        transaction.setDescription("Money added to wallet");
        transactionRepository.save(transaction);

        Wallet updatedWallet = walletRepository.save(wallet);
        return toWalletDto(updatedWallet);
    }

    public WalletDto deductMoney(Long userId, BigDecimal amount) {
        Wallet wallet = walletRepository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("Wallet not found for user ID: " + userId));

        if (wallet.getBalance().compareTo(amount) < 0) {
            throw new RuntimeException("Insufficient balance");
        }

        wallet.setBalance(wallet.getBalance().subtract(amount));

        WalletTransaction transaction = new WalletTransaction();
        transaction.setWallet(wallet);
        transaction.setAmount(amount);
        transaction.setTransactionType(TransactionType.DEBIT);
        transaction.setDescription("Money deducted for bus booking");
        transactionRepository.save(transaction);

        Wallet updatedWallet = walletRepository.save(wallet);
        return toWalletDto(updatedWallet);
    }

    public List<WalletTransactionDto> getTransactionHistory(Long userId) {
        Wallet wallet = walletRepository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("Wallet not found for user ID: " + userId));
        List<WalletTransaction> transactions = transactionRepository.findByWallet_WalletId(wallet.getWalletId());
        return transactions.stream().map(this::toWalletTransactionDto).toList();
    }

    private WalletDto toWalletDto(Wallet wallet) {
        WalletDto walletDto = new WalletDto();
        walletDto.setWalletId(wallet.getWalletId());
        walletDto.setUserId(wallet.getUser().getId());
        walletDto.setBalance(wallet.getBalance());
        return walletDto;
    }

    private WalletTransactionDto toWalletTransactionDto(WalletTransaction transaction) {
        WalletTransactionDto transactionDto = new WalletTransactionDto();
        transactionDto.setTransactionId(transaction.getTransactionId());
        transactionDto.setWalletId(transaction.getWallet().getWalletId());
        transactionDto.setAmount(transaction.getAmount());
        transactionDto.setTransactionType(transaction.getTransactionType());
        transactionDto.setDescription(transaction.getDescription());
        transactionDto.setTransactionTime(transaction.getTransactionTime());
        return transactionDto;
    }
    public Wallet addWallet(Long userId, BigDecimal initialBalance) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found for ID: " + userId));

        Wallet wallet = new Wallet();
        wallet.setUser(user);
        wallet.setBalance(initialBalance);

        return walletRepository.save(wallet);
    }
    public void deleteTransaction(Long transactionId) {
        // Find the transaction by its ID
        WalletTransaction transaction = transactionRepository.findById(transactionId)
                .orElseThrow(() -> new RuntimeException("Transaction not found with ID: " + transactionId));
        transactionRepository.delete(transaction);
    }

}
