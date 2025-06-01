package com.example.app.service;

import com.example.app.entity.RoutePoint;
import com.example.app.repository.RoutePointRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class RoutePointService {

    private final RoutePointRepository routePointRepository;
    private final RouteService routeService;

    public Optional<RoutePoint> findById(UUID id) {
        return routePointRepository.findById(id);
    }

    public List<RoutePoint> findAll() {
        List<RoutePoint> routePoints = routePointRepository.findAll();
        routePoints.sort(Comparator.comparingInt(RoutePoint::getSequenceNumber));
        return routePoints;
    }

    public List<RoutePoint> findAllByRouteId(UUID routeId) {
        List<RoutePoint> routePoints = routePointRepository.findAllByRouteId(routeId);;
        routePoints.sort(Comparator.comparingInt(RoutePoint::getSequenceNumber));
        return routePoints;
    }

    public RoutePoint create(RoutePoint routePoint) {
        if (routePoint.getLatitude() <= 0) {
            throw new IllegalArgumentException("");
        }
        if (routePoint.getLongitude() <= 0) {
            throw new IllegalArgumentException("");
        }
        if (routePoint.getSequenceNumber() <= 0) {
            throw new IllegalArgumentException("");
        }
        /*if (routePointRepository.existsByRouteIdAndSequenceNumber(
                routePoint.getRoute().getId(), routePoint.getSequenceNumber())) {
            throw new IllegalArgumentException("");
        }*/
        /*var route = routeRepository.findById(routePoint.getRoute().getId()).orElseThrow();
        List<RoutePoint> routePoints = routePointRepository.findAllByRouteId(routePoint.getRoute().getId());
        routePoints.forEach(r -> {
            if (r.getSequenceNumber()== routePoint.getSequenceNumber()) {
                throw new IllegalArgumentException("");
            }
        });*/
        var route = routeService.findById(routePoint.getRoute().getId()).orElseThrow();
        routePoint.setRoute(route);
        return routePointRepository.save(routePoint);
    }

    public List<RoutePoint> createAllWithSettingSN(List<RoutePoint> routePoints) {
        if (routePoints.isEmpty()) {
            throw new IllegalArgumentException("");
        }

        int sequenceNumber = 1;
        UUID routeId = routePoints.getFirst().getRoute().getId();
        for (RoutePoint routePoint : routePoints) {
            if (!routePoint.getRoute().getId().equals(routeId)) {
                throw new IllegalArgumentException("");
            }
            if (routePoint.getLatitude() <= 0) {
                throw new IllegalArgumentException("");
            }
            if (routePoint.getLongitude() <= 0) {
                throw new IllegalArgumentException("");
            }
            routePoint.setSequenceNumber(sequenceNumber++);
        }

        var route = routeService.findById(routePoints.getFirst().getRoute().getId()).orElseThrow();
        routePoints.forEach(routePoint -> {
            routePoint.setRoute(route);
        });
        return routePointRepository.saveAll(routePoints);
    }

    public List<RoutePoint> createAllCheckingSN(List<RoutePoint> routePoints) {
        if (routePoints.isEmpty()) {
            throw new IllegalArgumentException("");
        }

        int prevSequenceNumber = routePoints.getFirst().getSequenceNumber() - 1;
        UUID routeId = routePoints.getFirst().getRoute().getId();

        for (RoutePoint routePoint : routePoints) {
            if (!routePoint.getRoute().getId().equals(routeId))
                throw new IllegalArgumentException("");
            if (routePoint.getLatitude() <= 0)
                throw new IllegalArgumentException("");
            if (routePoint.getLongitude() <= 0)
                throw new IllegalArgumentException("");
            if (routePoint.getSequenceNumber() <= prevSequenceNumber)
                throw new IllegalArgumentException("");
            prevSequenceNumber = routePoint.getSequenceNumber();
        }

        var route = routeService.findById(routePoints.getFirst().getRoute().getId()).orElseThrow();
        routePoints.forEach(routePoint -> {
            routePoint.setRoute(route);
        });
        return routePointRepository.saveAll(routePoints);
    }

    public RoutePoint update(RoutePoint routePoint) {
        if (routePoint.getLatitude() <= 0) {
            throw new IllegalArgumentException("");
        }
        if (routePoint.getLongitude() <= 0) {
            throw new IllegalArgumentException("");
        }
        if (routePoint.getSequenceNumber() <= 0) {
            throw new IllegalArgumentException("");
        }
        /*if (routePointRepository.existsByRouteIdAndSequenceNumber(
                routePoint.getRoute().getId(), routePoint.getSequenceNumber())) {
            throw new IllegalArgumentException("");
        }*/
        var routePointDb = routePointRepository.findById(routePoint.getId()).orElseThrow();
        if (routePointDb.getSequenceNumber() != routePoint.getSequenceNumber()) {
            /*if (routePointRepository.existsByRouteIdAndSequenceNumber(
                    routePoint.getRoute().getId(), routePoint.getSequenceNumber())){
                throw new IllegalArgumentException("");
            }*/
            routePointDb.setSequenceNumber(routePoint.getSequenceNumber());
        }
        routePointDb.setLongitude(routePoint.getLongitude());
        routePointDb.setLatitude(routePoint.getLatitude());
        routePointDb.setDescription(routePoint.getDescription());
        return routePointRepository.save(routePoint);
    }

    public List<RoutePoint> updateAll(List<RoutePoint> routePoints) {
        if (routePoints.isEmpty()) {
            throw new IllegalArgumentException("");
        }

        UUID routeId = routePoints.getFirst().getRoute().getId();

        for (RoutePoint routePoint : routePoints) {
            if (routePoint.getLatitude() <= 0) {
                throw new IllegalArgumentException("");
            }
            if (routePoint.getLongitude() <= 0) {
                throw new IllegalArgumentException("");
            }
            if (routePoint.getSequenceNumber() <= 0) {
                throw new IllegalArgumentException("");
            }
            if (routePoint.getRoute().getId().equals(routeId))
                throw new IllegalArgumentException("");
        }
        var routePointDbs = routePointRepository.findAllById(
                routePoints.stream().map(RoutePoint::getId).toList());
        Set<Integer> sequenceNumbers = new HashSet<>();
        for (int i = 0; i < routePointDbs.size(); i++) {
            routePointDbs.get(i).setLongitude(routePoints.get(i).getLongitude());
            routePointDbs.get(i).setLatitude(routePoints.get(i).getLatitude());
            routePointDbs.get(i).setDescription(routePoints.get(i).getDescription());
            routePointDbs.get(i).setSequenceNumber(routePoints.get(i).getSequenceNumber());
            if (!sequenceNumbers.add(routePointDbs.get(i).getSequenceNumber())){
                throw new IllegalArgumentException("");
            }
        }
        /*routePointDbs = routePointDbs.stream().sorted(Comparator.comparingInt(RoutePoint::getSequenceNumber)).toList();
        for(int i = 1; i < routePointDbs.size(); i++){
            if (routePointDbs.get(i) == routePointDbs.get(i-1))
                throw new IllegalArgumentException("");
        }*/
        return routePointRepository.saveAll(routePointDbs);
    }

    @Transactional
    public List<RoutePoint> updateAll(List<RoutePoint> routePoints, UUID routeId) {
        if (routePoints.isEmpty()) {
            throw new IllegalArgumentException("");
        }
        for (RoutePoint routePoint : routePoints) {
            if (routePoint.getLatitude() <= 0) {
                throw new IllegalArgumentException("");
            }
            if (routePoint.getLongitude() <= 0) {
                throw new IllegalArgumentException("");
            }
            if (routePoint.getSequenceNumber() <= 0) {
                throw new IllegalArgumentException("");
            }
        }

        List<RoutePoint> toRetain = new ArrayList<>();
        for (RoutePoint rp: routePoints){
            if (Objects.isNull(rp.getId())){
                toRetain.add(create(rp));
            } else{
                toRetain.add(update(rp));
            }
        }
        var routePointDbs = new ArrayList<>(routePointRepository.findAllByRouteId(routeId));

        routePointDbs.removeAll(toRetain);
        routePointRepository.deleteAll(routePointDbs);
//        routePointDbs.sort(Comparator.comparingInt(RoutePoint::getSequenceNumber));
//        routePoints.sort(Comparator.comparingInt(RoutePoint::getSequenceNumber));
//        Set<Integer> sequenceNumbers = new HashSet<>();
        /*for (int i = 0; i < routePointDbs.size(); i++) {
            routePointDbs.get(i).setLongitude(routePoints.get(i).getLongitude());
            routePointDbs.get(i).setLatitude(routePoints.get(i).getLatitude());
            routePointDbs.get(i).setDescription(routePoints.get(i).getDescription());
            routePointDbs.get(i).setSequenceNumber(routePoints.get(i).getSequenceNumber());
            if (!sequenceNumbers.add(routePointDbs.get(i).getSequenceNumber())){
                throw new IllegalArgumentException("");
            }
            //if (routePoints.contains())
            toRetain.add(routePointDbs.get(i));
        }*/
        //routePointDbs.retainAll(toRetain);
        /*routePointDbs = routePointDbs.stream().sorted(Comparator.comparingInt(RoutePoint::getSequenceNumber)).toList();
        for(int i = 1; i < routePointDbs.size(); i++){
            if (routePointDbs.get(i) == routePointDbs.get(i-1))
                throw new IllegalArgumentException("");
        }*/
        return toRetain;//routePointRepository.saveAll(routePointDbs);
    }

    public void delete(RoutePoint routePoint) {
        routePointRepository.delete(routePoint);
    }

    public void deleteById(UUID id) {
        routePointRepository.deleteById(id);
    }

    public void deleteAll(List<RoutePoint> routePoints) {
        routePointRepository.deleteAll(routePoints);
    }
}
