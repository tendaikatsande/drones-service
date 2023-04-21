package com.musalasoft.drones.service.impl;

import com.musalasoft.drones.entity.Consignment;
import com.musalasoft.drones.entity.Drone;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class ConsignmentServiceImpl implements ConsignmentService {

    private final DroneService droneService;

    @Override
    public List<Consignment> getDroneConsignment(Long droneId) {
        return null;
    }

    @Override
    public float getDroneConsignmentWeight(Long droneId) {
        return 0;
    }

    @Override
    public void addMedicationToDroneConsignment(Long droneId, Long medicationId) {

    }

    @Override
    public Boolean canLoadMedication(Long droneId, float medicationWeight) throws Exception {
        Drone drone = droneService.getDrone(droneId);
        if (drone.getBatteryCapacity() < 25)
            throw new IllegalStateException("Drone Battery capacity is less than 25% and can not load");
        float remainingWeight = drone.getWeightLimit() - (getDroneConsignmentWeight(droneId) + medicationWeight);
        if (remainingWeight < 0)
            throw new IllegalArgumentException("Exceeds weight limit by " + remainingWeight);
        return true;
    }
}
