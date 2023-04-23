package com.musalasoft.drones.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Entity
@Table
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DroneBatteryLog {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "drone_battery_log_gen")
    @SequenceGenerator(name = "drone_battery_log_gen", sequenceName = "drone_battery_log_seq")
    @Column(name = "id", nullable = false)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "drone_id", nullable = false)
    private Drone drone;

    @Column(name = "battery_level", nullable = false)
    private float batteryLevel;

    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

}
