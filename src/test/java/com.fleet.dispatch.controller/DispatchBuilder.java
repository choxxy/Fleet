package com.fleet.dispatch.controller;

import com.fleet.dispatch.dto.DispatchDto;

import java.util.Collections;
import java.util.List;

public class DispatchBuilder {
    public static List<String> getIds() {
        return Collections.singletonList("1");
    }

    public static DispatchDto getDto() {
        DispatchDto dto = new DispatchDto();
        dto.setId(1);
        return dto;
    }
}