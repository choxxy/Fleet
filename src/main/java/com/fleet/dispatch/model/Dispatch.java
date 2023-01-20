package com.fleet.dispatch.model;

import com.fleet.drone.model.Drone;
import com.fleet.medication.model.Medication;
import jakarta.persistence.*;
import lombok.Data;


@Entity
@Table(name = "dispatches")
@Data
public class Dispatch {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @ManyToOne
    @JoinColumn(name = "drone_id", nullable = false)
    private Drone drone;
    @ManyToOne
    @JoinColumn(name = "medication_id", nullable = false)
    private Medication medication;
}
