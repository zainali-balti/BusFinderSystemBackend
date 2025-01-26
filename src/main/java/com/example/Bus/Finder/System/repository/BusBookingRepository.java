package com.example.Bus.Finder.System.repository;

import com.example.Bus.Finder.System.entity.BusBooking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BusBookingRepository extends JpaRepository<BusBooking, Long> {
}
