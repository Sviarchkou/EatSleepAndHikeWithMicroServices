package com.example.app.web.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CountryDto {
    private int id;
    private String name;
    private ContinentDto continentDto;

    // private int continentId;

/*    public CountryDto(Country country) {
        this.id = country.getId();
        this.name = country.getName();
        this.continentId = country.getContinent().getId();
    }*/
}
