package com.musalasoft.drones.service.impl;

import com.musalasoft.drones.dto.DroneBatteryResponse;
import com.musalasoft.drones.dto.DroneMedicationResponse;
import com.musalasoft.drones.entity.Consignment;
import com.musalasoft.drones.entity.Drone;
import com.musalasoft.drones.entity.Medication;
import com.musalasoft.drones.helper.DroneState;
import com.musalasoft.drones.repository.DroneRepository;
import com.musalasoft.drones.service.ConsignmentService;
import com.musalasoft.drones.service.DroneService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class DroneServiceImpl implements DroneService {

    private final DroneRepository droneRepository;
    private final ConsignmentService consignmentService;

    @Override
    public Drone registerDrone(Drone drone) {
        return droneRepository.save(drone);
    }

    @Override
    public DroneMedicationResponse loadDrone(Long droneId, List<Medication> medications) throws Exception {
        Drone drone = getDrone(droneId);
        //check if current consignment can accommodate the medication
        Optional<Float> medicationWeight = medications.stream().map(Medication::getWeight).reduce(Float::sum);
        if (medicationWeight.isPresent()) {
            float currentLoadWeight = consignmentService.getDroneConsignmentWeight(drone);
            if ((currentLoadWeight + medicationWeight.get()) > drone.getWeightLimit()) {
                throw new Exception("Weight limit exceeded");
            }
        } else {
            throw new Exception("Weight of medication could not be determined");
        }

        medications.forEach(medication -> {
            try {
                consignmentService.addMedicationToDroneConsignment(drone, medication);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });

        return getDroneMedications(droneId);
    }

    @Override
    public DroneMedicationResponse getDroneMedications(long droneId) throws Exception {
        Drone drone = getDrone(droneId);
        //get drone current consignment
        List<Consignment> currentConsignment = consignmentService.getDroneConsignment(drone);
        List<Medication> medications = new ArrayList<>();
        currentConsignment.forEach(consignment -> medications.add(consignment.getMedication()));
        return new DroneMedicationResponse(droneId, medications);

    }

    @Override
    public List<Drone> getAvailableDrones() {
        return droneRepository.findAllByState(DroneState.IDLE);
    }

    @Override
    public DroneBatteryResponse getDroneBatteryLevel(long droneId) throws Exception {
        return DroneBatteryResponse.builder()
                .droneId(droneId).batteryLevel(getDrone(droneId).getBatteryCapacity())
                .build();
    }

    @Override
    public Drone getDrone(Long droneId) throws Exception {
        Optional<Drone> drone = droneRepository.findById(droneId);
        if (drone.isPresent()) {
            return drone.get();
        }
        throw new Exception("Drone not found");
    }

    @Override
    public Page<Drone> getAll(Pageable pageable) {
        return droneRepository.findAll(pageable);
    }

    @Override
    public Drone updateDroneState(Long droneId, DroneState state) throws Exception {
        Drone drone = getDrone(droneId);
        drone.setState(state);
        return droneRepository.save(drone);
    }


}
