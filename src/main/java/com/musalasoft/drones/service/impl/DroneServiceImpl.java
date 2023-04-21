package com.musalasoft.drones.service.impl;

import com.musalasoft.drones.dto.DroneBatteryResponse;
import com.musalasoft.drones.dto.DroneMedicationResponse;
import com.musalasoft.drones.entity.Consignment;
import com.musalasoft.drones.entity.Drone;
import com.musalasoft.drones.entity.Medication;
import com.musalasoft.drones.helper.DroneState;
import com.musalasoft.drones.repository.ConsignmentRepository;
import com.musalasoft.drones.repository.DroneRepository;
import com.musalasoft.drones.repository.MedicationRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class DroneServiceImpl implements DroneService {

    private final DroneRepository droneRepository;
    private final ConsignmentRepository consignmentRepository;

    private final MedicationRepository medicationRepository;

    @Override
    public Drone registerDrone(Drone drone) {
        return droneRepository.save(drone);
    }

    @Override
    public List<Medication> loadDrone(Long droneId, List<Medication> medication) throws Exception {
        Drone drone = getDrone(droneId);
        //check if current consignment can accomodate the medication
        //load drone or reject loaded
        return List.of();
    }

    @Override
    public DroneMedicationResponse getDroneMedications(long droneId) throws Exception {
        Drone drone = getDrone(droneId);
        //get drone current consignment
        return new DroneMedicationResponse(droneId, List.of());

    }

    @Override
    public List<Drone> getAvailableDrones() {
        return droneRepository.findAllByState(DroneState.IDLE);
    }

    @Override
    public DroneBatteryResponse getDroneBatteryLevel(long droneId) throws Exception {
        DroneBatteryResponse droneBatteryResponse = DroneBatteryResponse.builder()
                .droneId(droneId).batteryLevel(getDrone(droneId).getBatteryCapacity())
                .build();
        return droneBatteryResponse;
    }

    @Override
    public Drone getDrone(Long droneId) throws Exception {
        Optional<Drone> drone = droneRepository.findById(droneId);
        if (drone.isPresent()) {
            return drone.get();
        }
        throw new Exception("Drone not found");
    }


    private List<Consignment> getDroneCurrentConsignment(Long droneId) {
        return List.of();
    }
}
