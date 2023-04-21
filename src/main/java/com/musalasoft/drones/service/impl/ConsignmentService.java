package com.musalasoft.drones.service.impl;

import com.musalasoft.drones.entity.Consignment;

import java.util.List;

public interface ConsignmentService {
    List<Consignment> getDroneConsignment(Long droneId);

    float getDroneConsignmentWeight(Long droneId);

    void addMedicationToDroneConsignment(Long droneId, Long medicationId);

    Boolean canLoadMedication(Long droneId, float medicationWeight) throws Exception;
}
