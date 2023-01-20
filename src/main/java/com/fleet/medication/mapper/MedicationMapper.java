package com.fleet.medication.mapper;

import com.fleet.common.mapper.EntityMapper;
import com.fleet.medication.dto.MedicationDto;
import com.fleet.medication.model.Medication;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface MedicationMapper extends EntityMapper<MedicationDto, Medication> {
}