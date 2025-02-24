package com.example.Bus.Finder.System.service.BusStop;


import com.example.Bus.Finder.System.dto.BusStopDto;
import com.example.Bus.Finder.System.entity.BusStop;

import java.util.List;

public interface BusStopService {
    BusStop addBusStops(BusStopDto busStopDto);
    BusStop getBusStopById(Long busStopId);
    List<BusStop> getAllBusStops();
    void deleteBusStop(Long busStopId);
    BusStop updateBusStop(Long busStopId, BusStopDto busStopDto);
}
