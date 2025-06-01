package com.example.app.web.controller;

import com.example.app.mapper.TripTypeMapper;
import com.example.app.service.TripTypeService;
import com.example.app.web.dto.TripTypeDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/trip-types")
public class TripTypeController {

    private final TripTypeService tripTypeService;
    private final TripTypeMapper tripTypeMapper;

    @GetMapping
    public ResponseEntity<List<TripTypeDto>> getAll() {
        return ResponseEntity.ok(tripTypeService.findAll().stream().map(tripTypeMapper::toDto).toList());
    }

    @GetMapping("{id}")
    public ResponseEntity<TripTypeDto> getById(@PathVariable int id) {
        return ResponseEntity.ok(tripTypeMapper.toDto(tripTypeService.findById(id).orElseThrow()));
    }

    @PostMapping
    public ResponseEntity<TripTypeDto> create(@RequestBody TripTypeDto tripTypeDto) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(tripTypeMapper.toDto(tripTypeService.create(tripTypeMapper.toEntity(tripTypeDto))));
    }

    @PutMapping
    public ResponseEntity<TripTypeDto> update(@RequestBody TripTypeDto tripTypeDto) {
        return ResponseEntity.ok(tripTypeMapper.toDto(tripTypeService.update(tripTypeMapper.toEntity(tripTypeDto))));
    }

    @DeleteMapping
    public ResponseEntity<TripTypeDto> delete(@RequestBody TripTypeDto tripTypeDto) {
        tripTypeService.delete(tripTypeMapper.toEntity(tripTypeDto));
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("{id}")
    public ResponseEntity<TripTypeDto> deleteByIf(@PathVariable int id) {
        tripTypeService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
