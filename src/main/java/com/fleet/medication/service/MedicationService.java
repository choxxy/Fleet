package com.fleet.medication.service;

import com.fleet.medication.dto.MedicationDto;

import java.util.List;

public interface MedicationService {
    MedicationDto save(MedicationDto medicationDto);

    void deleteById(Integer id);

    MedicationDto findById(Integer id);

    MedicationDto update(MedicationDto medicationDto, Integer id);

    List<MedicationDto> findAll();
}
