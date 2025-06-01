package com.example.app.web.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RoutePointDto implements Serializable {
    private UUID id;
    private double latitude;
    private double longitude;
    private int sequenceNumber;
    private String description;
    private RouteDto routeDto;

   /* public RoutePointDto(RoutePoint routePoint) {
        this.id = routePoint.getId();
        this.latitude = routePoint.getLatitude();
        this.longitude = routePoint.getLongitude();
        this.sequenceNumber = routePoint.getSequenceNumber();
        this.description = routePoint.getDescription();
    }*/
}
