package com.example.app.web.controller;

import com.example.app.mapper.TripMapper;
import com.example.app.mapper.TripTypeMapper;
import com.example.app.service.TripService;
import com.example.app.web.dto.DifficultyRangeDto;
import com.example.app.web.dto.TripDto;
import com.example.app.web.dto.TripTypeDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/trips")
@RequiredArgsConstructor
@Slf4j
public class TripController {

    private final TripService tripService;
    private final TripMapper tripMapper;
    private final TripTypeMapper tripTypeMapper;
    @GetMapping("")
    public ResponseEntity<List<TripDto>> getTrips() {
        log.info("get trips");
        return ResponseEntity.ok(tripService.findAll().stream().map(tripMapper::toDto).toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<TripDto> getTripById(@PathVariable UUID id) {
        log.info("get trip by id: {}", id);
        return ResponseEntity.ok(tripMapper.toDto(tripService.findById(id).orElseThrow()));
    }

    @GetMapping("along-route/{id}")
    public ResponseEntity<List<TripDto>> getTripsByRouteId(@PathVariable UUID id) {
        return ResponseEntity.ok(tripService.findAllByRouteId(id).stream().map(tripMapper::toDto).toList());
    }

    /*
    @PostMapping("along-route")
    public ResponseEntity<List<TripDto>> getTripsByRoute(@RequestBody RouteDto routeDto) {
        return ResponseEntity.ok(tripService.findAllByRoute(routeDto).stream().map(tripMapper::toDto).toList());
    }*/

/*    @PostMapping("difficulty-category-filter/")
    public ResponseEntity<List<TripDto>> getAllByDifficultyCategory(@RequestBody DifficultyCategoryDto difficultyCategoryDto) {
        return ResponseEntity.ok(tripService.findAllByDifficultyCategory(
                difficultyCategoryMapper.toEntity(difficultyCategoryDto))
                .stream().map(tripMapper::toDto).toList());
    }*/

/*    @PostMapping("trip-type-filter/")
    public ResponseEntity<List<TripDto>> getAllByTripType(@RequestBody TripTypeDto tripTypeDto) {
        return ResponseEntity.ok(tripService.findAllByTripType(
                tripTypeMapper.toEntity(tripTypeDto))
                .stream().map(tripMapper::toDto).toList());
    }*/

/*    @PostMapping("trip-type-and-difficulty-category-filter/")
    public ResponseEntity<List<TripDto>> getAllByTripType(@RequestBody TripTypeDto tripTypeDto, @RequestBody DifficultyCategoryDto difficultyCategoryDto) {
        return ResponseEntity.ok(tripService.findAllByDifficultyCategoryAndTripType(
                difficultyCategoryMapper.toEntity(difficultyCategoryDto),
                tripTypeMapper.toEntity(tripTypeDto))
                .stream().map(tripMapper::toDto).toList());
    }*/

/*    @PostMapping("difficulty-filter/")
    public ResponseEntity<List<TripDto>> getAllByTripType(@RequestBody List<TripTypeDifCatDescDto> tripTypeDifCatDescDtos) {
        return ResponseEntity.ok(tripService.findTripByDifficultyOverlap(
                tripTypeDifCatDescDtos.stream().map(tripTypeDifCatDescMapper::toEntity)
                        .collect(Collectors.toList()))
                .stream().map(tripMapper::toDto)
                .collect(Collectors.toList()));
    }*/
    @PostMapping("trip-type/")
    public ResponseEntity<List<TripDto>> getAllByTripType(@RequestBody TripTypeDto tripTypeDto) {
        return ResponseEntity.ok(tripService.findAllByTripType(tripTypeMapper.toEntity(tripTypeDto))
                .stream().map(tripMapper::toDto)
                .collect(Collectors.toList()));
    }
    @GetMapping("trip-type/{id}")
    public ResponseEntity<List<TripDto>> getAllByTripType(@PathVariable int id) {
        return ResponseEntity.ok(tripService.findAllByTripTypeId(id)
                .stream().map(tripMapper::toDto)
                .collect(Collectors.toList()));
    }

    @PostMapping("difficulty-overlap/")
    public ResponseEntity<List<TripDto>> getAllByDifficultOverlap(@RequestBody DifficultyRangeDto request) {
        return ResponseEntity.ok(tripService.findAllByDifficultyOverlap(request.getMin(), request.getMax())
                .stream().map(tripMapper::toDto)
                .collect(Collectors.toList()));
    }

   /* @PostMapping("difficulty-overlap-trip/")
    public ResponseEntity<List<TripDto>> getAllByDifficultOverlapAndTripType(
            @RequestBody DifficultyCategories min,
            @RequestBody DifficultyCategories max,
            @RequestBody TripTypeDto tripTypeDto) {
        return ResponseEntity.ok(
                tripService.findAllByDifficultyOverlapAndTripType(
                        min, max,tripTypeMapper.toEntity(tripTypeDto))
                .stream().map(tripMapper::toDto)
                .collect(Collectors.toList()));
    }*/

    @PostMapping("difficulty-overlap-trip/{id}")
    public ResponseEntity<List<TripDto>> getAllByDifficultOverlapAndTripType(
            @RequestBody DifficultyRangeDto request, @PathVariable int id) {
        return ResponseEntity.ok(
                tripService.findAllByDifficultyOverlapAndTripTypeId(
                                request.getMin(), request.getMax(), id)
                        .stream().map(tripMapper::toDto)
                        .collect(Collectors.toList()));
    }

    @PostMapping
    public ResponseEntity<TripDto> create(@RequestBody TripDto tripDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(
                tripMapper.toDto(tripService.create(tripMapper.toEntity(tripDto))));
    }

    @PutMapping
    public ResponseEntity<TripDto> update(@RequestBody TripDto tripDto) {
        return ResponseEntity.ok(
                tripMapper.toDto(tripService.update(tripMapper.toEntity(tripDto))));
    }

    @DeleteMapping
    public ResponseEntity<Void> delete(@RequestBody TripDto tripDto) {
        tripService.delete(tripMapper.toEntity(tripDto));
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteById(@PathVariable UUID id) {
        tripService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

/*
    @PostMapping("")
    public ResponseEntity<TripDto> getTripById(@PathVariable TripDto tripDto) {
        return ResponseEntity.ok(new TripDto(tripService.findById(id).orElseThrow()));
    }*/

}
