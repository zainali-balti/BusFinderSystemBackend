package com.example.Bus.Finder.System.repository;

import com.example.Bus.Finder.System.entity.BusRoute;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BusRouteRepository extends JpaRepository<BusRoute,Long> {
}
