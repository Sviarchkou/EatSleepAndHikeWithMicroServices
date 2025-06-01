package com.example.app.repository;

import com.example.app.entity.Country;
import com.example.app.entity.Route;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;
import java.util.UUID;

@Repository
public interface RouteRepository extends JpaRepository<Route, UUID> {

    List<Route> findByCountriesId(int countryId);

    List<Route> findByCountriesIdIn(List<Integer> countryIds);

    List<Route> findAllByCountries(Set<Country> country);

}
