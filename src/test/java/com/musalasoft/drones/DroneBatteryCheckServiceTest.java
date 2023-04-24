package com.musalasoft.drones;

import com.musalasoft.drones.entity.Drone;
import com.musalasoft.drones.repository.DroneBatteryLogRepository;
import com.musalasoft.drones.service.DroneService;
import com.musalasoft.drones.service.impl.DroneBatteryCheckServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class DroneBatteryCheckServiceTest {
    @InjectMocks
    private DroneBatteryCheckServiceImpl droneBatteryCheckService;

    @Mock
    private DroneService droneService;

    @Mock
    private DroneBatteryLogRepository droneBatteryLogRepository;

    @Test
    public void testCheckBatteryStatus_noDrones() {
        when(droneService.getAll(any())).thenReturn(Page.empty());
        droneBatteryCheckService.checkBatteryStatus();
        verify(droneService, times(1)).getAll(any());
    }

    @Test
    public void testProcessDrones_emptySlice() {
        Slice<Drone> emptySlice = Page.empty();
        droneBatteryCheckService.processDrones(emptySlice);
        // verify(droneBatteryLogRepository, times(0)).saveAll(any());
    }

    @Test
    public void testProcessDrones_nonEmptySlice() {
        // Prepare data
        List<Drone> droneList = new ArrayList<>();
        droneList.add(createDrone(1L));
        droneList.add(createDrone(2L));

        Slice<Drone> droneSlice = new SliceImpl<>(droneList);

        // Test the method
        droneBatteryCheckService.processDrones(droneSlice);

        // Verify interactions
        verify(droneBatteryLogRepository, times(1)).saveAll(any());
    }

    private Drone createDrone(Long id) {
        Drone drone = new Drone();
        drone.setId(id);
        drone.setBatteryCapacity(100);
        return drone;
    }
}