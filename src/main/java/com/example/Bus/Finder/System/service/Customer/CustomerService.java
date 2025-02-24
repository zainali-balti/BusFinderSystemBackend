package com.example.Bus.Finder.System.service.Customer;

import com.example.Bus.Finder.System.dto.UserDto;
import com.example.Bus.Finder.System.dto.VehicleDto;

import java.util.List;

public interface CustomerService {
    List<VehicleDto> FindVehicleName(String vehicleName);
    List<VehicleDto> FindAllVehicles();
    UserDto findUserById(Long userId);
}
