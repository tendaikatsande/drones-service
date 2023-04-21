package com.musalasoft.drones.repository;

import com.musalasoft.drones.entity.Drone;
import com.musalasoft.drones.helper.DroneState;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DroneRepository extends JpaRepository<Drone, Long> {
    List<Drone> findAllByState(DroneState state);
}
