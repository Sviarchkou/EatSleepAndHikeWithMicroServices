package com.example.app.service;

import com.example.app.entity.Continent;
import com.example.app.repository.ContinentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class ContinentService {

    private final ContinentRepository continentRepository;

    public Optional<Continent> findById(int id){
        return continentRepository.findById(id);
    }

    public List<Continent> findAll(){
        return continentRepository.findAll();
    }

    public Continent create(Continent continent){
        if (Objects.isNull(continent.getName())){
            throw new IllegalArgumentException("");
        }
        return continentRepository.save(continent);
    }
}
