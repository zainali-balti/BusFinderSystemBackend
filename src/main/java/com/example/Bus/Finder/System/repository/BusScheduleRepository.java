package com.example.Bus.Finder.System.repository;

import com.example.Bus.Finder.System.entity.BusSchedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BusScheduleRepository extends JpaRepository<BusSchedule,Long> {
    Optional<BusSchedule> findByBus_BusIdAndRoute_Id(Long busId, Long routeId);
    Optional<BusSchedule> findByBus_BusId(Long busId);

}

