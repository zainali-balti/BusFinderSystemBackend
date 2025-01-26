package com.example.Bus.Finder.System.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Entity
@Table(name = "fares")
public class Fare {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long fareId;

    @ManyToOne
    @JoinColumn(name = "route_id", nullable = false)
    private Route route;

    @ManyToOne
    @JoinColumn(name = "source_stop_id", nullable = false)
    private BusStop sourceStop;

    @ManyToOne
    @JoinColumn(name = "destination_stop_id", nullable = false)
    private BusStop destinationStop;

    @Column(nullable = false)
    private BigDecimal fare;
}
