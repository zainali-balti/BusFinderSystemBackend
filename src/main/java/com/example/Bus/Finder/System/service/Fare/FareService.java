package com.example.Bus.Finder.System.service.Fare;

import com.example.Bus.Finder.System.dto.FareDto;
import com.example.Bus.Finder.System.entity.Bus;
import com.example.Bus.Finder.System.entity.Fare;

import java.util.List;
import java.util.Map;

public interface FareService {
    void deleteFare(Long fareId);
    Fare updateFare(Long fareId, FareDto fareDto);
    Fare getFareById(Long fareId);
    List<Fare> getAllFares();
    Fare createFare(FareDto fareDto);
    List<FareDto> getFareByBusId(Long busId);
    List<Map<String, Object>> getBusesWithFareAndSchedule(Long sourceStopId, Long destinationStopId);
}
