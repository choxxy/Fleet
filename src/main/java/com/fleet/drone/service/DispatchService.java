package com.fleet.drone.service;


import com.fleet.drone.dto.DroneDto;
import com.fleet.medication.dto.MedicationDto;

import java.util.List;

public interface DispatchService {
    void deleteById(Integer id);

    List<DroneDto> findAvailableDrones();

    List<MedicationDto> findDroneMedicationLoad(String serialNumber);

    void load(String serialNumber, List<Integer> medicationIdList);
}
