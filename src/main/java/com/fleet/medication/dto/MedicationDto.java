package com.fleet.medication.dto;

import com.fleet.common.dto.AbstractDto;
import lombok.Data;

@Data
public class MedicationDto extends AbstractDto<Long> {
    private Long id;
    private String name;
    private Integer weight;
    private String code;
    private String image;
}