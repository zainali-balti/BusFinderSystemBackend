package com.example.Bus.Finder.System.service.BusStop;

import com.example.Bus.Finder.System.dto.BusStopDto;
import com.example.Bus.Finder.System.entity.Bus;
import com.example.Bus.Finder.System.entity.BusStop;
import com.example.Bus.Finder.System.repository.BusRepository;
import com.example.Bus.Finder.System.repository.BusStopRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BusStopImplementation implements BusStopService{

    @Autowired
    private BusStopRepository busStopRepository;
    @Autowired
    private BusRepository busRepository;

    public BusStop addBusStops(Long busId, BusStopDto busStopDto){
        Bus existingBus = busRepository.findById(busId)
                .orElseThrow(() -> new RuntimeException("Bus not found"));
        BusStop busStop = new BusStop();
        busStop.setName(busStopDto.getName());
        return  busStopRepository.save(busStop);
    }
    public BusStop updateBusStop(Long busStopId, BusStopDto busStopDto) {
        BusStop existingBusStop = busStopRepository.findById(busStopId)
                .orElseThrow(() -> new RuntimeException("BusStop not found"));
        existingBusStop.setName(busStopDto.getName());
        return busStopRepository.save(existingBusStop);
    }
    public void deleteBusStop(Long busStopId) {
        BusStop busStop = busStopRepository.findById(busStopId)
                .orElseThrow(() -> new RuntimeException("BusStop not found"));
        busStopRepository.delete(busStop);
    }
    public List<BusStop> getAllBusStops() {
        return busStopRepository.findAll();
    }
    public BusStop getBusStopById(Long busStopId) {
        return busStopRepository.findById(busStopId)
                .orElseThrow(() -> new RuntimeException("BusStop not found"));
    }

}
