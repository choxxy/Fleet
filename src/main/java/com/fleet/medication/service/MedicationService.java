package com.fleet.medication.service;

import com.fleet.medication.dto.MedicationDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface MedicationService {
    MedicationDto save(MedicationDto medicationDto);

    void deleteById(Integer id);

    MedicationDto findById(Integer id);

    Page<MedicationDto> findByCondition(MedicationDto medicationDto, Pageable pageable);

    MedicationDto update(MedicationDto medicationDto, Integer id);
}
