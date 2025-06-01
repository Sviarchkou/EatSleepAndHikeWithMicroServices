package com.example.app.service;

import com.example.app.entity.TripType;
import com.example.app.repository.TripTypeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class TripTypeService {

    private final TripTypeRepository tripTypeRepository;

    public Optional<TripType> findById(int id) {
        return tripTypeRepository.findById(id);
    }

    public List<TripType> findAll() {
        return tripTypeRepository.findAll();
    }

    public TripType create(TripType tripType) {
        if (Objects.isNull(tripType.getName())) {
            throw new IllegalArgumentException("");
        }
        return tripTypeRepository.save(tripType);
    }

    public TripType update(TripType tripType) {
        if (Objects.isNull(tripType.getName())) {
            throw new IllegalArgumentException("");
        }
        var tripTypeDb = tripTypeRepository.findById(tripType.getId()).orElseThrow();
        tripTypeDb.setName(tripType.getName());
//        tripTypeDb.setTrips(tripType.getTrips());
//        tripTypeDb.setDifficultyCategory(tripType.getDifficultyCategory());
        return tripTypeRepository.save(tripType);
    }

    public void delete(TripType tripType) {
        tripTypeRepository.delete(tripType);
    }

    public void delete(int id) {
        tripTypeRepository.deleteById(id);
    }

}
