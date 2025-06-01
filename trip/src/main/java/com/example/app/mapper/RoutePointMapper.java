package com.example.app.mapper;

import com.example.app.entity.RoutePoint;
import com.example.app.web.dto.RoutePointDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = RouteMapper.class)
public interface RoutePointMapper {

    @Mapping(source = "routeDto", target = "route")
    RoutePoint toEntity(RoutePointDto routePointDto);

    @Mapping(source = "route", target = "routeDto")
    RoutePointDto toDto(RoutePoint routePoint);

}
