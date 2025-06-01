package com.example.app.web.dto;

import com.example.app.entity.DifficultyCategory;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TripDto {

    private UUID id;

    private String name;

    private String description;

    private int duration;

    private double distance;

    private RouteDto routeDto;

    private TripTypeDto tripTypeDto;

    private Set<ReportDto> reportDtos = new HashSet<>();

    private DifficultyCategory minDifficultyCategory;

    private DifficultyCategory maxDifficultyCategory;
}
