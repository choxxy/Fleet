package com.fleet.drone.service;

import com.fleet.common.exceptions.LowBatteryException;
import com.fleet.common.exceptions.WeightExceededException;
import com.fleet.drone.dto.DispatchDto;
import com.fleet.drone.dto.DroneDto;
import com.fleet.drone.mapper.DispatchMapper;
import com.fleet.drone.mapper.DroneMapper;
import com.fleet.drone.model.Dispatch;
import com.fleet.drone.model.Drone;
import com.fleet.drone.model.DroneState;
import com.fleet.drone.repository.DispatchRepository;
import com.fleet.medication.dto.MedicationDto;
import com.fleet.medication.mapper.MedicationMapper;
import com.fleet.medication.model.Medication;
import com.fleet.medication.service.MedicationService;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@Transactional
public class DispatchServiceImpl implements DispatchService {

    private static final int BATTERY_LEVEL_THRESHOLD = 25;

    private final DispatchRepository repository;
    private final DroneService droneService;
    private final MedicationService medicationService;
    private final DispatchMapper dispatchMapper;
    private final DroneMapper droneMapper;
    private final MedicationMapper medicationMapper;

    public DispatchServiceImpl(DispatchRepository repository,
                               DroneService droneService,
                               MedicationService medicationService,
                               DispatchMapper dispatchMapper,
                               DroneMapper droneMapper,
                               MedicationMapper medicationMapper) {
        this.repository = repository;
        this.droneService = droneService;
        this.medicationService = medicationService;
        this.dispatchMapper = dispatchMapper;
        this.droneMapper = droneMapper;
        this.medicationMapper = medicationMapper;
    }

    private DispatchDto save(DispatchDto dispatchDto) {
        Dispatch entity = dispatchMapper.toEntity(dispatchDto);
        return dispatchMapper.toDto(repository.save(entity));
    }

    @Override
    public void deleteById(Integer id) {
        repository.deleteById(id);
    }

    @Override
    public List<DroneDto> findAvailableDrones() {
        List<Drone> list = repository.findAvailableDrones();
        return droneMapper.toDto(list);
    }

    @Override
    public List<MedicationDto> findDroneMedicationLoad(String serialNumber) {
        return medicationMapper.toDto(repository.findMedicationsByDroneId(serialNumber));
    }

    @Override
    public void load(String serialNumber, List<Integer> medicationIdList) {
        Drone drone = droneMapper.toEntity(droneService.findBySerialNumber(serialNumber));
        int totalWeight = 0;

        if (drone.getBatteryLevel() < BATTERY_LEVEL_THRESHOLD)
            throw new LowBatteryException("Drone battery level is too low");

        for (Integer id : medicationIdList) {
            Medication medication = medicationMapper.toEntity(medicationService.findById(id));
            DispatchDto dispatchDto = new DispatchDto();
            dispatchDto.setDrone(drone);
            dispatchDto.setMedication(medication);
            totalWeight += medication.getWeight();

            if (totalWeight > drone.getWeightLimit())
                throw new WeightExceededException("Drone carrying weight exceeded");
            else
                save(dispatchDto);
        }

        drone.setState(DroneState.LOADING);
        droneService.update(droneMapper.toDto(drone), drone.getSerialNumber());

    }
}