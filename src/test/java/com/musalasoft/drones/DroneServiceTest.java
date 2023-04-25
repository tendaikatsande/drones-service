package com.musalasoft.drones;

import com.musalasoft.drones.dto.DroneBatteryResponse;
import com.musalasoft.drones.dto.DroneMedicationResponse;
import com.musalasoft.drones.entity.Consignment;
import com.musalasoft.drones.entity.Drone;
import com.musalasoft.drones.entity.Medication;
import com.musalasoft.drones.helper.ConsignmentState;
import com.musalasoft.drones.helper.DroneModel;
import com.musalasoft.drones.helper.DroneState;
import com.musalasoft.drones.repository.DroneRepository;
import com.musalasoft.drones.service.ConsignmentService;
import com.musalasoft.drones.service.impl.DroneServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static junit.framework.TestCase.assertEquals;
import static org.junit.Assert.assertNotNull;
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
        drone = new Drone(1L, "Drone 1", DroneModel.Heavyweight, 150f, 100f, DroneState.IDLE);
        medication = new Medication(1L, "Medication 1", 10f, "CODE", false, "image");
        consignment = new Consignment(1L, drone, medication, ConsignmentState.LOADED, Instant.now());

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
        when(droneRepository.save(drone)).thenReturn(drone);

        Drone registeredDrone = droneService.registerDrone(drone);

        assertNotNull(registeredDrone);
        assertEquals(drone, registeredDrone);
        verify(droneRepository, times(1)).save(drone);
    }

    @Test
    public void testGetDroneMedications() throws Exception {
        when(consignmentService.getDroneConsignment(drone)).thenReturn(consignments);
        when(droneRepository.findById(1L)).thenReturn(Optional.of(drone));

        DroneMedicationResponse response = droneService.getDroneMedications(1L);

        assertNotNull(response);
        assertEquals(Optional.ofNullable(1L), Optional.ofNullable(response.getDroneId()));
        assertEquals(medications, response.getMedications());
    }

    @Test
    public void testGetAvailableDrones() {
        when(droneRepository.findAllByState(DroneState.IDLE)).thenReturn(drones);

        List<Drone> availableDrones = droneService.getAvailableDrones();

        assertNotNull(availableDrones);
        assertEquals(drones, availableDrones);
    }

    @Test
    public void testGetDroneBatteryLevel() throws Exception {
        when(droneRepository.findById(1L)).thenReturn(Optional.of(drone));

        DroneBatteryResponse response = droneService.getDroneBatteryLevel(1L);

        assertNotNull(response);
        assertEquals(Optional.ofNullable(1L), Optional.ofNullable(response.getDroneId()));
        assertEquals(drone.getBatteryCapacity(), response.getBatteryLevel());
    }

    @Test
    public void testGetDrone() throws Exception {
        when(droneRepository.findById(1L)).thenReturn(Optional.of(drone));

        Drone foundDrone = droneService.getDrone(1L);

        assertNotNull(foundDrone);
        assertEquals(drone, foundDrone);
    }

    @Test
    public void testGetAll() {
        Page<Drone> dronePage = new PageImpl<>(drones, pageable, drones.size());
        when(droneRepository.findAll(pageable)).thenReturn(dronePage);

        Page<Drone> result = droneService.getAll(pageable);

        assertNotNull(result);
        assertEquals(dronePage, result);
    }

    @Test
    public void testUpdateDroneState() throws Exception {
        when(droneRepository.findById(1L)).thenReturn(Optional.of(drone));
        when(droneRepository.save(drone)).thenReturn(drone);

        Drone updatedDrone = droneService.updateDroneState(1L, DroneState.LOADING);

        assertNotNull(updatedDrone);
        assertEquals(DroneState.LOADING, updatedDrone.getState());
    }


}
