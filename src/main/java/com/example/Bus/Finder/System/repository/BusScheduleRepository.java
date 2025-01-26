package com.example.Bus.Finder.System.repository;

import com.example.Bus.Finder.System.entity.BusSchedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BusScheduleRepository extends JpaRepository<BusSchedule,Long> {
}
