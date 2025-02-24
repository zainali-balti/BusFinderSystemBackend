package com.example.Bus.Finder.System.service.BusSchedule;

import com.example.Bus.Finder.System.dto.BusScheduleDto;
import com.example.Bus.Finder.System.entity.BusSchedule;

import java.util.List;

public interface BusScheduleService {
    BusSchedule createBusSchedule(BusScheduleDto busScheduleDto);
    BusSchedule getBusScheduleById(Long id);
    List<BusSchedule> getAllBusSchedules();
    void deleteBusSchedule(Long id);
    BusSchedule updateBusSchedule(Long scheduleId,BusScheduleDto busScheduleDto);

}
