package com.example.app.web.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DifficultyCategoryDto {
    private int id;
    private int difficulty;
    private String description;
    private TripTypeDto tripTypeDto;

/*    public DifficultyCategoryDto(DifficultyCategory difficultyCategory) {
        this.id = difficultyCategory.getId();
        this.difficulty = difficultyCategory.getDifficulty();
        this.description = difficultyCategory.getDescription();
    }*/

}
