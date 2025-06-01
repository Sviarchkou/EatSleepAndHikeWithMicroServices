package com.example.app.mapper;

import com.example.app.entity.Continent;
import com.example.app.web.dto.ContinentDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ContinentMapper {
    @Mapping(target = "countries", ignore = true)
    Continent toEntity(ContinentDto ContinentDto);

    ContinentDto toDto(Continent continent);
}