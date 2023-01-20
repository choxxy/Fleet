package com.fleet.medication.model;


import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;


@Entity
@Table(name = "medications")
@Data
public class Medication {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Integer id;

    @Column(name = "name")
    @NotBlank
    @Pattern(regexp = "^[a-zA-Z0-9_-]*$")
    private String name;

    @Column(name = "weight")
    @Min(value = 0)
    private Integer weight;

    @Column(name = "code")
    @NotBlank
    @Pattern(regexp = "^[A-Z_0-9]*$")
    private String code;

    @Column(name = "image")
    private String image;

}
