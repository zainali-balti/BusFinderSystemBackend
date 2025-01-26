package com.example.Bus.Finder.System.repository;

import com.example.Bus.Finder.System.entity.Fare;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FareRepository extends JpaRepository<Fare,Long> {
}
