package com.example.Bus.Finder.System.service.BusRoute;


import com.example.Bus.Finder.System.dto.BusRouteDto;
import com.example.Bus.Finder.System.entity.BusRoute;

import java.util.List;

public interface BusRouteService {
    BusRoute addRouteBus(BusRouteDto routeDto);
    BusRoute getRouteById(Long busRouteId);
    List<BusRouteDto> getAllRoutes();
    void deleteRoute(Long busRouteId);
    BusRoute updateRoute(Long busRouteId, BusRouteDto routeDto);
}
