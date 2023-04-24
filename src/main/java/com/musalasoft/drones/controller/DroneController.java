package com.musalasoft.drones.controller;

import com.musalasoft.drones.dto.DroneBatteryResponse;
import com.musalasoft.drones.dto.DroneMedicationResponse;
import com.musalasoft.drones.dto.DroneStateChangeRequest;
import com.musalasoft.drones.entity.Drone;
import com.musalasoft.drones.entity.Medication;
import com.musalasoft.drones.service.DroneService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Tag(name = "Drone Routes", description = "Routes to access drone functionalities")
@RequestMapping("/api/drones")

@AllArgsConstructor
public class DroneController {

    private final DroneService droneService;

    @PostMapping
    public ResponseEntity<Drone> registerDrone(@RequestBody Drone drone) {
        return ResponseEntity.ok(droneService.registerDrone(drone));
    }

    @PostMapping("/{droneId}/medications")
    public ResponseEntity<?> loadDrone(@PathVariable Long droneId, @RequestBody List<Medication> medications) throws Exception {
        return ResponseEntity.ok(droneService.loadDrone(droneId, medications));
    }

    @GetMapping("/{droneId}/medications")
    private ResponseEntity<DroneMedicationResponse> getDroneMedications(@PathVariable Long droneId) throws Exception {
        return ResponseEntity.ok(droneService.getDroneMedications(droneId));
    }

    @GetMapping("/available")
    private ResponseEntity<List<Drone>> getDronesAvailable() {
        return ResponseEntity.ok(droneService.getAvailableDrones());
    }

    @GetMapping("/{droneId}/battery")
    private ResponseEntity<DroneBatteryResponse> getDroneBatteryLevel(@PathVariable Long droneId) throws Exception {
        return ResponseEntity.ok(droneService.getDroneBatteryLevel(droneId));
    }

    @PatchMapping("/{droneId}/change-state")
    private ResponseEntity<Drone> setDroneState(@PathVariable Long droneId, @RequestBody DroneStateChangeRequest droneStateChangeRequest) throws Exception {
        return ResponseEntity.ok(droneService.updateDroneState(droneId, droneStateChangeRequest.getState()));
    }


}
