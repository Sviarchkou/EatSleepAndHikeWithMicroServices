package com.example.app.repository;

import com.example.app.entity.RoutePoint;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface RoutePointRepository extends JpaRepository<RoutePoint, UUID> {
    List<RoutePoint> findAllByRouteId(UUID routeId);

    boolean existsByRouteIdAndSequenceNumber(UUID routeId, int sequenceNumber);
}
