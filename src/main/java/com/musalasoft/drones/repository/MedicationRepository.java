package com.musalasoft.drones.repository;

import com.musalasoft.drones.entity.Medication;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MedicationRepository extends JpaRepository<Medication, Long> {
}
