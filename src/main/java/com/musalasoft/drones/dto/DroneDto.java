package com.musalasoft.drones.dto;

import com.musalasoft.drones.helper.DroneModel;
import com.musalasoft.drones.helper.DroneState;

public class DroneDto {
    private Long id;
    private String serialNumber;
    private DroneModel model;
    private float weightLimit;
    private float batteryCapacity;
    private DroneState state;
}
