package com.example.Bus.Finder.System.service.BusSchedule;

import com.example.Bus.Finder.System.dto.BusScheduleDto;
import com.example.Bus.Finder.System.entity.Bus;
import com.example.Bus.Finder.System.entity.BusSchedule;
import com.example.Bus.Finder.System.entity.BusStop;
import com.example.Bus.Finder.System.entity.Route;
import com.example.Bus.Finder.System.repository.BusRepository;
import com.example.Bus.Finder.System.repository.BusScheduleRepository;
import com.example.Bus.Finder.System.repository.BusStopRepository;
import com.example.Bus.Finder.System.repository.RouteRepository;
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
    private RouteRepository routeRepository;
    @Autowired
    private BusStopRepository busStopRepository;

    public BusSchedule createBusSchedule(Long busId, Long busStopId, Long routeId, BusScheduleDto busScheduleDto){
        Bus existingBus = busRepository.findById(busId)
                .orElseThrow(() -> new RuntimeException("Bus not found"));
        BusStop busStop = busStopRepository.findById(busStopId)
                .orElseThrow(() -> new RuntimeException("Bus Stop not found"));
        Route route = routeRepository.findById(routeId)
                .orElseThrow(() -> new RuntimeException("Bus Route not found"));
        BusSchedule busSchedule = new BusSchedule();
        busSchedule.setBus(existingBus);
        busSchedule.setStop(busStop);
        busSchedule.setRoute(route);
        busSchedule.setSequence(busScheduleDto.getSequence());
        busSchedule.setArrivalTime(busScheduleDto.getArrivalTime());
        busSchedule.setDepartureTime(busScheduleDto.getDepartureTime());
        return busScheduleRepository.save(busSchedule);
    }
    public BusSchedule getBusScheduleById(Long id) {
        return busScheduleRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Bus Schedule not found"));
    }
    public BusSchedule updateBusSchedule(Long scheduleId,Long busId, Long busStopId, Long routeId, BusScheduleDto busScheduleDto) {
        BusSchedule existingBusSchedule = busScheduleRepository.findById(scheduleId)
                .orElseThrow(() -> new RuntimeException("Bus Schedule not found"));

        if (busScheduleDto.getScheduleId() != null) {
            Bus updatedBus = busRepository.findById(busId)
                    .orElseThrow(() -> new RuntimeException("Bus not found"));
            existingBusSchedule.setBus(updatedBus);
        }

        if (busStopId != null) {
            BusStop updatedBusStop = busStopRepository.findById(busStopId)
                    .orElseThrow(() -> new RuntimeException("Bus Stop not found"));
            existingBusSchedule.setStop(updatedBusStop);
        }

        if (routeId != null) {
            Route updatedRoute = routeRepository.findById(routeId)
                    .orElseThrow(() -> new RuntimeException("Bus Route not found"));
            existingBusSchedule.setRoute(updatedRoute);
        }

        existingBusSchedule.setSequence(busScheduleDto.getSequence());

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
