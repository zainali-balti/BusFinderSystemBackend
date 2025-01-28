package com.example.Bus.Finder.System.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Entity
@Table(name = "wallet")
public class Wallet {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long walletId;

        @OneToOne
        @JoinColumn(name = "user_id", nullable = false)
        private User user;

        @Column(nullable = false)
        private BigDecimal balance = BigDecimal.ZERO;
}
