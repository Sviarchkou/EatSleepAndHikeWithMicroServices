package com.example.app.web.controller;

import com.example.app.entity.Country;
import com.example.app.mapper.CountryMapper;
import com.example.app.service.CountryService;
import com.example.app.web.dto.CountryDto;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@RestController
@RequestMapping("/countries")
public class CountryController {
    private final CountryService countryService;
    private final CountryMapper countryMapper;

    @GetMapping
    public ResponseEntity<List<CountryDto>> getAll() {
        List<Country> countries = countryService.findAll();
        countries.sort(Comparator.comparing(Country::getName));
        return ResponseEntity.ok(
                countries.stream().map(countryMapper::toDto).toList());
    }

    @GetMapping("{id}")
    public ResponseEntity<CountryDto> getById(@PathVariable int id) {
        return ResponseEntity.ok(
                countryMapper.toDto(countryService.findById(id).orElseThrow()));
    }

    @PostMapping
    public ResponseEntity<CountryDto> create(@RequestBody CountryDto countryDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(
                countryMapper.toDto(countryService.create(countryMapper.toEntity(countryDto))));
    }

    @PutMapping
    public ResponseEntity<CountryDto> update(@RequestBody CountryDto countryDto) {
        return ResponseEntity.ok(
                countryMapper.toDto(countryService.update(countryMapper.toEntity(countryDto))));
    }

    @DeleteMapping
    public ResponseEntity<Void> delete(@RequestBody CountryDto countryDto) {
        countryService.delete(countryMapper.toEntity(countryDto));
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteById(@PathVariable int id) {
        countryService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PostConstruct
    public void addCountries(){

    }

}
