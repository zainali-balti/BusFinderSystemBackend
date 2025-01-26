package com.example.Bus.Finder.System.controller;

import com.example.Bus.Finder.System.dto.RouteDto;
import com.example.Bus.Finder.System.entity.Route;
import com.example.Bus.Finder.System.service.Route.RouteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/route")
public class RouteController {

    @Autowired
    private RouteService routeService;

    @PostMapping("/add/{busId}/{busStopId}")
    public ResponseEntity<String> createRouteForBuses(
            @PathVariable Long busId,
            @PathVariable Long busStopId,
            @RequestBody RouteDto routeDto){

        Route route = routeService.addRouteBus(busId,busStopId,routeDto);
        return ResponseEntity.ok("Route Created For Bus Successfully "+route);
    }
    @PutMapping("/update/{routeId}/{busId}/{busStopId}")
    public ResponseEntity<Route> updateRoute(
            @PathVariable Long routeId,
            @PathVariable Long busId,
            @PathVariable Long busStopId,
            @RequestBody RouteDto routeDto) {
        Route updatedRoute = routeService.updateRoute(routeId, routeDto, busId, busStopId);
        return ResponseEntity.ok(updatedRoute);
    }

    @DeleteMapping("/delete/{routeId}")
    public ResponseEntity<String> deleteRoute(@PathVariable Long routeId) {
        routeService.deleteRoute(routeId);
        return ResponseEntity.ok("Route with ID " + routeId + " deleted successfully.");
    }

    @GetMapping("/all")
    public ResponseEntity<List<RouteDto>> getAllRoutes() {
        List<RouteDto> routes = routeService.getAllRoutes();
        return ResponseEntity.ok(routes);
    }

    @GetMapping("/{routeId}")
    public ResponseEntity<Route> getRouteById(@PathVariable Long routeId) {
        Route route = routeService.getRouteById(routeId);
        return ResponseEntity.ok(route);
    }
}
