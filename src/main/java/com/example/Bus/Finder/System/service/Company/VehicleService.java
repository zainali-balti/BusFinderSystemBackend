package com.example.Bus.Finder.System.service.Company;

import com.example.Bus.Finder.System.dto.VehicleDto;

import java.util.List;

public interface VehicleService {
    boolean addVehicle(Long userId, VehicleDto addVehicleDto);
    List<VehicleDto> FindOwnerVehicle(Long ownerId);
    List<VehicleDto> FindAllVehicles();
    boolean updateVehicle(Long vehicleId, Long userId, VehicleDto updatedVehicleDto);
    boolean deleteVehicle(Long vehicleId, Long userId);
}
