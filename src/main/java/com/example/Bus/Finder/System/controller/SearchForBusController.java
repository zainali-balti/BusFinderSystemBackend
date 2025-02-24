package com.example.Bus.Finder.System.controller;

import com.example.Bus.Finder.System.dto.BusScheduleDto;
import com.example.Bus.Finder.System.dto.SearchForBusDto;
import com.example.Bus.Finder.System.entity.BusSchedule;
import com.example.Bus.Finder.System.entity.Fare;
import com.example.Bus.Finder.System.repository.BusScheduleRepository;
import com.example.Bus.Finder.System.service.SearchingForBus.SearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/search")
public class SearchForBusController {
    @Autowired
    private SearchService service;
    @Autowired
    private BusScheduleRepository busScheduleRepository;


}
