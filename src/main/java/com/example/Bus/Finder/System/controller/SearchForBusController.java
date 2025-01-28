package com.example.Bus.Finder.System.controller;

import com.example.Bus.Finder.System.dto.SearchForBusDto;
import com.example.Bus.Finder.System.entity.Fare;
import com.example.Bus.Finder.System.entity.SearchingForBus;
import com.example.Bus.Finder.System.service.SearchingForBus.SearchService;
import com.example.Bus.Finder.System.service.SearchingForBus.SearchServiceImplementation;
import jakarta.persistence.Access;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/search")
public class SearchForBusController {
    @Autowired
    private SearchService service;

    @PostMapping("/all")
    public ResponseEntity<List<Fare>> getAllBusBySourceAndDestination(@RequestBody SearchForBusDto searchForBusDto){
        List<Fare> fares = service.getAllBusBySourceAndDestination(searchForBusDto);
        return ResponseEntity.ok(fares);
    }
}
