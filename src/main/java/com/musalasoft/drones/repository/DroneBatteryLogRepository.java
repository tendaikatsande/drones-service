package com.musalasoft.drones.repository;

import com.musalasoft.drones.entity.DroneBatteryLog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DroneBatteryLogRepository extends JpaRepository<DroneBatteryLog, Long> {
}
