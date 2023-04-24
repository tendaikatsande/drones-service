package com.musalasoft.drones.dto;

import com.musalasoft.drones.helper.DroneState;
import lombok.Data;

@Data
public class DroneStateChangeRequest {
    DroneState state;
}
