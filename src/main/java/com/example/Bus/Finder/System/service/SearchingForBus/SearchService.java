package com.example.Bus.Finder.System.service.SearchingForBus;

import com.example.Bus.Finder.System.dto.SearchForBusDto;
import com.example.Bus.Finder.System.entity.Fare;
import org.springframework.stereotype.Service;

import java.util.List;


public interface SearchService{
    List<Fare> getAllBusBySourceAndDestination(SearchForBusDto searchForBusDto);
}
