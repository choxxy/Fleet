package com.fleet.medication.controller;

import com.fleet.medication.dto.MedicationDto;
import com.fleet.medication.model.Medication;
import com.fleet.medication.service.MedicationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RequestMapping("/api/medication")
@RestController
@Slf4j
public class MedicationController {
    private final MedicationService medicationService;

    public MedicationController(MedicationService medicationService) {
        this.medicationService = medicationService;
    }

    @PostMapping("/register")
    public ResponseEntity<MedicationDto> register(@Validated(Medication.class) @RequestBody MedicationDto medicationDto) {
        MedicationDto dto= medicationService.save(medicationDto);
        return new ResponseEntity<>(dto, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<MedicationDto> findById(@PathVariable("id") Integer id) {
        MedicationDto medication = medicationService.findById(id);
        return ResponseEntity.ok(medication);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Integer id) {
        Optional.ofNullable(medicationService.findById(id)).orElseThrow(() -> {
            log.error("Unable to delete non-existent data！");
            return new ResourceNotFoundException();
        });
        medicationService.deleteById(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/")
    public ResponseEntity<List<MedicationDto>> getAllDrones() {
        List<MedicationDto> medicationDtoList = medicationService.findAll();
        return ResponseEntity.ok(medicationDtoList);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> update(@RequestBody @Validated MedicationDto medicationDto, @PathVariable("id") Integer id) {
        medicationService.update(medicationDto, id);
        return ResponseEntity.ok().build();
    }
}