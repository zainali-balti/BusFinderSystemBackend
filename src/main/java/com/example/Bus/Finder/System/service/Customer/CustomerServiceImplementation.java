package com.example.Bus.Finder.System.service.Customer;

import com.example.Bus.Finder.System.dto.UserDto;
import com.example.Bus.Finder.System.dto.VehicleDto;
import com.example.Bus.Finder.System.entity.User;
import com.example.Bus.Finder.System.entity.Vehicle;
import com.example.Bus.Finder.System.repository.UserRepository;
import com.example.Bus.Finder.System.repository.VehicleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerServiceImplementation implements CustomerService{
    @Autowired
    private VehicleRepository vehicleRepository;
    @Autowired
    private UserRepository userRepository;
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
    public UserDto findUserById(Long userId){
        return userRepository.findById(userId)
                .map(User::getDto)
                .orElseThrow(() -> new RuntimeException("User not found with ID: " + userId));
    }
}
