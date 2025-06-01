package com.example.app.mapper;

import com.example.app.entity.Report;
import com.example.app.web.dto.ReportDto;
import org.mapstruct.Mapper;


@Mapper(componentModel = "spring", uses = {
    RouteMapper.class
})
public interface ReportMapper {

    Report toEntity(ReportDto reportDto);

    ReportDto toDto(Report report);
}
