package com.fleet.dispatch.mapper;

import com.fleet.common.mapper.EntityMapper;
import com.fleet.dispatch.dto.DispatchDto;
import com.fleet.dispatch.model.Dispatch;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface DispatchMapper extends EntityMapper<DispatchDto, Dispatch> {
}