package com.example.app.web.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RouteDto implements Serializable {
    private UUID id;
    private String name;
    private String description;
    //private List<RoutePointDto> routePointDtos = new ArrayList<>();
    private Set<CountryDto> countryDtos = new HashSet<>();
    // private Set<TripDto> tripDtos = new HashSet<>();


    //    private Queue<UUID> routePointIds = new PriorityQueue<>();
//    private Set<Integer> countryIds  = new HashSet<>();;
//    private Set<UUID> tripIds = new HashSet<>()
/*    public RouteDto(Route route) {
        this.id = route.getId();
        this.name = route.getName();
        this.description = route.getDescription();
        this.routePointIds = new PriorityQueue<>(route.getRoutePoints().stream().map(RoutePoint::getId).toList());
        this.countryIds = new HashSet<>(route.getCountries().stream().map(Country::getId).toList());
    }*/

}
