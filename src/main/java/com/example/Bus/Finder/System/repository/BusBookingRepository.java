package com.example.Bus.Finder.System.repository;

import com.example.Bus.Finder.System.entity.BusBooking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BusBookingRepository extends JpaRepository<BusBooking, Long> {
    List<BusBooking> findByUserId(Long userId);
}
