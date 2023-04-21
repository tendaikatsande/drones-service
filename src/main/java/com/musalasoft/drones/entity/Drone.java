package com.musalasoft.drones.entity;

import com.musalasoft.drones.helper.DroneModel;
import com.musalasoft.drones.helper.DroneState;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Drone {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "drone_gen")
    @SequenceGenerator(name = "drone_gen", sequenceName = "drone_seq")
    @Column(name = "id", nullable = false)
    private Long id;


    @NotNull(message = "Drone Serial Number can not be null")
    @Column(name = "serial_number", length = 100, nullable = false)
    private String serialNumber;

    @NotNull(message = "Drone Model can not be null")
    @Column(name = "model", nullable = false)
    private DroneModel model;

    @NotNull(message = "Drone Weight Limit can not be null")
    @Column(name = "weight_limit", nullable = false)
    private float weightLimit;


    @NotNull(message = "Drone Battery Capacity can not be null")
    @Column(name = "battery_capacity", nullable = false)
    private float batteryCapacity;

    @NotNull(message = "Drone State can not be null")
    @Column(name = "state", nullable = false)
    private DroneState state;


}

