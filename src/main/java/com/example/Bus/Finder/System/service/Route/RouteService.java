package com.example.Bus.Finder.System.service.Route;


import com.example.Bus.Finder.System.dto.RouteDto;

import java.util.List;

public interface RouteService {
    RouteDto addRoute(RouteDto routeDto);
    RouteDto updateRoute(Long id, RouteDto routeDto);
    List<RouteDto> getAllRoutes();
    RouteDto getRouteById(Long id);
    void deleteRoute(Long id);
}
