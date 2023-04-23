package com.musalasoft.drones.service.impl;

import com.musalasoft.drones.entity.Consignment;
import com.musalasoft.drones.entity.Drone;
import com.musalasoft.drones.helper.ConsignmentState;
import com.musalasoft.drones.repository.ConsignmentRepository;
import com.musalasoft.drones.service.ConsignmentService;
import com.musalasoft.drones.service.DroneService;
import com.musalasoft.drones.service.MedicationService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class ConsignmentServiceImpl implements ConsignmentService {

    private final DroneService droneService;

    private final MedicationService medicationService;

    private final ConsignmentRepository consignmentRepository;

    @Override
    public List<Consignment> getDroneConsignment(Long droneId) {
        return consignmentRepository.findAllByDroneIdAndState(droneId, ConsignmentState.LOADED);
    }

    @Override
    public float getDroneConsignmentWeight(Long droneId) {
        return getDroneConsignment(droneId).stream().map(consignment -> consignment.getMedication().getWeight()).reduce(0f, Float::sum);
    }

    @Override
    public void addMedicationToDroneConsignment(Long droneId, Long medicationId) throws Exception {
        Consignment consignment = Consignment.builder()
                .state(ConsignmentState.LOADED)
                .drone(droneService.getDrone(droneId))
                .medication(medicationService.getMedication(medicationId))
                .build();
        if (consignment.getMedication().getAllocated())
            throw new Exception("Medication is already part of a certain consignment");
        consignmentRepository.save(consignment);
        medicationService.markAsAllocated(medicationId);

    }

    @Override
    public void removeMedicationToDroneConsignment(Long droneId, Long medicationId) {
        consignmentRepository.deleteAllByDroneIdAndMedicationId(droneId, medicationId);
    }

    @Override
    public void deliverConsignment(Long droneId) {
        List<Consignment> consignments = getDroneConsignment(droneId);
        consignments.forEach(consignment -> consignment.setState(ConsignmentState.DELIVERED));
        consignmentRepository.saveAll(consignments);
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
