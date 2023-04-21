package com.musalasoft.drones.mapper;

import com.musalasoft.drones.dto.DroneDto;
import com.musalasoft.drones.entity.Drone;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface DroneMapper {

    DroneMapper INSTANCE = Mappers.getMapper(DroneMapper.class);

    DroneDto droneToDroneDto(Drone drone);

    Drone droneDtoToDrone(Drone drone);
}
