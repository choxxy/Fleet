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

    @GetMapping("/{id}")
    public ResponseEntity<DroneDto> findById(@PathVariable("id") String id) {
        DroneDto drone = droneService.findById(id);
        return ResponseEntity.ok(drone);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") String id) {
        Optional.ofNullable(droneService.findById(id)).orElseThrow(() -> {
            log.error("Unable to delete non-existent data！");
            return new ResourceNotFoundException();
        });
        droneService.deleteById(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/page-query")
    public ResponseEntity<Page<DroneDto>> pageQuery(DroneDto droneDto, @PageableDefault(sort = "createAt", direction = Sort.Direction.DESC) Pageable pageable) {
        Page<DroneDto> dronePage = droneService.findByCondition(droneDto, pageable);
        return ResponseEntity.ok(dronePage);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> update(@RequestBody @Validated DroneDto droneDto, @PathVariable("id") String id) {
        droneService.update(droneDto, id);
        return ResponseEntity.ok().build();
    }
}