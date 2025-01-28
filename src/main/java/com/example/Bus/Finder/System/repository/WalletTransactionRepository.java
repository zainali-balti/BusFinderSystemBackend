package com.example.Bus.Finder.System.repository;

import com.example.Bus.Finder.System.entity.WalletTransaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WalletTransactionRepository extends JpaRepository<WalletTransaction,Long> {
    List<WalletTransaction> findByWallet_WalletId(Long walletId);
}
