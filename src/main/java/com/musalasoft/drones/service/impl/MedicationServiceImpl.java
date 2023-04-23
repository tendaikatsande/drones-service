package com.musalasoft.drones.service.impl;

import com.musalasoft.drones.entity.Medication;
import com.musalasoft.drones.repository.MedicationRepository;
import com.musalasoft.drones.service.MedicationService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class MedicationServiceImpl implements MedicationService {
    private final MedicationRepository medicationRepository;

    @Override
    public List<Medication> getAll() {
        return medicationRepository.findAll();
    }

    @Override
    public List<Medication> getUnallocated() {
        return medicationRepository.findByAllocated(false);
    }

    @Override
    public List<Medication> getAllocated() {
        return medicationRepository.findByAllocated(true);
    }

    @Override
    public Medication getMedication(Long medicationId) throws Exception {
        Optional<Medication> medication = medicationRepository.findById(medicationId);
        if (medication.isPresent()) {
            return medication.get();
        }
        throw new Exception("Medication not found");
    }

    @Override
    public void markAsAllocated(Long medicationId) throws Exception {
        Medication medication = getMedication(medicationId);
        medication.setAllocated(true);
        medicationRepository.save(medication);
    }

}
