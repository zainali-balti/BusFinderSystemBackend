package com.example.Bus.Finder.System.controller;

import com.example.Bus.Finder.System.dto.BusRouteDto;
import com.example.Bus.Finder.System.entity.BusRoute;
import com.example.Bus.Finder.System.service.BusRoute.BusRouteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/route")
public class BusRouteController {

    @Autowired
    private BusRouteService routeService;

    @PostMapping("/add")
    public ResponseEntity<?> createRouteForBuses(
            @RequestBody BusRouteDto routeDto){
        BusRoute route = routeService.addRouteBus(routeDto);

        // Return a structured response with routeId and a success message
        Map<String, Object> response = new HashMap<>();
        response.put("message", "Route Created Successfully");
        response.put("routeId", route.getId());

        return ResponseEntity.ok(response);
    }
    @PutMapping("/update/{busRouteId}")
    public ResponseEntity<BusRoute> updateRoute(
            @PathVariable Long busRouteId,
            @RequestBody BusRouteDto routeDto) {
        BusRoute updatedRoute = routeService.updateRoute(busRouteId, routeDto);
        return ResponseEntity.ok(updatedRoute);
    }

    @DeleteMapping("/delete/{busRouteId}")
    public ResponseEntity<String> deleteRoute(@PathVariable Long busRouteId) {
        routeService.deleteRoute(busRouteId);
        return ResponseEntity.ok("Route with ID " + busRouteId + " deleted successfully.");
    }

    @GetMapping("/all")
    public ResponseEntity<List<BusRouteDto>> getAllRoutes() {
        List<BusRouteDto> routes = routeService.getAllRoutes();
        return ResponseEntity.ok(routes);
    }

    @GetMapping("/id/{busRouteId}")
    public ResponseEntity<BusRoute> getRouteById(@PathVariable Long busRouteId) {
        BusRoute route = routeService.getRouteById(busRouteId);
        return ResponseEntity.ok(route);
    }
}
