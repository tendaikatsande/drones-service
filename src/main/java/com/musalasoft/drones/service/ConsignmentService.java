package com.musalasoft.drones.service;

import com.musalasoft.drones.entity.Consignment;

import java.util.List;

public interface ConsignmentService {
    List<Consignment> getDroneConsignment(Long droneId);

    float getDroneConsignmentWeight(Long droneId);

    void addMedicationToDroneConsignment(Long droneId, Long medicationId) throws Exception;

    void removeMedicationToDroneConsignment(Long droneId, Long medicationId) throws Exception;

    void deliverConsignment(Long droneId);


    Boolean canLoadMedication(Long droneId, float medicationWeight) throws Exception;
}
