package com.example.app.service;

import com.example.app.entity.Report;
import com.example.app.repository.ReportRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class ReportService {

    private final ReportRepository reportRepository;
    private final TripService tripService;

    public Optional<Report> findById(UUID id) {
        return reportRepository.findById(id);
    }

    public List<Report> findAll() {
        return reportRepository.findAll();
    }

    public Report create(Report report) {
        if (Objects.isNull(report.getStoragePath())){
            throw new IllegalArgumentException("");
        }
        var trip = tripService.findById(report.getTrip().getId()).orElseThrow();
        report.setTrip(trip);
        return reportRepository.save(report);
    }
    public Report update(Report report) {
        if (Objects.isNull(report.getStoragePath())){
            throw new IllegalArgumentException("");
        }
        var reportDb = reportRepository.findById(report.getId()).orElseThrow();
        reportDb.setTrip(report.getTrip());
        reportDb.setStoragePath(report.getStoragePath());
        return reportRepository.save(report);
    }

    public void delete(Report report) {
        reportRepository.delete(report);
    }
}
