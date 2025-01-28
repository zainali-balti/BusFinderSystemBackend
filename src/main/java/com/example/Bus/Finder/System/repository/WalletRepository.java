package com.example.Bus.Finder.System.repository;

import com.example.Bus.Finder.System.dto.WalletDto;
import com.example.Bus.Finder.System.entity.Wallet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface WalletRepository extends JpaRepository<Wallet,Long> {

    Optional<Wallet> findByUser_UserId(Long userId);

}
