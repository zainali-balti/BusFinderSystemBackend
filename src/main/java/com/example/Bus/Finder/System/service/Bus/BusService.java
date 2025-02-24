package com.example.Bus.Finder.System.service.Bus;


import com.example.Bus.Finder.System.dto.BusDto;
import com.example.Bus.Finder.System.entity.Bus;

import java.util.List;

public interface BusService {
    Bus addBuses(BusDto busDto);
    Bus getBusById(Long busId);
    List<Bus> getAllBuses();
    void deleteBus(Long busId);
    Bus updateBus(Long busId, BusDto busDto);
    List<Bus> getBusesByUserId(Long userId);
}
