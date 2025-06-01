package com.example.app.mapper;

import com.example.app.entity.Trip;
import com.example.app.web.dto.TripDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {
        RouteMapper.class,
        TripTypeMapper.class,
//        DifficultyCategoryMapper.class,
//        TripTypeDifCatDescMapper.class,
        ReportMapper.class

})
public interface TripMapper {
    @Mapping(source = "routeDto", target = "route")
//    @Mapping(source = "tripTypeDifCatDescDtos", target = "tripTypeDifCatDescs")
    @Mapping(source = "tripTypeDto", target = "tripType")
//    @Mapping(source = "difficultyCategoryDto", target = "difficultyCategory")
    @Mapping(source = "reportDtos", target = "reports")
    Trip toEntity(TripDto tripDto);

    @Mapping(source = "route", target = "routeDto")
//    @Mapping(source = "tripTypeDifCatDescs", target = "tripTypeDifCatDescDtos")
    @Mapping(source = "tripType", target = "tripTypeDto")
//    @Mapping(source = "difficultyCategory", target = "difficultyCategoryDto")
    @Mapping(source = "reports", target = "reportDtos")
    TripDto toDto(Trip trip);

}
