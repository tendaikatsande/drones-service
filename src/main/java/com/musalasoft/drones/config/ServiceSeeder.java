package com.musalasoft.drones.config;

import com.github.javafaker.Faker;
import com.github.javafaker.service.FakeValuesService;
import com.musalasoft.drones.entity.Drone;
import com.musalasoft.drones.entity.Medication;
import com.musalasoft.drones.helper.DroneModel;
import com.musalasoft.drones.helper.DroneState;
import com.musalasoft.drones.repository.DroneRepository;
import com.musalasoft.drones.repository.MedicationRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

@Configuration
@AllArgsConstructor
@Slf4j
public class ServiceSeeder implements CommandLineRunner {

    private final DroneRepository droneRepository;

    private final MedicationRepository medicationRepository;

    private final Faker faker;


    @Override
    public void run(String... args) throws Exception {

        long count = droneRepository.count();
        if (count == 0) {
            log.info("Seeding 10 Drones");
            List<Drone> drones = new ArrayList<>();

            Drone drone;
            for (int i = 0; i < 10; i++) {
                drone = Drone.builder()
                        .serialNumber(String.valueOf(faker.code().imei()))
                        .state(DroneState.IDLE)
                        .batteryCapacity(faker.number().numberBetween(1, 100))
                        .model(DroneModel.getRandom())
                        .weightLimit(faker.number().numberBetween(1, 100))
                        .build();

                drones.add(drone);
            }
            droneRepository.saveAll(drones);
            log.info("Done seeding drones");


            log.info("Seeding medication");
            List<Medication> medications = new ArrayList<>();
            Medication medication;
            for (int i = 0; i < 100; i++) {
                medication = Medication.builder()
                        .name(faker.ancient().god().toUpperCase())
                        .code(faker.ancient().god().toUpperCase())
                        .weight(faker.number().numberBetween(1, 20))
                        .allocated(false)
                        .image(faker.internet().image())
                        .build();
                medications.add(medication);
            }
            medicationRepository.saveAll(medications);
            log.info("Done seeding medication");
        }

    }
}
