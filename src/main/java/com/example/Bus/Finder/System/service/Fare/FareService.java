package com.example.Bus.Finder.System.service.Fare;

import com.example.Bus.Finder.System.dto.FareDto;
import com.example.Bus.Finder.System.entity.Fare;

import java.util.List;

public interface FareService {
    void deleteFare(Long fareId);
    Fare updateFare(Long fareId, FareDto fareDto);
    Fare getFareById(Long fareId);
    List<Fare> getAllFares();
    Fare createFare(FareDto fareDto);
    List<Fare> getFaresByStops(Long sourceStopId, Long destinationStopId);
}
