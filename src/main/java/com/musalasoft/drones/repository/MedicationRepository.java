package com.musalasoft.drones.repository;

import com.musalasoft.drones.entity.Medication;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface MedicationRepository extends JpaRepository<Medication, Long> {
    List<Medication> findByAllocated(boolean allocated);
}
