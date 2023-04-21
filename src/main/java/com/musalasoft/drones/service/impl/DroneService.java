package com.musalasoft.drones.service.impl;

import com.musalasoft.drones.entity.Drone;
import com.musalasoft.drones.entity.Medication;

import java.util.List;

public interface DroneService {
    Drone registerDrone(Drone drone);

    List<Medication> loadDrone(Long droneId, List<Medication> medication) throws Exception;

    List<Medication> getDroneMedications(long droneId) throws Exception;

    List<Drone> getAvailableDrones();

    Float getDroneBatteryLevel(long droneId) throws Exception;
}
