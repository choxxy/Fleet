package com.fleet.drone.dto;

import com.fleet.drone.model.DroneModel;
import com.fleet.drone.model.DroneState;
import com.fleet.drone.model.DroneStatus;
import lombok.Data;


@Data
public class DroneDto {
    private String serialNumber;
    private DroneStatus status;
    private DroneModel model;
    private Integer weightLimit;
    private Integer batteryLevel;
    private DroneState state;
}