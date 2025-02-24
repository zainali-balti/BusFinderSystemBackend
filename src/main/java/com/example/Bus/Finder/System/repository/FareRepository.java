package com.example.Bus.Finder.System.repository;

import com.example.Bus.Finder.System.dto.FareDto;
import com.example.Bus.Finder.System.entity.Bus;
import com.example.Bus.Finder.System.entity.BusStop;
import com.example.Bus.Finder.System.entity.Fare;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FareRepository extends JpaRepository<Fare,Long> {


        @Query("SELECT f FROM Fare f " +
                "JOIN f.busRoute br " +
                "JOIN f.bus b " +
                "JOIN BusSchedule bs ON bs.bus = b AND bs.route = br " +
                "WHERE br.start.stopId = :sourceStopId AND br.stop.stopId = :destinationStopId")
        List<Fare> findBusesBySourceAndDestination(
                @Param("sourceStopId") Long sourceStopId,
                @Param("destinationStopId") Long destinationStopId
        );


    @Query("SELECT f FROM Fare f JOIN FETCH f.bus WHERE f.fareId = :fareId")
    Optional<Fare> findByIdWithBus(@Param("fareId") Long fareId);

    List<Fare> findByBus_BusId(Long busId);
}
