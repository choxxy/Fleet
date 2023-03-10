package com.fleet.drone.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Entity
@Table(name = "drones")
@Data
public class Drone {
    @Id
    @Column(name = "serial_number", length = 100)
    String serialNumber;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    @NotNull
    DroneStatus status;

    @Column(name = "model")
    @Enumerated(EnumType.STRING)
    @NotNull
    DroneModel model;

    @Column(name = "weight_limit")
    @Min(value = 0)
    @Max(value = 500)
    private Integer weightLimit;

    @Column(name = "battery_level")
    @Min(value = 0)
    @Max(value = 100)
    private Integer batteryLevel;

    @Column(name = "state")
    @Enumerated(EnumType.STRING)
    @NotNull
    private DroneState state;

}
