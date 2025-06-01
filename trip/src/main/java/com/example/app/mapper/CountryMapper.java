package com.example.app.mapper;

import com.example.app.entity.Country;
import com.example.app.web.dto.CountryDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = ContinentMapper.class)
public interface CountryMapper {
    @Mapping(source = "continent", target = "continentDto")
    CountryDto toDto(Country country);

    @Mapping(source = "continentDto", target = "continent")
    Country toEntity(CountryDto countryDto);
}
