package com.musalasoft.drones.service;

import com.musalasoft.drones.entity.Medication;

import java.util.List;

public interface MedicationService {
    List<Medication> getAll();

    List<Medication> getUnallocated();

    List<Medication> getAllocated();

    Medication getMedication(Long medicationId) throws Exception;

    void markAsAllocated(Long medicationId) throws Exception;
}
