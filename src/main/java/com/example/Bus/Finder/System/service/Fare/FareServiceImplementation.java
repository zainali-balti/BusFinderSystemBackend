package com.example.Bus.Finder.System.service.Fare;

import com.example.Bus.Finder.System.dto.BusScheduleDto;
import com.example.Bus.Finder.System.dto.FareDto;
import com.example.Bus.Finder.System.entity.*;
import com.example.Bus.Finder.System.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class FareServiceImplementation implements FareService{
    @Autowired
    private FareRepository fareRepository;

    @Autowired
    private BusRouteRepository routeRepository;

    @Autowired
    private BusStopRepository busStopRepository;
    @Autowired
    private BusRepository busRepository;
    @Autowired
    private BusScheduleRepository busScheduleRepository;

    public Fare createFare(FareDto fareDto) {
        BusRoute route = routeRepository.findById(fareDto.getBusRouteId())
                .orElseThrow(() -> new RuntimeException("Route not found"));
        Bus bus = busRepository.findById(fareDto.getBusId())
                .orElseThrow(() -> new RuntimeException("Bus  not found"));

        Fare fare = new Fare();
        fare.setBusRoute(route);
        fare.setBus(bus);
        fare.setFare(fareDto.getFare());

        return fareRepository.save(fare);
    }

    public List<Fare> getAllFares() {
        return fareRepository.findAll();
    }

    public Fare getFareById(Long fareId) {
        return fareRepository.findById(fareId)
                .orElseThrow(() -> new RuntimeException("Fare not found"));
    }

    public Fare updateFare(Long fareId, FareDto fareDto) {
        Fare existingFare = fareRepository.findById(fareId)
                .orElseThrow(() -> new RuntimeException("Fare not found"));

        if (fareDto.getBusRouteId() != null) {
            BusRoute updatedRoute = routeRepository.findById(fareDto.getBusRouteId())
                    .orElseThrow(() -> new RuntimeException("Route not found"));
            existingFare.setBusRoute(updatedRoute);
        }
        if (fareDto.getBusId() != null) {
            Bus bus = busRepository.findById(fareDto.getBusId())
                    .orElseThrow(() -> new RuntimeException("Bus not found"));
            existingFare.setBus(bus);
        }

        if (fareDto.getFare() != null) {
            existingFare.setFare(fareDto.getFare());
        }

        return fareRepository.save(existingFare);
    }

    public void deleteFare(Long fareId) {
        Fare fare = fareRepository.findById(fareId)
                .orElseThrow(() -> new RuntimeException("Fare not found"));
        fareRepository.delete(fare);
    }

    public List<FareDto> getFareByBusId(Long busId) {
        List<Fare> fares = fareRepository.findByBus_BusId(busId);
        if (fares.isEmpty()) {
            throw new RuntimeException("No fares found for the given busId");
        }
        List<FareDto> fareDtos = fares.stream()
                .map(fare -> new FareDto(
                        fare.getFareId(),
                        fare.getBus().getBusId(),
                        fare.getBusRoute().getId(),
                        fare.getFare()

                ))
                .collect(Collectors.toList());

        return fareDtos;
    }

    public List<Map<String, Object>> getBusesWithFareAndSchedule(Long sourceStopId, Long destinationStopId) {
            List<Fare> fares = fareRepository.findBusesBySourceAndDestination(sourceStopId, destinationStopId);
            List<Map<String, Object>> responseList = new ArrayList<>();

            for (Fare fare : fares) {
                Bus bus = fare.getBus();
                Optional<BusSchedule> scheduleOpt = busScheduleRepository.findByBus_BusIdAndRoute_Id(
                        bus.getBusId(), fare.getBusRoute().getId());

                Map<String, Object> busData = new HashMap<>();
                busData.put("busId", bus.getBusId());
                busData.put("busName", bus.getBusName());
                busData.put("busNumber", bus.getBusNumber());
                busData.put("capacity", bus.getCapacity());
                busData.put("img",bus.getImg());
                busData.put("status",bus.getStatus());
                busData.put("fare", fare.getFare());
                busData.put("fareId",fare.getFareId());

                if (scheduleOpt.isPresent()) {
                    BusSchedule schedule = scheduleOpt.get();
                    busData.put("arrivalTime", schedule.getArrivalTime());
                    busData.put("departureTime", schedule.getDepartureTime());
                } else {
                    busData.put("arrivalTime", "Not Available");
                    busData.put("departureTime", "Not Available");
                }

                responseList.add(busData);
            }

            return responseList;
        }

}
