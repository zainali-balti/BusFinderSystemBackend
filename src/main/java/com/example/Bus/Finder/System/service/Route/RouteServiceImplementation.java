package com.example.Bus.Finder.System.service.Route;

import com.example.Bus.Finder.System.dto.RouteDto;
import com.example.Bus.Finder.System.entity.Bus;
import com.example.Bus.Finder.System.entity.BusStop;
import com.example.Bus.Finder.System.entity.Route;
import com.example.Bus.Finder.System.repository.BusRepository;
import com.example.Bus.Finder.System.repository.BusStopRepository;
import com.example.Bus.Finder.System.repository.RouteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RouteServiceImplementation implements RouteService{
    @Autowired
    private BusRepository busRepository;
    @Autowired
    private RouteRepository routeRepository;
    @Autowired
    private BusStopRepository busStopRepository;

    public Route addRouteBus(Long busId,Long busStopId, RouteDto routeDto){
        Bus bus = busRepository.findById(busId)
                .orElseThrow(() -> new RuntimeException("Bus not found"));
        BusStop busStop = busStopRepository.findById(busStopId)
                .orElseThrow(() -> new RuntimeException("Bus Stop not found"));
        Route route = new Route();
        route.setBus(bus);
        route.setStop(busStop);
        route.setStopSequence(routeDto.getStopSequence());
        return  routeRepository.save(route);
    }
    public Route updateRoute(Long routeId, RouteDto routeDto, Long busId, Long busStopId) {
        Route existingRoute = routeRepository.findById(routeId)
                .orElseThrow(() -> new RuntimeException("Route not found"));

        Bus bus = busRepository.findById(busId)
                .orElseThrow(() -> new RuntimeException("Bus not found"));

        BusStop busStop = busStopRepository.findById(busStopId)
                .orElseThrow(() -> new RuntimeException("Bus Stop not found"));

        existingRoute.setBus(bus);
        existingRoute.setStop(busStop);
        existingRoute.setStopSequence(routeDto.getStopSequence());

        return routeRepository.save(existingRoute);
    }
    public void deleteRoute(Long routeId) {
        Route route = routeRepository.findById(routeId)
                .orElseThrow(() -> new RuntimeException("Route not found"));
        routeRepository.delete(route);
    }
    public List<RouteDto> getAllRoutes() {
        List<Route> routes = routeRepository.findAll();
        return routes.stream().map(Route::getAllRoute).toList();
    }
    public Route getRouteById(Long routeId) {
        return routeRepository.findById(routeId)
                .orElseThrow(() -> new RuntimeException("Route not found"));
    }

}
