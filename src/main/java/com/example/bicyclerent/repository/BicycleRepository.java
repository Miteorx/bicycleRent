package com.example.bicyclerent.repository;

import com.example.bicyclerent.model.Bicycle;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BicycleRepository extends JpaRepository<Bicycle, Long> {

}
