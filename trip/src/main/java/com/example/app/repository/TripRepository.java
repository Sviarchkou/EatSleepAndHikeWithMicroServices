package com.example.app.repository;

import com.example.app.entity.DifficultyCategory;
import com.example.app.entity.Route;
import com.example.app.entity.Trip;
import com.example.app.entity.TripType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface TripRepository extends JpaRepository<Trip, UUID> {
    List<Trip> findAllByRouteId(UUID id);

    List<Trip> findAllByRoute(Route route);


    @Query("""
    SELECT t FROM Trip t
    WHERE t.tripType = :tripType
      AND t.minDifficultyCategory <= :max
      AND t.maxDifficultyCategory >= :min
    """)
    List<Trip> findByDifficultyOverlapAndHikeTypeOrdered(
            @Param("min") DifficultyCategory min,
            @Param("max") DifficultyCategory max,
            @Param("tripType") TripType tripType
    );

    @Query("""
    SELECT t FROM Trip t
    WHERE t.tripType.id = :tripTypeId
      AND t.minDifficultyCategory <= :max
      AND t.maxDifficultyCategory >= :min
    """)
    List<Trip> findByDifficultyOverlapAndHikeTypeIdOrdered(
            @Param("min") DifficultyCategory min,
            @Param("max") DifficultyCategory max,
            @Param("tripTypeId") int tripTypeId
    );


    @Query("""
    SELECT t FROM Trip t
    WHERE t.minDifficultyCategory <= :max
      AND t.maxDifficultyCategory >= :min
    """)
    List<Trip> findByDifficultyOverlap(
            @Param("min") DifficultyCategory min,
            @Param("max") DifficultyCategory max
    );

    List<Trip> findAllByTripTypeId(int id);

    List<Trip> findAllByTripType(TripType tripType);


}
