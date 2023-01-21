package com.fleet.drone.dto;

import com.fleet.drone.model.DroneModel;
import com.fleet.drone.model.DroneState;
import lombok.Data;


@Data
public class DroneDto {
    private String serialNumber;
    private DroneModel model;
    private Integer weightLimit;
    private Integer batteryCapacity;
    private DroneState state;
}