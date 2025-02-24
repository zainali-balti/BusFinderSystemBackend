package com.example.Bus.Finder.System.service.SearchingForBus;

import com.example.Bus.Finder.System.dto.FareDto;
import com.example.Bus.Finder.System.dto.SearchForBusDto;
import com.example.Bus.Finder.System.entity.Fare;
import com.example.Bus.Finder.System.repository.BusStopRepository;
import com.example.Bus.Finder.System.repository.FareRepository;
import com.example.Bus.Finder.System.service.Fare.FareService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SearchServiceImplementation implements  SearchService{
    @Autowired
    private FareRepository fareRepository;
    @Autowired
    private BusStopRepository busStopRepository;
    @Autowired
    private FareService fareService;

//    @Override
//    public List<Fare> getAllBusBySourceAndDestination(SearchForBusDto searchForBusDto) {
//        return fareService.getFaresByStops(searchForBusDto.getSourceStopId(),searchForBusDto.getDestinationStopId());
//    }
}
