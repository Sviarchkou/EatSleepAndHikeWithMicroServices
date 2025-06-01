package com.example.app.web.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TripTypeDto implements Serializable {
    private int id;
    private String name;
    //private Set<DifficultyCategoryDto> difficultyCategoryDtos = new HashSet<>();

    // private Set<Integer> difficultyCategoryIds = new HashSet<>();

/*    public TripTypeDto(TripType tripType) {
        this.id = tripType.getId();
        this.name = tripType.getName();
        this.difficultyCategoryIds = new HashSet<>(
                tripType.getDifficultyCategory().stream().map(DifficultyCategory::getId).toList());
    }*/
}
