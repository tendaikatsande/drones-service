package com.musalasoft.drones.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
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
    @Pattern(regexp = "^[A-Za-z0-9_-]+$", message = "Name can only contain letters, numbers, hyphens, and underscores")
    private String name;

    @Column(name = "weight", nullable = false)
    @Positive(message = "Weight must be greater than 0")
    private float weight;


    @Column(name = "code", nullable = false)
    @Pattern(regexp = "^[A-Z_]+$", message = "Code can only contain uppercase letters and underscores")
    private String code;


    @Column(name = "image")
    private String image;

}
