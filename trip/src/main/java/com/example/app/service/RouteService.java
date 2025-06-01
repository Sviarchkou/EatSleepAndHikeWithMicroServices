package com.example.app.service;

import com.example.app.entity.Country;
import com.example.app.entity.Route;
import com.example.app.repository.RouteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;

@RequiredArgsConstructor
@Service
public class RouteService {

    private final RouteRepository routeRepository;
    private final CountryService countryService;

    public Optional<Route> findById(UUID id) {
        return routeRepository.findById(id);
    }

    public List<Route> findAll() {
        return routeRepository.findAll();
    }

    public List<Route> findByCountry(Country country) {
        return routeRepository.findByCountriesId(country.getId());
    }

    public List<Route> findByCountries(Set<Country> countries) {
        return routeRepository.findAllByCountries(countries);
    }

    public List<Route> findByCountries(List<Country> countries) {
        return routeRepository.findByCountriesIdIn(countries.stream().map(Country::getId).toList());
    }

    public Route create(Route route) {
        if (Objects.isNull(route.getName())){
            throw new IllegalArgumentException("");
        }
        if (route.getCountries().isEmpty()){
            throw new IllegalArgumentException("");
        }
        Set<Country> countries = new HashSet<>();
        for(Country country : route.getCountries()){
            countries.add(countryService.findById(country.getId()).orElseThrow());
        }
        route.setCountries(countries);
        return routeRepository.save(route);
    }

    public Route update(Route route) {
        if (Objects.isNull(route.getName())){
            throw new IllegalArgumentException("");
        }
        if (Objects.isNull(route.getId())){
            throw new IllegalArgumentException("");
        }
        if (route.getCountries().isEmpty()){
            throw new IllegalArgumentException("");
        }
        var routeDb = routeRepository.findById(route.getId()).orElseThrow();
        routeDb.setRoutePoints(route.getRoutePoints());
        routeDb.setName(route.getName());
        routeDb.setCountries(route.getCountries());
        routeDb.setTrips(route.getTrips());
        routeDb.setDescription(route.getDescription());
        return routeRepository.save(routeDb);
    }

    public void delete(Route route) {
        routeRepository.delete(route);
    }

    public void deleteById(UUID id) {
        routeRepository.deleteById(id);
    }
}
