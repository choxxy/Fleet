package com.fleet.dispatch.service;

import com.fleet.dispatch.dto.DispatchDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface DispatchService {
    DispatchDto save(DispatchDto dispatchDto);

    void deleteById(Integer id);

    DispatchDto findById(Integer id);

    Page<DispatchDto> findByCondition(DispatchDto dispatchDto, Pageable pageable);

    DispatchDto update(DispatchDto dispatchDto, Integer id);
}
