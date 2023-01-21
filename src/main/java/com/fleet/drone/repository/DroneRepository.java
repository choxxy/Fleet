package com.fleet.drone.repository;

import com.fleet.drone.model.Drone;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface DroneRepository extends JpaRepository<Drone, String>, JpaSpecificationExecutor<Drone> {
    @Query("SELECT batteryLevel FROM Drone WHERE serialNumber = :serialNumber")
    Integer findDroneBatteryCapacity(String serialNumber);
}