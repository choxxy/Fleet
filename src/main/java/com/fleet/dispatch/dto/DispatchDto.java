package com.fleet.dispatch.dto;

import com.fleet.common.dto.AbstractDto;
import com.fleet.drone.model.Drone;
import com.fleet.medication.model.Medication;
import lombok.Data;

@Data
public class DispatchDto extends AbstractDto<Integer> {
    private Integer id;
    private Drone drone;
    private Medication medication;
}