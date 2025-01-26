package com.example.Bus.Finder.System.service.Report;

import com.example.Bus.Finder.System.dto.ReportDto;
import com.example.Bus.Finder.System.dto.TimeDto;
import com.example.Bus.Finder.System.entity.Booking;
import com.example.Bus.Finder.System.repository.BookingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReportServiceImplementation implements ReportService{
    @Autowired
    private BookingRepository bookingRepository;

    public ResponseEntity<List<ReportDto>> getAll(TimeDto timeDto) {
        try {
            // Fetch bookings between the start and end date

            LocalDateTime startDateTime = timeDto.getStartDate().atStartOfDay();
            LocalDateTime endDateTime = timeDto.getEndDate().atTime(23, 59, 59);
            List<Booking> bookings = bookingRepository.findBookingsBetweenDates(startDateTime, endDateTime);

            // Convert the bookings to ReportDto
            List<ReportDto> reportDTOList = bookings.stream()
                    .map(booking -> {
                        ReportDto reportDto = new ReportDto();
                        reportDto.setId(booking.getId());
                        reportDto.setCustomerId(booking.getUser().getId());
                        reportDto.setCustomerName(booking.getUser().getFirstName());  // assuming there's a name in User entity
                        reportDto.setVehicleId(booking.getVehicles().getId());
                        reportDto.setVehicleName(booking.getVehicles().getVehicleName());  // assuming vehicle name field exists
                        reportDto.setBookingDate(booking.getBookingDate().toLocalDate());  // Convert LocalDateTime to LocalDate
                        reportDto.setEndDate(booking.getEndDate().toLocalDate());  // Convert LocalDateTime to LocalDate
                        reportDto.setPrice(booking.getTotalPrice());
                        reportDto.setDistance(booking.getDistance());
                        return reportDto;
                    })
                    .collect(Collectors.toList());

            return new ResponseEntity<>(reportDTOList, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>(new ArrayList<>(), HttpStatus.BAD_REQUEST);
    }

}
