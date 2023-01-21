package com.fleet.drone.controller;

import com.fleet.drone.dto.DroneDto;
import com.fleet.drone.model.DroneModel;
import com.fleet.drone.model.DroneState;
import com.fleet.drone.model.DroneStatus;

import java.util.Collections;
import java.util.List;

public class DroneBuilder {
    public static List<String> getIds() {
        return Collections.singletonList("1");
    }

    public static DroneDto getDto() {
        DroneDto dto = new DroneDto();
        dto.setSerialNumber("1");
        dto.setModel(DroneModel.CRUISERWEIGHT);
        dto.setBatteryLevel(90);
        dto.setStatus(DroneStatus.ACTIVE);
        dto.setWeightLimit(400);
        dto.setState(DroneState.IDLE);
        return dto;
    }
}