package com.fleet.medication.controller;

import com.fleet.medication.dto.MedicationDto;

import java.util.Collections;
import java.util.List;

public class MedicationBuilder {
    public static List<String> getIds() {
        return Collections.singletonList("1");
    }

    public static MedicationDto getDto() {
        MedicationDto dto = new MedicationDto();
        dto.setId(1L);
        return dto;
    }
}