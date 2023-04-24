
package com.musalasoft.drones;

import com.musalasoft.drones.dto.DroneMedicationResponse;
import com.musalasoft.drones.entity.Consignment;
import com.musalasoft.drones.entity.Drone;
import com.musalasoft.drones.entity.Medication;
import com.musalasoft.drones.helper.DroneState;
import com.musalasoft.drones.repository.DroneRepository;
import com.musalasoft.drones.service.ConsignmentService;
import com.musalasoft.drones.service.impl.DroneServiceImpl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class DroneServiceTest {

    @Mock
    private DroneRepository droneRepository;

    @Mock
    private ConsignmentService consignmentService;

    @InjectMocks
    private DroneServiceImpl droneService;

    private Drone drone;
    private Medication medication;
    private Consignment consignment;
    private List<Medication> medications;
    private List<Consignment> consignments;
    private List<Drone> drones;
    private PageRequest pageable;

    @BeforeEach
    public void setUp() {
        drone = new Drone();
        drone.setId(1L);
        drone.setWeightLimit(100);
        drone.setBatteryCapacity(100);
        drone.setState(DroneState.IDLE);

        medication = new Medication();
        medication.setId(1L);
        medication.setWeight(10);

        consignment = new Consignment();
        consignment.setDrone(drone);
        consignment.setMedication(medication);

        medications = new ArrayList<>();
        medications.add(medication);

        consignments = new ArrayList<>();
        consignments.add(consignment);

        drones = new ArrayList<>();
        drones.add(drone);

        pageable = PageRequest.of(0, 10);
    }

    @Test
    public void testRegisterDrone() {
        // Add test code here
    }

    @Test
    public void testLoadDrone() throws Exception {
        // Add test code here
        // when(droneRepository.findById(any())).thenReturn(Optional.of(drone));
        // when(consignmentService.getDroneConsignmentWeight(any())).thenReturn(0f);
        // when(consignmentService.addMedicationToDroneConsignment(any(Drone.class), any(Medication.class))).thenReturn(consignment);

        // DroneMedicationResponse response = droneService.loadDrone(drone.getId(), medications);

        // assertEquals(drone.getId(), response.getDroneId());
        // assertEquals(0, response.getMedications().size());
        // verify(consignmentService, times(1)).getDroneConsignmentWeight(drone);
        // verify(consignmentService, times(1)).addMedicationToDroneConsignment(drone, medication);
    }

    @Test
    public void testGetDroneMedications() {
        // Add test code here
    }

    @Test
    public void testGetAvailableDrones() {
        // Add test code here
    }

    @Test
    public void testGetDroneBatteryLevel() {
        // Add test code here
    }

    @Test
    public void testGetDrone() {
        // Add test code here
    }

    @Test
    public void testGetAll() {
        // Add test code here
    }

    @Test
    public void testUpdateDroneState() {
        // Add test code here
    }

}
