package com.example.Bus.Finder.System.repository;

import com.example.Bus.Finder.System.dto.ReportDto;
import com.example.Bus.Finder.System.entity.Booking;
import com.example.Bus.Finder.System.entity.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface BookingRepository extends JpaRepository<Booking,Long> {
    boolean existsByVehiclesAndEndDateAfter(Vehicle vehicle, LocalDateTime now);
    @Query("SELECT b FROM Booking b WHERE b.bookingDate >= :startDate AND b.endDate <= :endDate")
    List<Booking> findBookingsBetweenDates(@Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate);
    List<Booking> findByEndDateBefore(LocalDateTime now);
    List<Booking> findByUserId(Long userId);

}
