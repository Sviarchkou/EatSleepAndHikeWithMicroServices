package com.example.app.web.dto;

import com.example.app.entity.DifficultyCategory;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DifficultyRangeDto {
    private DifficultyCategory min;
    private DifficultyCategory max;
}
