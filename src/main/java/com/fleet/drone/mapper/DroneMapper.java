package com.fleet.drone.mapper;

import com.fleet.common.mapper.EntityMapper;
import com.fleet.drone.dto.DroneDto;
import com.fleet.drone.model.Drone;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface DroneMapper extends EntityMapper<DroneDto, Drone> {
}