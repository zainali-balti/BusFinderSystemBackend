package com.example.Bus.Finder.System.repository;

import com.example.Bus.Finder.System.entity.Bus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BusRepository extends JpaRepository<Bus,Long> {
}
