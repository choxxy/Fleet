package com.fleet.drone.repository;

import com.fleet.drone.model.Dispatch;
import com.fleet.drone.model.Drone;
import com.fleet.medication.model.Medication;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DispatchRepository extends JpaRepository<Dispatch, Integer>, JpaSpecificationExecutor<Dispatch> {
    @Query("SELECT d FROM Drone d LEFT JOIN Dispatch dp ON d.serialNumber = dp.drone.serialNumber WHERE dp.medication.id IS NULL")
    List<Drone> findAvailableDrones();

    @Query("SELECT m FROM Medication m JOIN Dispatch d ON  m.id = d.medication.id WHERE d.drone.serialNumber = :serialNumber")
    List<Medication> findMedicationsByDroneId(@Param("serialNumber") String serialNumber);
}