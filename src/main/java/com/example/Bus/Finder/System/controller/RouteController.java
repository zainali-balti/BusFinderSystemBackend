package com.example.Bus.Finder.System.controller;

import com.example.Bus.Finder.System.dto.RouteDto;
import com.example.Bus.Finder.System.service.Route.RouteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/route")
public class RouteController {

    @Autowired
    private  RouteService routeService;


    @PostMapping
    public ResponseEntity<RouteDto> addRoute(@RequestBody RouteDto routeDto) {
        RouteDto newRoute = routeService.addRoute(routeDto);
        return ResponseEntity.ok(newRoute);
    }
    @PutMapping("/{id}")
    public ResponseEntity<RouteDto> updateRoute(@PathVariable Long id, @RequestBody RouteDto routeDto) {
        RouteDto updatedRoute = routeService.updateRoute(id, routeDto);
        return ResponseEntity.ok(updatedRoute);
    }
    @GetMapping
    public ResponseEntity<List<RouteDto>> getAllRoutes() {
        List<RouteDto> routes = routeService.getAllRoutes();
        return ResponseEntity.ok(routes);
    }
    @GetMapping("/{id}")
    public ResponseEntity<RouteDto> getRouteById(@PathVariable Long id) {
        RouteDto route = routeService.getRouteById(id);
        return ResponseEntity.ok(route);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteRoute(@PathVariable Long id) {
        routeService.deleteRoute(id);
        return ResponseEntity.ok("Route deleted successfully with ID: " + id);
    }
}
