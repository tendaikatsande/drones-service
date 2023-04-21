package com.musalasoft.drones.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DroneBatteryResponse {
    private Long droneId;
    private float batteryLevel;
}
