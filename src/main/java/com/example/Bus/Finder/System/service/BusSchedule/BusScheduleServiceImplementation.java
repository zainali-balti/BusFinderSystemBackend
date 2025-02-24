package com.example.Bus.Finder.System.service.BusSchedule;

import com.example.Bus.Finder.System.dto.BusScheduleDto;
import com.example.Bus.Finder.System.entity.Bus;
import com.example.Bus.Finder.System.entity.BusSchedule;
import com.example.Bus.Finder.System.entity.BusStop;
import com.example.Bus.Finder.System.entity.BusRoute;
import com.example.Bus.Finder.System.repository.BusRepository;
import com.example.Bus.Finder.System.repository.BusScheduleRepository;
import com.example.Bus.Finder.System.repository.BusStopRepository;
import com.example.Bus.Finder.System.repository.BusRouteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BusScheduleServiceImplementation implements BusScheduleService{
    @Autowired
    private BusScheduleRepository busScheduleRepository;
    @Autowired
    private BusRepository busRepository;
    @Autowired
    private BusRouteRepository routeRepository;
    @Autowired
    private BusStopRepository busStopRepository;

    public BusSchedule createBusSchedule(BusScheduleDto busScheduleDto){
        Bus existingBus = busRepository.findById(busScheduleDto.getBusId())
                .orElseThrow(() -> new RuntimeException("Bus not found"));
        BusRoute route = routeRepository.findById(busScheduleDto.getBusRouteId())
                .orElseThrow(() -> new RuntimeException("Bus Route not found"));
        BusSchedule busSchedule = new BusSchedule();
        busSchedule.setBus(existingBus);
        busSchedule.setRoute(route);
        busSchedule.setArrivalTime(busScheduleDto.getArrivalTime());
        busSchedule.setDepartureTime(busScheduleDto.getDepartureTime());
        return busScheduleRepository.save(busSchedule);
    }
    public BusSchedule getBusScheduleById(Long id) {
        return busScheduleRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Bus Schedule not found"));
    }
    public BusSchedule updateBusSchedule(Long scheduleId,BusScheduleDto busScheduleDto) {
        BusSchedule existingBusSchedule = busScheduleRepository.findById(scheduleId)
                .orElseThrow(() -> new RuntimeException("Bus Schedule not found"));

        if (busScheduleDto.getBusId() != null) {
            Bus updatedBus = busRepository.findById(busScheduleDto.getBusId())
                    .orElseThrow(() -> new RuntimeException("Bus not found"));
            existingBusSchedule.setBus(updatedBus);
        }
        if (busScheduleDto.getBusRouteId() != null) {
            BusRoute updatedRoute = routeRepository.findById(busScheduleDto.getBusRouteId())
                    .orElseThrow(() -> new RuntimeException("Bus Route not found"));
            existingBusSchedule.setRoute(updatedRoute);
        }
        if (busScheduleDto.getArrivalTime() != null) {
            existingBusSchedule.setArrivalTime(busScheduleDto.getArrivalTime());
        }

        if (busScheduleDto.getDepartureTime() != null) {
            existingBusSchedule.setDepartureTime(busScheduleDto.getDepartureTime());
        }

        return busScheduleRepository.save(existingBusSchedule);
    }
    public void deleteBusSchedule(Long id) {
        BusSchedule existingBusSchedule = busScheduleRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Bus Schedule not found"));
        busScheduleRepository.delete(existingBusSchedule);
    }
    public List<BusSchedule> getAllBusSchedules() {
        return busScheduleRepository.findAll();
    }
}
