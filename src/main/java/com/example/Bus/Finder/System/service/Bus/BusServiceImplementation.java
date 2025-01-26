package com.example.Bus.Finder.System.service.Bus;

import com.example.Bus.Finder.System.dto.BusDto;
import com.example.Bus.Finder.System.entity.Bus;
import com.example.Bus.Finder.System.entity.User;
import com.example.Bus.Finder.System.enums.Status;
import com.example.Bus.Finder.System.repository.BusRepository;
import com.example.Bus.Finder.System.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Slf4j
@Service
public class BusServiceImplementation implements BusService{

    @Autowired
    private BusRepository busRepository;
    @Autowired
    private UserRepository userRepository;

    public Bus addBuses(Long userId,BusDto busDto){
        User user = userRepository.findById(userId)
            .orElseThrow(() -> new RuntimeException("User not found"));
        Bus bus = new Bus();
        bus.setBusNumber(busDto.getBusNumber());
        bus.setBusName(busDto.getBusName());
        bus.setCapacity(busDto.getCapacity());
        bus.setStatus(Status.ACTIVE);
        return busRepository.save(bus);
    }
    public Bus updateBus(Long busId, BusDto busDto) {
        Bus existingBus = busRepository.findById(busId)
                .orElseThrow(() -> new RuntimeException("Bus not found"));
        existingBus.setBusNumber(busDto.getBusNumber());
        existingBus.setBusName(busDto.getBusName());
        existingBus.setCapacity(busDto.getCapacity());
        existingBus.setStatus(busDto.getStatus() != null ? busDto.getStatus() : existingBus.getStatus());
        return busRepository.save(existingBus);
    }
    public void deleteBus(Long busId) {
        log.info("Attempting to delete Bus with ID: {}", busId);
        Bus bus = busRepository.findById(busId)
                .orElseThrow(() -> new RuntimeException("Bus not found"));
        log.info("Bus found: {}", bus);
        busRepository.delete(bus);
        log.info("Bus deleted successfully");
    }
    public List<Bus> getAllBuses() {
        return busRepository.findAll();
    }
    public Bus getBusById(Long busId) {
        return busRepository.findById(busId)
                .orElseThrow(() -> new RuntimeException("Bus not found"));
    }

}
