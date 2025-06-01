package com.example.app.web.controller;

import com.example.app.mapper.CountryMapper;
import com.example.app.mapper.RouteMapper;
import com.example.app.service.RouteService;
import com.example.app.web.dto.CountryDto;
import com.example.app.web.dto.RouteDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@RestController
@RequestMapping("/routes")
public class RouteController {
    private final RouteService routeService;
    private final RouteMapper routeMapper;
    private final CountryMapper countryMapper;

    @GetMapping()
    public ResponseEntity<List<RouteDto>> getAll() {
        return ResponseEntity.ok(routeService.findAll().stream().map(routeMapper::toDto).toList());
    }

    @GetMapping("{id}")
    public ResponseEntity<RouteDto> getById(@PathVariable UUID id) {
        return ResponseEntity.ok(routeMapper.toDto(routeService.findById(id).orElseThrow()));
    }

    @GetMapping("in-country")
    public ResponseEntity<List<RouteDto>> getByCountryId(@RequestBody CountryDto countryDto) {
        return ResponseEntity.ok(routeService.findByCountry(countryMapper.toEntity(countryDto))
                .stream().map(routeMapper::toDto).toList());
    }

    @GetMapping("in-countries")
    public ResponseEntity<List<RouteDto>> getByCountries(@RequestBody Set<CountryDto> countryDtos) {
        return ResponseEntity.ok(
                routeService.findByCountries(countryDtos.stream().map(countryMapper::toEntity)
                                .collect(Collectors.toSet()))
                .stream().map(routeMapper::toDto).toList());
    }

    @PostMapping
    public ResponseEntity<RouteDto> create(@RequestBody RouteDto routeDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(
                routeMapper.toDto(routeService.create(routeMapper.toEntity(routeDto))));
    }

    @PutMapping
    public ResponseEntity<RouteDto> update(@RequestBody RouteDto routeDto) {
        return ResponseEntity.ok(
                routeMapper.toDto(routeService.update(routeMapper.toEntity(routeDto))));
    }

    @DeleteMapping
    public ResponseEntity<Void> delete(@RequestBody RouteDto routeDto) {
        routeService.delete(routeMapper.toEntity(routeDto));
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteById(@PathVariable UUID id) {
        routeService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
