package com.musalasoft.drones.entity;

import com.musalasoft.drones.helper.DroneModel;
import com.musalasoft.drones.helper.DroneState;
import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
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
    @Pattern(regexp = "^[a-zA-Z0-9\\-_]*$", message = "Invalid serial number")
    private String serialNumber;

    @NotNull(message = "Drone Model can not be null")
    @Column(name = "model", nullable = false)
    @Enumerated(EnumType.STRING)
    private DroneModel model;

    @NotNull(message = "Drone Weight Limit can not be null")
    @Column(name = "weight_limit", nullable = false)
    @DecimalMax(value = "500.00", message = "Weight limit should not be more than 500 grams")
    private float weightLimit;

    @NotNull(message = "Drone Battery Capacity can not be null")
    @Column(name = "battery_capacity", nullable = false)
    @Min(value = 0, message = "Battery capacity should be between 0 and 100")
    @Max(value = 100, message = "Battery capacity should be between 0 and 100")
    private float batteryCapacity;

    @NotNull(message = "Drone State can not be null")
    @Column(name = "state", nullable = false)
    @Enumerated(EnumType.STRING)
    private DroneState state;

}
