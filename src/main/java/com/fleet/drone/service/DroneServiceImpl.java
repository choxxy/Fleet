package com.fleet.drone.service;

import com.fleet.drone.dto.DroneDto;
import com.fleet.drone.mapper.DroneMapper;
import com.fleet.drone.model.Drone;
import com.fleet.drone.model.DroneState;
import com.fleet.drone.repository.DroneRepository;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@Transactional
public class DroneServiceImpl implements DroneService {


    private final DroneRepository repository;
    private final DroneMapper droneMapper;

    public DroneServiceImpl(DroneRepository repository, DroneMapper droneMapper) {
        this.repository = repository;
        this.droneMapper = droneMapper;
    }

    @Override
    public DroneDto register(DroneDto droneDto) {
        Drone entity = droneMapper.toEntity(droneDto);
        return droneMapper.toDto(repository.save(entity));
    }

    @Override
    public void deleteBySerialNumber(String serialNumber) {
        repository.deleteById(serialNumber);
    }

    @Override
    public DroneDto findBySerialNumber(String serialNumber) {
        return droneMapper.toDto(repository.findById(serialNumber).orElseThrow(ResourceNotFoundException::new));
    }

    @Override
    public DroneDto update(DroneDto droneDto, String serialNumber) {
        DroneDto data = findBySerialNumber(serialNumber);
        Drone entity = droneMapper.toEntity(droneDto);
        BeanUtils.copyProperties(data, entity);
        return droneMapper.toDto(repository.save(entity));
    }

    @Override
    public List<DroneDto> findAll() {
        List<Drone> drones = repository.findAll();
        return droneMapper.toDto(drones);
    }

    @Override
    public String getDroneBatteryLevel(String serialNumber) {
        int batteryLevel = repository.findDroneBatteryCapacity(serialNumber);
        return String.format("%d%%", batteryLevel);
    }

    @Override
    public void logDronesBatteryLevel() {
        List<Drone> drones = repository.findAll();

        for (Drone drone : drones) {
            log.info("Drone {}, battery level {}%", drone.getSerialNumber(), drone.getBatteryLevel());
        }
    }

    @Override
    public void updateBatteryLevel() {

        List<DroneDto> droneDtoList = findAll();

        for (DroneDto drone : droneDtoList) {
            int level = drone.getBatteryLevel();
            if (level >= 0 && level <= 5) {
                drone.setBatteryLevel(100);
            } else {
                level -= 5;
                drone.setBatteryLevel(level);
            }
            update(drone, drone.getSerialNumber());
        }

    }

    @Override
    public void updateDroneStatus() {

        List<DroneDto> droneDtoList = findAll();

        for (DroneDto drone : droneDtoList) {
            DroneState state = drone.getState();

            switch (state) {
                case IDLE -> {
                    continue;
                }
                case LOADED -> drone.setState(DroneState.DELIVERING);
                case DELIVERED -> drone.setState(DroneState.RETURNING);
                case LOADING -> drone.setState(DroneState.LOADED);
                case RETURNING -> drone.setState(DroneState.IDLE);
                case DELIVERING -> drone.setState(DroneState.DELIVERED);

            }

            update(drone, drone.getSerialNumber());
        }

    }
}