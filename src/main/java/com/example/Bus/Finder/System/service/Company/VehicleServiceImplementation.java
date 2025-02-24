package com.example.Bus.Finder.System.service.Company;

import com.example.Bus.Finder.System.dto.VehicleDto;
import com.example.Bus.Finder.System.entity.Vehicle;
import com.example.Bus.Finder.System.entity.User;
import com.example.Bus.Finder.System.repository.VehicleRepository;
import com.example.Bus.Finder.System.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class VehicleServiceImplementation implements VehicleService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private VehicleRepository vehicleRepository;

    public boolean addVehicle(Long userId, VehicleDto addVehicleDto){
        Optional<User> optionalUser = userRepository.findById(userId);
        if (optionalUser.isPresent()){
            Vehicle addVehicles = new Vehicle();
            addVehicles.setVehicleName(addVehicleDto.getVehicleName());
            addVehicles.setColor(addVehicleDto.getColor());
            addVehicles.setDescription(addVehicleDto.getDescription());
            addVehicles.setDistance(addVehicleDto.getDistance());
            addVehicles.setEngineNo(addVehicleDto.getEngineNo());
            addVehicles.setModelNo(addVehicleDto.getModelNo());
            addVehicles.setPrice(addVehicleDto.getPrice());
            addVehicles.setTotalSeats(addVehicleDto.getTotalSeats());
            addVehicles.setUser(optionalUser.get());
            // Handle image conversion from MultipartFile to byte[]
            if (addVehicleDto.getImg() != null && !addVehicleDto.getImg().isEmpty()) {
                try {
                    byte[] imageBytes = addVehicleDto.getImg().getBytes();
                    addVehicles.setImg(imageBytes);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            vehicleRepository.save(addVehicles);
            return true;
        }
        return false;
    }
    public List<VehicleDto> FindOwnerVehicle(Long userId){
        List<Vehicle> vehicles = vehicleRepository.findAllByUserId(userId);
        return vehicles.stream()
                .map(Vehicle::getAddVehicleDto)
                .toList();
    }
public List<VehicleDto> FindAllVehicles() {
    List<Vehicle> vehicles = vehicleRepository.findAll();
    return vehicles.stream()
            .map(Vehicle::getAddVehicleDto)
            .collect(Collectors.toList());
}
    public boolean updateVehicle(Long vehicleId, Long userId, VehicleDto updatedVehicleDto, MultipartFile img) {
        Optional<Vehicle> optionalVehicle = vehicleRepository.findById(vehicleId);
        if (optionalVehicle.isPresent()) {
            Vehicle existingVehicle = optionalVehicle.get();
            if (existingVehicle.getUser().getId().equals(userId)) {
                existingVehicle.setVehicleName(updatedVehicleDto.getVehicleName());
                existingVehicle.setColor(updatedVehicleDto.getColor());
                existingVehicle.setDescription(updatedVehicleDto.getDescription());
                existingVehicle.setDistance(updatedVehicleDto.getDistance());
                existingVehicle.setEngineNo(updatedVehicleDto.getEngineNo());
                existingVehicle.setModelNo(updatedVehicleDto.getModelNo());
                existingVehicle.setPrice(updatedVehicleDto.getPrice());
                existingVehicle.setTotalSeats(updatedVehicleDto.getTotalSeats());

                // Handle image conversion if a new image is provided
                if (img != null && !img.isEmpty()) {
                    try {
                        byte[] imageBytes = img.getBytes();
                        existingVehicle.setImg(imageBytes);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                vehicleRepository.save(existingVehicle);
                return true;
            }
        }
        return false;
    }

    public boolean deleteVehicle(Long vehicleId, Long userId) {
        Optional<Vehicle> optionalVehicle = vehicleRepository.findById(vehicleId);
        if (optionalVehicle.isPresent()) {
            Vehicle existingVehicle = optionalVehicle.get();
            if (existingVehicle.getUser().getId().equals(userId)) {
                vehicleRepository.delete(existingVehicle);
                return true;
            }
        }
        return false;
    }

    public VehicleDto findVehicleById(Long vehicleId) {
        return vehicleRepository.findById(vehicleId)
                .map(Vehicle::getAddVehicleDto) // Convert to DTO if present
                .orElseThrow(() -> new RuntimeException("Vehicle not found with ID: " + vehicleId));
    }





}
