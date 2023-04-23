package com.musalasoft.drones.service.impl;

import com.musalasoft.drones.entity.Drone;
import com.musalasoft.drones.entity.DroneBatteryLog;
import com.musalasoft.drones.repository.DroneBatteryLogRepository;
import com.musalasoft.drones.service.DroneBatteryCheckService;
import com.musalasoft.drones.service.DroneService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
@Slf4j
public class DroneBatteryCheckServiceImpl implements DroneBatteryCheckService {

    private final DroneService droneService;

    private final DroneBatteryLogRepository droneBatteryLogRepository;

    @Scheduled(fixedDelay = 60000)
    @Override
    public void checkBatteryStatus() {
        log.info("Checking Battery Status Started");
        int pageSize = 2;
        Slice<Drone> drones = droneService.getAll(Pageable.ofSize(pageSize));
        processDrones(drones);
        while (drones.hasNext()) {
            drones = droneService.getAll(drones.nextPageable());
            processDrones(drones);
        }
        log.info("Checking Battery Status Done");

    }

    private void processDrones(Slice<Drone> drones) {
        List<DroneBatteryLog> droneBatteryLogs = new ArrayList<>();
        drones.get().forEach(drone -> {
            droneBatteryLogs.add(DroneBatteryLog.builder()
                    .drone(drone)
                    .batteryLevel(drone.getBatteryCapacity())
                    .createdAt(Instant.now())
                    .build());
        });
        log.info("Batch : {} ", drones);
        droneBatteryLogRepository.saveAll(droneBatteryLogs);
    }
}
