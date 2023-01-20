package com.fleet.dispatch.controller;

import com.fleet.dispatch.dto.DispatchDto;
import com.fleet.dispatch.service.DispatchService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RequestMapping("/api/dispatch")
@RestController
@Slf4j
public class DispatchController {
    private final DispatchService dispatchService;

    public DispatchController(DispatchService dispatchService) {
        this.dispatchService = dispatchService;
    }

    @PostMapping
    public ResponseEntity<Void> save(@RequestBody @Validated DispatchDto dispatchDto) {
        dispatchService.save(dispatchDto);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<DispatchDto> findById(@PathVariable("id") Integer id) {
        DispatchDto dispatch = dispatchService.findById(id);
        return ResponseEntity.ok(dispatch);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Integer id) {
        Optional.ofNullable(dispatchService.findById(id)).orElseThrow(() -> {
            log.error("Unable to delete non-existent data！");
            return new ResourceNotFoundException();
        });
        dispatchService.deleteById(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/page-query")
    public ResponseEntity<Page<DispatchDto>> pageQuery(DispatchDto dispatchDto, @PageableDefault(sort = "createAt", direction = Sort.Direction.DESC) Pageable pageable) {
        Page<DispatchDto> dispatchPage = dispatchService.findByCondition(dispatchDto, pageable);
        return ResponseEntity.ok(dispatchPage);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> update(@RequestBody @Validated DispatchDto dispatchDto, @PathVariable("id") Integer id) {
        dispatchService.update(dispatchDto, id);
        return ResponseEntity.ok().build();
    }
}