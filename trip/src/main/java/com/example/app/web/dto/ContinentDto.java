package com.example.app.web.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ContinentDto {
    private int id;
    private String name;

    /*public ContinentDto(Continent continent) {
        this.id = continent.getId();
        this.name = continent.getName();
    }*/
}
