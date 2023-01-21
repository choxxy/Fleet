package com.fleet.drone.controller;

import com.fleet.drone.dto.DroneDto;
import com.fleet.drone.service.DroneService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RequestMapping("/api/drone")
@RestController
@Slf4j
public class DroneController {
    private final DroneService droneService;

    public DroneController(DroneService droneService) {
        this.droneService = droneService;
    }

    @PostMapping
    public ResponseEntity<Void> save(@RequestBody @Validated DroneDto droneDto) {
        droneService.save(droneDto);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{serialNumber}")
    public ResponseEntity<DroneDto> findById(@PathVariable("serialNumber") String id) {
        DroneDto drone = droneService.findBySerialNumber(id);
        return ResponseEntity.ok(drone);
    }

    @DeleteMapping("/{serialNumber}")
    public ResponseEntity<Void> delete(@PathVariable("serialNumber") String serialNumber) {
        Optional.ofNullable(droneService.findBySerialNumber(serialNumber)).orElseThrow(() -> {
            log.error("Unable to delete non-existent data！");
            return new ResourceNotFoundException();
        });
        droneService.deleteBySerialNumber(serialNumber);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/page-query")
    public ResponseEntity<Page<DroneDto>> pageQuery(DroneDto droneDto, @PageableDefault(sort = "serialNumber", direction = Sort.Direction.DESC) Pageable pageable) {
        Page<DroneDto> dronePage = droneService.findByCondition(droneDto, pageable);
        return ResponseEntity.ok(dronePage);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> update(@RequestBody @Validated DroneDto droneDto, @PathVariable("serialNumber") String serialNumber) {
        droneService.update(droneDto, serialNumber);
        return ResponseEntity.ok().build();
    }
}