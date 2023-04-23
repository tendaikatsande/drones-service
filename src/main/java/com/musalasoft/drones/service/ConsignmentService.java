package com.musalasoft.drones.service;

import com.musalasoft.drones.entity.Consignment;
import com.musalasoft.drones.entity.Drone;
import com.musalasoft.drones.entity.Medication;

import java.util.List;

public interface ConsignmentService {
    List<Consignment> getDroneConsignment(Drone drone);

    float getDroneConsignmentWeight(Drone drone);


    void addMedicationToDroneConsignment(Drone drone, Medication medication) throws Exception;

    void removeMedicationFromDroneConsignment(Drone drone, Medication medication) throws Exception;

    void deliverConsignment(Drone drone);


    Boolean canLoadMedication(Drone drone, float medicationWeight) throws Exception;

}
