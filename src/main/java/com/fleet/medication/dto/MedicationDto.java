package com.fleet.medication.dto;

import lombok.Data;

@Data
public class MedicationDto {
    private Long id;
    private String name;
    private Integer weight;
    private String code;
    private String image;
}