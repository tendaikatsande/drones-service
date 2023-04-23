package com.musalasoft.drones.service;

import com.musalasoft.drones.entity.DroneBatteryLog;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface DroneBatteryCheckService {
    void checkBatteryStatus();

    Page<DroneBatteryLog> getAll(Pageable pageable);
}
