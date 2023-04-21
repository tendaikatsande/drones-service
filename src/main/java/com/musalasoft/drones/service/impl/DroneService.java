package com.musalasoft.drones.service.impl;

import com.musalasoft.drones.dto.DroneBatteryResponse;
import com.musalasoft.drones.dto.DroneMedicationResponse;
import com.musalasoft.drones.entity.Drone;
import com.musalasoft.drones.entity.Medication;

import java.util.List;

public interface DroneService {
    Drone registerDrone(Drone drone);

    List<Medication> loadDrone(Long droneId, List<Medication> medication) throws Exception;

    DroneMedicationResponse getDroneMedications(long droneId) throws Exception;

    List<Drone> getAvailableDrones();

    DroneBatteryResponse getDroneBatteryLevel(long droneId) throws Exception;


    Drone getDrone(Long droneId) throws Exception;
}
