package com.musalasoft.drones.entity;

import com.musalasoft.drones.helper.ConsignmentState;
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
public class Consignment {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "consignment_gen")
    @SequenceGenerator(name = "consignment_gen", sequenceName = "consignment_seq")
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "drone_id")
    private Drone drone;
    @ManyToOne
    @JoinColumn(name = "medication_id")
    private Medication medication;
    
    @Column(name = "state", nullable = false)
    private ConsignmentState state = ConsignmentState.LOADED;

    @Column(name = "dateCreated")
    private Instant dateCreated = Instant.now();

}
