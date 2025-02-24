package com.example.Bus.Finder.System.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "bus_stops")
public class BusStop{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long stopId;
    @Column(nullable = false)
    private String name;
}
