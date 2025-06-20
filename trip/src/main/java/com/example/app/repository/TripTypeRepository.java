package com.example.app.repository;

import com.example.app.entity.TripType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TripTypeRepository extends JpaRepository<TripType, Integer> {
}
