package com.example.app.mapper;

import com.example.app.entity.Route;
import com.example.app.web.dto.RouteDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;


@Mapper(componentModel = "spring", uses = {
        RoutePointMapper.class,
        CountryMapper.class
})
public interface RouteMapper {
    //@Mapping(source = "routePointDtos", target = "routePoints")
    @Mapping(source = "countryDtos", target = "countries")
    Route toEntity(RouteDto routeDto);

   // @Mapping(source = "routePoints", target = "routePointDtos")
    @Mapping(source = "countries", target = "countryDtos")
    RouteDto toDto(Route route);
}
