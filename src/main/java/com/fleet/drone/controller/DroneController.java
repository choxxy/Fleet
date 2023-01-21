package com.fleet.drone.controller;

import com.fleet.drone.dto.DroneDto;
import com.fleet.drone.service.DispatchService;
import com.fleet.drone.service.DroneService;
import com.fleet.medication.dto.MedicationDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/api/drone")
@RestController
@Slf4j
public class DroneController {
    private final DroneService droneService;
    private final DispatchService dispatchService;

    public DroneController(DroneService droneService, DispatchService dispatchService) {
        this.droneService = droneService;
        this.dispatchService = dispatchService;
    }

    // drone registration
    @PostMapping("/register")
    public ResponseEntity<DroneDto> save(@RequestBody DroneDto droneDto) {
        DroneDto result =  droneService.register(droneDto);
        return new ResponseEntity<>(result, HttpStatus.CREATED);
    }

    // retrieve a drone
    @GetMapping("/{serialNumber}")
    public ResponseEntity<DroneDto> getDrone(@PathVariable("serialNumber") String id) {
        DroneDto drone = droneService.findBySerialNumber(id);
        return ResponseEntity.ok(drone);
    }

    // retrieve a drone battery level
    @GetMapping("/{serialNumber}/battery")
    public ResponseEntity<String> getDroneBatteryLevel(@PathVariable("serialNumber") String serialNumber) {
        String batterLevel = droneService.getDroneBatteryLevel(serialNumber);
        return ResponseEntity.ok(batterLevel);
    }

    // get all drones
    @GetMapping("/")
    public ResponseEntity<List<DroneDto>> getAllDrones() {
        List<DroneDto> drones = droneService.findAll();
        return ResponseEntity.ok(drones);
    }

    // get available drones for loading
    @GetMapping("/available")
    public ResponseEntity<List<DroneDto>> getAvailableDrones() {
        List<DroneDto> droneDtoList = dispatchService.findAvailableDrones();
        return ResponseEntity.ok(droneDtoList);
    }

    // get medication load for drone
    @GetMapping("/{serialNumber}/medications")
    public ResponseEntity<List<MedicationDto>> getDroneLoadStatus(@PathVariable("serialNumber") String serialNumber) {
        List<MedicationDto> medicationDtoList = dispatchService.findDroneMedicationLoad(serialNumber);
        return ResponseEntity.ok(medicationDtoList);
    }

    // load a drone with medication
    @PostMapping("/{serialNumber}/load")
    public ResponseEntity<Void> loadDrone(@PathVariable("serialNumber") String serialNumber, @RequestBody List<Integer> medicationIdList) {
        dispatchService.load(serialNumber, medicationIdList);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> update(@RequestBody @Validated DroneDto droneDto, @PathVariable("serialNumber") String serialNumber) {
        droneService.update(droneDto, serialNumber);
        return ResponseEntity.ok().build();
    }
}