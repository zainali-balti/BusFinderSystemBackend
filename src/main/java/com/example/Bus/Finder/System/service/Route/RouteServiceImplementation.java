package com.example.Bus.Finder.System.service.Route;

import com.example.Bus.Finder.System.dto.RouteDto;
import com.example.Bus.Finder.System.entity.Route;
import com.example.Bus.Finder.System.repository.RouteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RouteServiceImplementation implements RouteService{


    @Autowired
    private RouteRepository routeRepository;

    // ✅ Add a new route
    @Override
    public RouteDto addRoute(RouteDto routeDto) {
        Route route = new Route();
        route.setName(routeDto.getRouteName());

        Route savedRoute = routeRepository.save(route);
        return mapToDto(savedRoute);
    }

    // ✅ Update an existing route
    @Override
    public RouteDto updateRoute(Long id, RouteDto routeDto) {
        Route route = routeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Route not found with ID: " + id));

        route.setName(routeDto.getRouteName());
        Route updatedRoute = routeRepository.save(route);
        return mapToDto(updatedRoute);
    }

    // ✅ Get all routes
    @Override
    public List<RouteDto> getAllRoutes() {
        List<Route> routes = routeRepository.findAll();
        return routes.stream()
                .map(this::mapToDto)  // Use helper method
                .collect(Collectors.toList());
    }

    // ✅ Get route by ID
    @Override
    public RouteDto getRouteById(Long id) {
        Route route = routeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Route not found with ID: " + id));
        return mapToDto(route);
    }

    // ✅ Delete route by ID
    @Override
    public void deleteRoute(Long id) {
        if (!routeRepository.existsById(id)) {
            throw new RuntimeException("Route not found with ID: " + id);
        }
        routeRepository.deleteById(id);
    }

    private RouteDto mapToDto(Route route) {
        RouteDto dto = new RouteDto();
        dto.setRouteId(route.getId());
        dto.setRouteName(route.getName());
        return dto;
    }
}
