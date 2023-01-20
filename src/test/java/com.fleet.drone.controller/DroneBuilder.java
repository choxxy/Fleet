package com.fleet.drone.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fleet.drone.dto.DroneDto;

import java.util.Collections;
import java.util.List;

public class DroneBuilder {
    public static List<String> getIds() {
        return Collections.singletonList("1");
    }

    public static DroneDto getDto() {
        DroneDto dto = new DroneDto();
        dto.setSerialNumber("1");
        return dto;
    }
}