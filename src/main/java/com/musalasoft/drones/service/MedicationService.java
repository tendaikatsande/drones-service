package com.musalasoft.drones.service;

import java.util.List;

import com.musalasoft.drones.entity.Medication;

public interface MedicationService {
    public List<Medication> getAll();
    public List<Medication> getUnallocated();
    public List<Medication> getAllocated();
    
}
