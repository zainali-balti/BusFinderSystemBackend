package com.example.Bus.Finder.System.service.Route;


import com.example.Bus.Finder.System.dto.RouteDto;
import com.example.Bus.Finder.System.entity.Route;

import java.util.List;

public interface RouteService {
    Route addRouteBus(Long busId, Long busStopId, RouteDto routeDto);
    Route getRouteById(Long routeId);
    List<RouteDto> getAllRoutes();
    void deleteRoute(Long routeId);
    Route updateRoute(Long routeId, RouteDto routeDto, Long busId, Long busStopId);
}
