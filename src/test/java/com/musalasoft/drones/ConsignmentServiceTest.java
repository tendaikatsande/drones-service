package com.musalasoft.drones;

import com.musalasoft.drones.entity.Consignment;
import com.musalasoft.drones.entity.Drone;
import com.musalasoft.drones.entity.Medication;
import com.musalasoft.drones.helper.ConsignmentState;
import com.musalasoft.drones.helper.DroneModel;
import com.musalasoft.drones.helper.DroneState;
import com.musalasoft.drones.repository.ConsignmentRepository;
import com.musalasoft.drones.service.DroneService;
import com.musalasoft.drones.service.MedicationService;
import com.musalasoft.drones.service.impl.ConsignmentServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Instant;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ConsignmentServiceTest {
    @InjectMocks
    private ConsignmentServiceImpl consignmentService;

    @Mock
    private DroneService droneService;

    @Mock
    private MedicationService medicationService;

    @Mock
    private ConsignmentRepository consignmentRepository;

    private Drone drone;
    private Medication medication;
    private Consignment consignment;

    @BeforeEach
    public void setUp() {
        drone = new Drone(1L, "Drone 1", DroneModel.Heavyweight, 150f, 100f, DroneState.IDLE);
        medication = new Medication(1L, "Medication 1", 10f, "CODE", false, "image");
        consignment = new Consignment(1L, drone, medication, ConsignmentState.LOADED, Instant.now());
    }

    @Test
    public void testGetDroneConsignment() {
        drone = new Drone(1L, "Drone 1", DroneModel.Heavyweight, 150f, 100f, DroneState.IDLE);
        when(consignmentRepository.findAllByDroneIdAndState(drone.getId(), ConsignmentState.LOADED)).thenReturn(Collections.singletonList(consignment));

        List<Consignment> result = consignmentService.getDroneConsignment(drone);

        assertEquals(1, result.size());
        assertEquals(consignment, result.get(0));
    }

    @Test
    public void testGetDroneConsignmentWeight() {
        drone = new Drone(1L, "Drone 1", DroneModel.Heavyweight, 150f, 100f, DroneState.IDLE);

        when(consignmentRepository.findAllByDroneIdAndState(drone.getId(), ConsignmentState.LOADED)).thenReturn(Collections.singletonList(consignment));

        float result = consignmentService.getDroneConsignmentWeight(drone);

        assertEquals(10f, result);
    }

    @Test
    public void testAddMedicationToDroneConsignment() throws Exception {
        drone = new Drone(1L, "Drone 1", DroneModel.Heavyweight, 150f, 100f, DroneState.IDLE);
        medication = new Medication(1L, "Medication 1", 10f, "CODE", false, "image");


        consignmentService.addMedicationToDroneConsignment(drone, medication);

        verify(consignmentRepository).save(any(Consignment.class));
        verify(medicationService).markAsAllocated(1L);
    }

    @Test
    public void testRemoveMedicationToDroneConsignment() {
        drone = new Drone(1L, "Drone 1", DroneModel.Heavyweight, 150f, 100f, DroneState.IDLE);
        medication = new Medication(1L, "Medication 1", 10f, "CODE", false, "image");

        consignmentService.removeMedicationFromDroneConsignment(drone, medication);

        verify(consignmentRepository).deleteAllByDroneIdAndMedicationId(1L, 1L);
    }

    @Test
    public void testDeliverConsignment() {
        drone = new Drone(1L, "Drone 1", DroneModel.Heavyweight, 150f, 100f, DroneState.IDLE);

        when(consignmentRepository.findAllByDroneIdAndState(1L, ConsignmentState.LOADED)).thenReturn(Collections.singletonList(consignment));

        consignmentService.deliverConsignment(drone);

        assertEquals(ConsignmentState.DELIVERED, consignment.getState());
        verify(consignmentRepository).saveAll(Collections.singletonList(consignment));
    }

    @Test
    public void testCanLoadMedication() throws Exception {
        drone = new Drone(1L, "Drone 1", DroneModel.Heavyweight, 150f, 100f, DroneState.IDLE);


        boolean result = consignmentService.canLoadMedication(drone, 5f);

        assertTrue(result);
    }

    @Test
    public void testCanLoadMedicationWithLowBattery() throws Exception {
        Drone lowBatteryDrone = new Drone(1L, "Drone 1", DroneModel.Heavyweight, 150f, 20f, DroneState.IDLE);
        assertThrows(Exception.class, () -> consignmentService.canLoadMedication(lowBatteryDrone, 5f));
    }

    @Test
    public void testCanLoadMedicationExceedingWeightLimit() throws Exception {
        drone = new Drone(1L, "Drone 1", DroneModel.Heavyweight, 150f, 100f, DroneState.IDLE);
        medication = new Medication(1L, "Medication 1", 10f, "CODE", false, "image");

        when(consignmentRepository.findAllByDroneIdAndState(1L, ConsignmentState.LOADED)).thenReturn(Collections.singletonList(consignment));

        assertThrows(Exception.class, () -> consignmentService.canLoadMedication(drone, 150f));
    }
}