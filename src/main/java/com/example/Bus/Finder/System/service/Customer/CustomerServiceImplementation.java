package com.example.Bus.Finder.System.service.Customer;

import com.example.Bus.Finder.System.dto.VehicleDto;
import com.example.Bus.Finder.System.entity.Vehicle;
import com.example.Bus.Finder.System.repository.VehicleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerServiceImplementation implements CustomerService{
    @Autowired
    private VehicleRepository vehicleRepository;
    public List<VehicleDto> FindAllVehicles(){
        List<Vehicle> vehicles = vehicleRepository.findAll();
        return vehicles.stream()
                .map(Vehicle::getAddVehicleDto)
                .toList();
    }
    public List<VehicleDto> FindVehicleName(String vehicleName){
        List<Vehicle> vehicles = vehicleRepository.findByVehicleNameIgnoreCase(vehicleName);
        return vehicles.stream()
                .map(Vehicle::getAddVehicleDto)
                .toList();
    }
}
