package com.example.Bus.Finder.System.service.BusRoute;

import com.example.Bus.Finder.System.dto.BusRouteDto;
import com.example.Bus.Finder.System.entity.BusStop;
import com.example.Bus.Finder.System.entity.BusRoute;
import com.example.Bus.Finder.System.entity.Route;
import com.example.Bus.Finder.System.repository.BusRepository;
import com.example.Bus.Finder.System.repository.BusStopRepository;
import com.example.Bus.Finder.System.repository.BusRouteRepository;
import com.example.Bus.Finder.System.repository.RouteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BusRouteServiceImplementation implements BusRouteService {
    @Autowired
    private BusRepository busRepository;
    @Autowired
    private RouteRepository routeRepository;
    @Autowired
    private BusRouteRepository busRouteRepository;
    @Autowired
    private BusStopRepository busStopRepository;

    public BusRoute addRouteBus(BusRouteDto routeDto){
        Route route = routeRepository.findById(routeDto.getRouteId())
                .orElseThrow(() -> new RuntimeException("Route not found"));
        BusStop busStart = busStopRepository.findById(routeDto.getSourceStopId())
                .orElseThrow(() -> new RuntimeException("Bus Start not found"));
        BusStop busStop = busStopRepository.findById(routeDto.getDestinationStopId())
                .orElseThrow(() -> new RuntimeException("Bus Stop not found"));

        BusRoute busRoute = new BusRoute();
        busRoute.setRoute(route);
        busRoute.setStart(busStart);
        busRoute.setStop(busStop);
        busRoute.setSequence(routeDto.getSequence());
        return  busRouteRepository.save(busRoute);
    }
    public BusRoute updateRoute(Long busRouteId, BusRouteDto routeDto) {
        BusRoute existingRoute = busRouteRepository.findById(busRouteId)
                .orElseThrow(() -> new RuntimeException("Route not found"));
        BusStop busStart = busStopRepository.findById(routeDto.getSourceStopId())
                .orElseThrow(() -> new RuntimeException("Bus Start not found"));
        BusStop busStop = busStopRepository.findById(routeDto.getDestinationStopId())
                .orElseThrow(() -> new RuntimeException("Bus Stop not found"));
        existingRoute.setStart(busStart);
        existingRoute.setStop(busStop);
        existingRoute.setSequence(routeDto.getSequence());
        return busRouteRepository.save(existingRoute);
    }
    public void deleteRoute(Long busRouteId) {
        BusRoute route = busRouteRepository.findById(busRouteId)
                .orElseThrow(() -> new RuntimeException("Route not found"));
        busRouteRepository.delete(route);
    }
    public List<BusRouteDto> getAllRoutes() {
        List<BusRoute> routes = busRouteRepository.findAll();
        return routes.stream().map(BusRoute::getAllRoute).toList();
    }
    public BusRoute getRouteById(Long busRouteId) {
        return busRouteRepository.findById(busRouteId)
                .orElseThrow(() -> new RuntimeException("Route not found"));
    }

}
