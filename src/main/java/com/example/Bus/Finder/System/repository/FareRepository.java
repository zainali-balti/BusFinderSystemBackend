package com.example.Bus.Finder.System.repository;

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
    List<Fare> findBySourceStopAndDestinationStop(BusStop sourceStop, BusStop destinationStop);
    @Query("SELECT f FROM Fare f JOIN FETCH f.bus WHERE f.fareId = :fareId")
    Optional<Fare> findByIdWithBus(@Param("fareId") Long fareId);

}
