package com.musalasoft.drones.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.musalasoft.drones.entity.Medication;
import com.musalasoft.drones.repository.MedicationRepository;
import com.musalasoft.drones.service.MedicationService;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class MedicationServiceImpl implements MedicationService{
    private final MedicationRepository medicationRepository;

    @Override
    public List<Medication> getAll() {
      return   medicationRepository.findAll();
    }

    @Override
    public List<Medication> getUnallocated() {
      return   medicationRepository.findByAllocated(false);
    }

    @Override
    public List<Medication> getAllocated() {
        return medicationRepository.findByAllocated(true);
    }
    
}
