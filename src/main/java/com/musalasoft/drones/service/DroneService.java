package com.musalasoft.drones.service;

import com.musalasoft.drones.dto.DroneBatteryResponse;
import com.musalasoft.drones.dto.DroneMedicationResponse;
import com.musalasoft.drones.entity.Drone;
import com.musalasoft.drones.entity.Medication;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface DroneService {
    Drone registerDrone(Drone drone);

    DroneMedicationResponse loadDrone(Long droneId, List<Medication> medication) throws Exception;

    DroneMedicationResponse getDroneMedications(long droneId) throws Exception;

    List<Drone> getAvailableDrones();

    DroneBatteryResponse getDroneBatteryLevel(long droneId) throws Exception;


    Drone getDrone(Long droneId) throws Exception;

    Page<Drone> getAll(Pageable pageable);
}
