package com.example.Bus.Finder.System.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "search_for_bus")
public class SearchingForBus {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
    @ManyToOne
    @JoinColumn(name = "source_stop_id", nullable = false)
    private BusStop sourceStop;
    @ManyToOne
    @JoinColumn(name = "destination_stop_id", nullable = false)
    private BusStop destinationStop;
}
