package com.fleet.drone.service;

import com.fleet.drone.dto.DroneDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface DroneService {
    DroneDto save(DroneDto droneDto);

    void deleteBySerialNumber(String serialNumber);

    DroneDto findBySerialNumber(String serialNumber);

    Page<DroneDto> findByCondition(DroneDto droneDto, Pageable pageable);

    DroneDto update(DroneDto droneDto, String serialNumber);
}
