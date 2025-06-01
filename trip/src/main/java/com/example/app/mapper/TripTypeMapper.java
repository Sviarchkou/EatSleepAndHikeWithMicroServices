package com.example.app.mapper;

import com.example.app.entity.TripType;
import com.example.app.web.dto.TripTypeDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TripTypeMapper {
    TripType toEntity(TripTypeDto tripTypeDto);

    TripTypeDto toDto(TripType tripType);
}
