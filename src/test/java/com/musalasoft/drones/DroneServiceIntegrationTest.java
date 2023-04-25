package com.musalasoft.drones;

import com.github.javafaker.Faker;
import com.musalasoft.drones.dto.DroneMedicationResponse;
import com.musalasoft.drones.entity.Drone;
import com.musalasoft.drones.entity.Medication;
import com.musalasoft.drones.helper.DroneModel;
import com.musalasoft.drones.helper.DroneState;
import com.musalasoft.drones.repository.DroneRepository;
import com.musalasoft.drones.repository.MedicationRepository;
import com.musalasoft.drones.service.ConsignmentService;
import com.musalasoft.drones.service.DroneService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@RunWith(MockitoJUnitRunner.class)
@ExtendWith(SpringExtension.class)
@SpringBootTest
public class DroneServiceIntegrationTest {

    @Autowired
    Faker faker;
    @Autowired
    private DroneService droneService;
    @Autowired
    private DroneRepository droneRepository;
    @Autowired
    private MedicationRepository medicationRepository;
    @Autowired
    private ConsignmentService consignmentService;
    private Drone drone;
    private List<Medication> medications;

    @BeforeEach
    public void setUp() {
        // Create a drone and save it to the repository
        drone = new Drone();
        drone.setWeightLimit(50.0f);
        drone.setBatteryCapacity(100.0f);
        drone.setState(DroneState.IDLE);
        drone.setSerialNumber(String.valueOf(faker.code().imei()));
        drone.setModel(DroneModel.Cruiserweight);
        drone = droneRepository.save(drone);

        // Create a list of medications
        medications = new ArrayList<>();
        Medication medication1 = new Medication();
        medication1.setWeight(10.0f);
        medication1.setName(faker.ancient().god().toUpperCase());
        medication1.setCode(faker.ancient().god().toUpperCase());
        medication1.setAllocated(false);
        medication1.setImage(faker.internet().image());

        medications.add(medication1);

        Medication medication2 = new Medication();
        medication2.setWeight(20.0f);
        medication2.setName(faker.ancient().god().toUpperCase());
        medication2.setCode(faker.ancient().god().toUpperCase());
        medication2.setAllocated(false);
        medication2.setImage(faker.internet().image());
        medications.add(medication2);

        medications = medicationRepository.saveAll(medications);
    }

    @Test
    public void testLoadDrone() throws Exception {

        // Load the drone with medications
        DroneMedicationResponse response = droneService.loadDrone(drone.getId(), medications);

        // Check if the drone state is LOADING
        assertThat(droneService.getDrone(drone.getId()).getState()).isEqualTo(DroneState.LOADING);

        // Check if the medications are loaded into the drone
        List<Medication> loadedMedication = new ArrayList<>();
        medications.forEach(medication -> {
            medication.setAllocated(true);
            loadedMedication.add(medication);
        });
        assertThat(response.getMedications()).containsAll(loadedMedication);
    }

    @Test
    public void testDeliverConsignment() throws Exception {
        // Load the drone with medications
        droneService.loadDrone(drone.getId(), medications);

        // Update the drone state to DELIVERING
        droneService.updateDroneState(drone.getId(), DroneState.DELIVERING);

        // Check if the drone state is DELIVERING
        assertThat(droneService.getDrone(drone.getId()).getState()).isEqualTo(DroneState.DELIVERING);
    }

    @Test
    public void testDeliveryComplete() throws Exception {
        // Load the drone with medications and update the drone state to DELIVERING
        droneService.loadDrone(drone.getId(), medications);
        droneService.updateDroneState(drone.getId(), DroneState.DELIVERING);

        // Update the drone state to DELIVERED
        droneService.updateDroneState(drone.getId(), DroneState.DELIVERED);

        // Check if the drone state is DELIVERED
        assertThat(droneService.getDrone(drone.getId()).getState()).isEqualTo(DroneState.DELIVERED);
    }

    @Test
    public void testReturnToBase() throws Exception {
        // Load the drone with medications, update the drone state to DELIVERING, and
        // then update the drone state to DELIVERED
        droneService.loadDrone(drone.getId(), medications);
        droneService.updateDroneState(drone.getId(), DroneState.DELIVERING);
        droneService.updateDroneState(drone.getId(), DroneState.DELIVERED);

        // Update the drone state to RETURNING
        droneService.updateDroneState(drone.getId(), DroneState.RETURNING);

        // Check if the drone state is RETURNING
        assertThat(droneService.getDrone(drone.getId()).getState()).isEqualTo(DroneState.RETURNING);
    }

    @Test
    public void testExceedWeightLimit() {
        // Create a medication with a weight exceeding the drone's weight limit
        Medication medication3 = new Medication();
        medication3.setWeight(60.0f);
        medication3.setName(faker.ancient().god().toUpperCase());
        medication3.setCode(faker.ancient().god().toUpperCase());
        medication3.setAllocated(false);
        medication3.setImage(faker.internet().image());
        medications.add(medication3);

        // Attempt to load the drone with medications
        assertThatThrownBy(() -> droneService.loadDrone(drone.getId(), medications));
    }
}
