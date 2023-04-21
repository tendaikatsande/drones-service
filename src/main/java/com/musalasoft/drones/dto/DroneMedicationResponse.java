package com.musalasoft.drones.dto;

import com.musalasoft.drones.entity.Medication;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DroneMedicationResponse {
    private Long droneId;
    private List<Medication> medications;
}
