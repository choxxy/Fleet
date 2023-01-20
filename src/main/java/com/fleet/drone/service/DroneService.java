package com.fleet.drone.service;

import com.fleet.drone.dto.DroneDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface DroneService {
    DroneDto save(DroneDto droneDto);

    void deleteById(String id);

    DroneDto findById(String id);

    Page<DroneDto> findByCondition(DroneDto droneDto, Pageable pageable);

    DroneDto update(DroneDto droneDto, String id);
}
