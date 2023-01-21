package com.fleet.medication.service;

import com.fleet.medication.dto.MedicationDto;
import com.fleet.medication.mapper.MedicationMapper;
import com.fleet.medication.model.Medication;
import com.fleet.medication.repository.MedicationRepository;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@Transactional
public class MedicationServiceImpl implements MedicationService {
    private final MedicationRepository repository;
    private final MedicationMapper medicationMapper;

    public MedicationServiceImpl(MedicationRepository repository, MedicationMapper medicationMapper) {
        this.repository = repository;
        this.medicationMapper = medicationMapper;
    }

    @Override
    public MedicationDto save(MedicationDto medicationDto) {
        Medication entity = medicationMapper.toEntity(medicationDto);
        return medicationMapper.toDto(repository.save(entity));
    }

    @Override
    public void deleteById(Integer id) {
        repository.deleteById(id);
    }

    @Override
    public MedicationDto findById(Integer id) {
        return medicationMapper.toDto(repository.findById(id).orElseThrow(ResourceNotFoundException::new));
    }

    @Override
    public MedicationDto update(MedicationDto medicationDto, Integer id) {
        MedicationDto data = findById(id);
        Medication entity = medicationMapper.toEntity(medicationDto);
        BeanUtils.copyProperties(data, entity);
        return save(medicationMapper.toDto(entity));
    }

    @Override
    public List<MedicationDto> findAll() {
        List<Medication> medicationList = repository.findAll();
        return medicationMapper.toDto(medicationList);
    }
}