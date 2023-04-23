package com.musalasoft.drones.repository;

import com.musalasoft.drones.entity.Consignment;
import com.musalasoft.drones.helper.ConsignmentState;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ConsignmentRepository extends JpaRepository<Consignment, Long> {
    List<Consignment> findAllByDroneIdAndState(Long droneId, ConsignmentState loaded);

    void deleteAllByDroneIdAndMedicationId(Long droneId, Long medicationId);
}
