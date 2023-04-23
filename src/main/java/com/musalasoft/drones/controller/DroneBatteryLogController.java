package com.musalasoft.drones.controller;

import com.musalasoft.drones.service.DroneBatteryCheckService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Tag(name = "Battery log Routes", description = "Routes to access drone battery logs")
@RequestMapping("/api/battery-logs")
@AllArgsConstructor
public class DroneBatteryLogController {

    private final DroneBatteryCheckService droneBatteryCheckService;

    @GetMapping
    public ResponseEntity<?> getBatteryLogs(Pageable pageable) {
        return ResponseEntity.ok(droneBatteryCheckService.getAll(pageable));
    }

}
