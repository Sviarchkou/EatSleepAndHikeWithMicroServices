package com.example.app.service;

import com.example.app.entity.Continent;
import com.example.app.entity.Country;
import com.example.app.repository.CountryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class CountryService {

    private final CountryRepository countryRepository;
    private final ContinentService continentService;

    public Optional<Country> findById(int id){
        return countryRepository.findById(id);
    }

    public List<Country> findAllByContinent(Continent continent){
        return countryRepository.findAllByContinentId(continent.getId());
    }

    public List<Country> findAll(){
        return countryRepository.findAll();
    }

    public Country create(Country country){
        if (Objects.isNull(country.getName())) {
            throw new IllegalArgumentException("");
        }
        if (Objects.isNull(country.getContinent())) {
            throw new IllegalArgumentException("");
        }

        var continent = continentService.findById(country.getContinent().getId());
        country.setContinent(continent.orElseThrow());
        return countryRepository.save(country);
    }

    public Country update(Country country){
        if (Objects.isNull(country.getName())) {
            throw new IllegalArgumentException("");
        }
        if (Objects.isNull(country.getContinent())) {
            throw new IllegalArgumentException("");
        }

        var countryDb = countryRepository.findById(country.getId()).orElseThrow();
        countryDb.setName(country.getName());
        countryDb.setContinent(country.getContinent());
        return countryRepository.save(country);
    }

    public void delete(Country country){
        countryRepository.delete(country);
    }

    public void delete(int id){
        countryRepository.deleteById(id);
    }
}
