package com.fleet.drone.service;

import com.fleet.drone.dto.DroneDto;
import com.fleet.drone.model.Drone;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface DroneService {
    DroneDto register(DroneDto droneDto);

    void deleteBySerialNumber(String serialNumber);

    DroneDto findBySerialNumber(String serialNumber);

    Page<DroneDto> findByCondition(DroneDto droneDto, Pageable pageable);

    DroneDto update(DroneDto droneDto, String serialNumber);

    List<DroneDto> findAll();

    String getDroneBatteryLevel(String serialNumber);

    List<DroneDto> findAvailableDrones();

    void logDronesBatteryLevel();

    void updateBatteryLevel();
}
