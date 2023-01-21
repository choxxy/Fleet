package com.fleet.drone.dto;

import com.fleet.drone.model.Drone;
import com.fleet.medication.model.Medication;
import lombok.Data;

@Data
public class DispatchDto {
    private Integer id;
    private Drone drone;
    private Medication medication;
}
