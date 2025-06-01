package com.example.app.web.controller;

import com.example.app.mapper.ContinentMapper;
import com.example.app.service.ContinentService;
import com.example.app.web.dto.ContinentDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/continents")
public class ContinentController {
    private final ContinentService continentService;
    private final ContinentMapper continentMapper;

    @GetMapping()
    public ResponseEntity<List<ContinentDto>> getAll() {
        return ResponseEntity.ok(continentService.findAll().stream().map(continentMapper::toDto).toList());
    }

    @GetMapping("{id}")
    public ResponseEntity<ContinentDto> getById(@PathVariable int id) {
        return ResponseEntity.ok(continentMapper.toDto(continentService.findById(id).orElseThrow()));
    }

    @PostMapping
    public ResponseEntity<ContinentDto> create(@RequestBody ContinentDto continentDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(
                continentMapper.toDto(continentService.create(continentMapper.toEntity(continentDto))));
    }
}
