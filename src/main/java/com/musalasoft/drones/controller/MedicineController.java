package com.musalasoft.drones.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.musalasoft.drones.service.MedicationService;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;

@RestController
@Tag(name = "Medicine Routes", description = "Medicine to access drone functionalities")
@RequestMapping("/api/medicines")

@AllArgsConstructor
public class MedicineController {
    private final MedicationService medicationService;

    @GetMapping
    public ResponseEntity<?>getAll(){
        return ResponseEntity.ok(medicationService.getAll());
    }

    @GetMapping("/allocated")
    public ResponseEntity<?>getAllocated(){
        return ResponseEntity.ok(medicationService.getAllocated());
    }

    @GetMapping("/unallocated")
    public ResponseEntity<?>getUnAllocated(){
        return ResponseEntity.ok(medicationService.getUnallocated());
    }
    
}
