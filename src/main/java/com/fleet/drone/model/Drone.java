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
    DroneModel model;
    @Column(name = "weight_limit")
    @Min(value = 0)
    @Max(value = 500)
    private Integer weightLimit;
    @Column(name = "battery_capacity")
    @Min(value = 0)
    @Max(value = 100)
    private Integer batteryCapacity;
    @Column(name = "state")
    @Enumerated(EnumType.STRING)
    @NotNull
    private DroneState state;

}
