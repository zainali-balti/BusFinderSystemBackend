package com.example.Bus.Finder.System.entity;



import com.example.Bus.Finder.System.dto.BusDto;
import com.example.Bus.Finder.System.enums.Status;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "buses")
public class Bus {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long busId;

    @Column(nullable = false)
    private String busName;


    @Column(unique = true, nullable = false)
    private String busNumber;

    @Column(nullable = false)
    private int capacity;

    @Lob
    @Column(columnDefinition = "longblob")
    private byte[] img;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Status status;
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
}
