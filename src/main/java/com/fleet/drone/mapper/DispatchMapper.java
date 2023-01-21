package com.fleet.drone.mapper;


import com.fleet.common.mapper.EntityMapper;
import com.fleet.drone.dto.DispatchDto;
import com.fleet.drone.model.Dispatch;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface DispatchMapper extends EntityMapper<DispatchDto, Dispatch> {
}