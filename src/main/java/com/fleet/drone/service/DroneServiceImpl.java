package com.fleet.drone.service;

import com.fleet.drone.dto.DroneDto;
import com.fleet.drone.mapper.DroneMapper;
import com.fleet.drone.model.Drone;
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
    public DroneDto save(DroneDto droneDto) {
        Drone entity = droneMapper.toEntity(droneDto);
        return droneMapper.toDto(repository.save(entity));
    }

    @Override
    public void deleteBySerialNumber(String id) {
        repository.deleteById(id);
    }

    @Override
    public DroneDto findBySerialNumber(String id) {
        return droneMapper.toDto(repository.findById(id).orElseThrow(ResourceNotFoundException::new));
    }

    @Override
    public Page<DroneDto> findByCondition(DroneDto droneDto, Pageable pageable) {
        Page<Drone> entityPage = repository.findAll(pageable);
        List<Drone> entities = entityPage.getContent();
        return new PageImpl<>(droneMapper.toDto(entities), pageable, entityPage.getTotalElements());
    }

    @Override
    public DroneDto update(DroneDto droneDto, String id) {
        DroneDto data = findBySerialNumber(id);
        Drone entity = droneMapper.toEntity(droneDto);
        BeanUtils.copyProperties(data, entity);
        return save(droneMapper.toDto(entity));
    }
}