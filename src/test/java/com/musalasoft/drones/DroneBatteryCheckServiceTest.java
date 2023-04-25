package com.musalasoft.drones;

import com.github.javafaker.Faker;
import com.musalasoft.drones.entity.Drone;
import com.musalasoft.drones.helper.DroneModel;
import com.musalasoft.drones.helper.DroneState;
import com.musalasoft.drones.repository.DroneBatteryLogRepository;
import com.musalasoft.drones.service.DroneService;
import com.musalasoft.drones.service.impl.DroneBatteryCheckServiceImpl;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
public class DroneBatteryCheckServiceTest {
    @InjectMocks
    private DroneBatteryCheckServiceImpl droneBatteryCheckService;

    @Mock
    private DroneService droneService;

    @Mock
    private DroneBatteryLogRepository droneBatteryLogRepository;

    @Autowired
    private Faker faker;
    private Drone drone;

    @BeforeEach
    public void setUp() {
        // Create a drone and save it to the repository
        drone = new Drone();
        drone.setWeightLimit(50.0f);
        drone.setBatteryCapacity(100.0f);
        drone.setState(DroneState.IDLE);
        drone.setSerialNumber(String.valueOf(faker.code().imei()));
        drone.setModel(DroneModel.Cruiserweight);
        drone = droneService.registerDrone(drone);
    }

    @Test
    public void testCheckBatteryStatus_noDrones() {
        when(droneService.getAll(any())).thenReturn(Page.empty());
        droneBatteryCheckService.checkBatteryStatus();
        verify(droneService, times(1)).getAll(any());
    }

    @Test
    public void testProcessDrones_nonEmptySlice() {
        // Prepare data
        List<Drone> droneList = new ArrayList<>();
        droneList.add(drone);
        droneList.add(drone);

        Slice<Drone> droneSlice = new SliceImpl<>(droneList);

        // Test the method
        droneBatteryCheckService.processDrones(droneSlice);

        // Verify interactions
        verify(droneBatteryLogRepository, times(1)).saveAll(any());
    }


}