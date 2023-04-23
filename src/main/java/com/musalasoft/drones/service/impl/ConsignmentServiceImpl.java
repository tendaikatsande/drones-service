package com.musalasoft.drones.service.impl;

import com.musalasoft.drones.entity.Consignment;
import com.musalasoft.drones.entity.Drone;
import com.musalasoft.drones.entity.Medication;
import com.musalasoft.drones.helper.ConsignmentState;
import com.musalasoft.drones.repository.ConsignmentRepository;
import com.musalasoft.drones.service.ConsignmentService;
import com.musalasoft.drones.service.MedicationService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class ConsignmentServiceImpl implements ConsignmentService {


    private final MedicationService medicationService;

    private final ConsignmentRepository consignmentRepository;

    @Override
    public List<Consignment> getDroneConsignment(Drone drone) {
        return consignmentRepository.findAllByDroneIdAndState(drone.getId(), ConsignmentState.LOADED);
    }

    @Override
    public float getDroneConsignmentWeight(Drone drone) {
        return getDroneConsignment(drone).stream().map(consignment -> consignment.getMedication().getWeight()).reduce(0f, Float::sum);
    }

    @Override
    public void addMedicationToDroneConsignment(Drone drone, Medication medication) throws Exception {
        Consignment consignment = Consignment.builder()
                .state(ConsignmentState.LOADED)
                .drone(drone)
                .medication(medication)
                .build();
        if (consignment.getMedication().getAllocated())
            throw new Exception("Medication is already part of a certain consignment");
        consignmentRepository.save(consignment);
        medicationService.markAsAllocated(medication.getId());

    }

    @Override
    public void removeMedicationFromDroneConsignment(Drone drone, Medication medication) {
        consignmentRepository.deleteAllByDroneIdAndMedicationId(drone.getId(), medication.getId());
    }

    @Override
    public void deliverConsignment(Drone drone) {
        List<Consignment> consignments = getDroneConsignment(drone);
        consignments.forEach(consignment -> consignment.setState(ConsignmentState.DELIVERED));
        consignmentRepository.saveAll(consignments);
    }

    @Override
    public Boolean canLoadMedication(Drone drone, float medicationWeight) {
        if (drone.getBatteryCapacity() < 25)
            throw new IllegalStateException("Drone Battery capacity is less than 25% and can not load");
        float remainingWeight = drone.getWeightLimit() - (getDroneConsignmentWeight(drone) + medicationWeight);
        if (remainingWeight < 0)
            throw new IllegalArgumentException("Exceeds weight limit by " + remainingWeight);
        return true;
    }
}
