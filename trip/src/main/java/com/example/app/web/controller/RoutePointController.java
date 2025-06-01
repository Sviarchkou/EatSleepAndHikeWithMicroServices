package com.example.app.web.controller;

import com.example.app.entity.RoutePoint;
import com.example.app.mapper.RoutePointMapper;
import com.example.app.service.RoutePointService;
import com.example.app.web.dto.RouteDto;
import com.example.app.web.dto.RoutePointDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@RestController
@RequestMapping("/route-points")
public class RoutePointController {

    private final RoutePointService routePointService;
    private final RoutePointMapper routePointMapper;

    @GetMapping("{id}")
    public ResponseEntity<RoutePointDto> getById(@PathVariable UUID id) {
        return ResponseEntity.ok(
                routePointMapper.toDto(routePointService.findById(id).orElseThrow()));
    }

    @GetMapping("in-route/{id}")
    public ResponseEntity<List<RoutePointDto>> getByRouteId(@PathVariable UUID id) {
        return ResponseEntity.ok(
                routePointService.findAllByRouteId(id).stream().map(routePointMapper::toDto).collect(Collectors.toList()));
    }

    @PostMapping
    public ResponseEntity<RoutePointDto> create(@RequestBody RoutePointDto routePointDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(
                routePointMapper.toDto(routePointService.create(routePointMapper.toEntity(routePointDto))));
    }

    @PostMapping("/all")
    public ResponseEntity<List<RoutePointDto>> createAll(@RequestBody List<RoutePointDto> routePointDtos) {
        List<RoutePoint> routePoints = routePointService.
                createAllCheckingSN(routePointDtos.stream().map(routePointMapper::toEntity).collect(Collectors.toList()));
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(routePoints.stream().map(routePointMapper::toDto).toList());
    }

    @PutMapping
    public ResponseEntity<RoutePointDto> update(@RequestBody RoutePointDto routePointDto) {
        return ResponseEntity.ok(routePointMapper.toDto(
                routePointService.update(routePointMapper.toEntity(routePointDto))));
    }

    @PutMapping("/all")
    public ResponseEntity<List<RoutePointDto>> updateAll(@RequestBody List<RoutePointDto> routePointDtos) {
        List<RoutePoint> routePoints = routePointService.updateAll(
                routePointDtos.stream().map(routePointMapper::toEntity).collect(Collectors.toList()));
        return ResponseEntity.ok(routePoints.stream().map(routePointMapper::toDto).toList());
    }

    @PutMapping("{routeId}")
    public ResponseEntity<List<RoutePointDto>> updateAllInRoute(@RequestBody List<RoutePointDto> routePointDtos, @PathVariable UUID routeId) {
        List<RoutePoint> routePoints = routePointService.updateAll(
                routePointDtos.stream().map(routePointMapper::toEntity).collect(Collectors.toList()), routeId);
        return ResponseEntity.ok(routePoints.stream().map(routePointMapper::toDto).toList());
    }

    @DeleteMapping
    public ResponseEntity<RoutePointDto> delete(@RequestBody RoutePointDto routePointDto) {
        routePointService.delete(routePointMapper.toEntity(routePointDto));
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("{id}")
    public ResponseEntity<RoutePointDto> deleteById(@PathVariable UUID id) {
        routePointService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/all")
    public ResponseEntity<List<RoutePointDto>> deleteAll(@RequestBody List<RoutePointDto> routePointDtos) {
        routePointService.deleteAll(routePointDtos.stream().map(routePointMapper::toEntity).collect(Collectors.toList()));
        return ResponseEntity.noContent().build();
    }

}
