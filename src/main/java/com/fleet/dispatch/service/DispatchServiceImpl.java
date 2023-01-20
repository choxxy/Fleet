package com.fleet.dispatch.service;

import com.fleet.dispatch.dto.DispatchDto;
import com.fleet.dispatch.mapper.DispatchMapper;
import com.fleet.dispatch.model.Dispatch;
import com.fleet.dispatch.repository.DispatchRepository;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@Transactional
public class DispatchServiceImpl implements DispatchService {
    private final DispatchRepository repository;
    private final DispatchMapper dispatchMapper;

    public DispatchServiceImpl(DispatchRepository repository, DispatchMapper dispatchMapper) {
        this.repository = repository;
        this.dispatchMapper = dispatchMapper;
    }

    @Override
    public DispatchDto save(DispatchDto dispatchDto) {
        Dispatch entity = dispatchMapper.toEntity(dispatchDto);
        return dispatchMapper.toDto(repository.save(entity));
    }

    @Override
    public void deleteById(Integer id) {
        repository.deleteById(id);
    }

    @Override
    public DispatchDto findById(Integer id) {
        return dispatchMapper.toDto(repository.findById(id).orElseThrow(ResourceNotFoundException::new));
    }

    @Override
    public Page<DispatchDto> findByCondition(DispatchDto dispatchDto, Pageable pageable) {
        Page<Dispatch> entityPage = repository.findAll(pageable);
        List<Dispatch> entities = entityPage.getContent();
        return new PageImpl<>(dispatchMapper.toDto(entities), pageable, entityPage.getTotalElements());
    }

    @Override
    public DispatchDto update(DispatchDto dispatchDto, Integer id) {
        DispatchDto data = findById(id);
        Dispatch entity = dispatchMapper.toEntity(dispatchDto);
        BeanUtils.copyProperties(data, entity);
        return save(dispatchMapper.toDto(entity));
    }
}