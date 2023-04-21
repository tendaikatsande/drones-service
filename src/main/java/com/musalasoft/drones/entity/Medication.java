package com.musalasoft.drones.entity;

import jakarta.persistence.*;
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
public class Medication {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "medication_gen")
    @SequenceGenerator(name = "medication_gen", sequenceName = "medication_seq")
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "weight", nullable = false)
    private float weight;


    @Column(name = "code", nullable = false)
    private String code;


    @Column(name = "image")
    private String image;

}
