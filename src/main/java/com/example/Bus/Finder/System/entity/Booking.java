package com.example.Bus.Finder.System.entity;

import com.example.Bus.Finder.System.dto.BookingDto;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "booking")
public class Booking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id")
    private User user;
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "vehicle_id")
    private Vehicle vehicles;
    private LocalDateTime bookingDate;
    private LocalDateTime endDate;
    private Double totalPrice;
    private Double distance;

    public BookingDto getAllBooking(){
        BookingDto bookingDto =new BookingDto();
        bookingDto.setId(id);
        bookingDto.setBookingDate(bookingDate);
        bookingDto.setDistance(distance);
        bookingDto.setUserId(user.getId());
        bookingDto.setVehicleId(vehicles.getId());
        bookingDto.setEndDate(endDate);
        bookingDto.setTotalPrice(totalPrice);
        return bookingDto;
    }


}
