package com.example.Bus.Finder.System.repository;

import com.example.Bus.Finder.System.entity.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VehicleRepository extends JpaRepository<Vehicle,Long> {
    List<Vehicle> findAllByUserId(Long userId);
    List<Vehicle> findByVehicleNameIgnoreCase(String vehicleName);
}
