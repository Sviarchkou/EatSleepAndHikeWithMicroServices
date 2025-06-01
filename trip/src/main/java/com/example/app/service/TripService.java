package com.example.app.service;

import com.example.app.entity.DifficultyCategory;
import com.example.app.entity.Route;
import com.example.app.entity.Trip;
import com.example.app.entity.TripType;
import com.example.app.repository.TripRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;

@RequiredArgsConstructor
@Service
public class TripService {

    private final TripRepository tripRepository;
    private final TripTypeService tripTypeService;
    private final RouteService routeService;

    public Optional<Trip> findById(UUID id) {
        return tripRepository.findById(id);
    }

    public List<Trip> findAll() {
        return tripRepository.findAll();
    }

    public List<Trip> findAllByRouteId(UUID routeId) {
        return tripRepository.findAllByRouteId(routeId);
    }

    public List<Trip> findAllByDifficultyOverlap(DifficultyCategory min, DifficultyCategory max){
        List<Trip> trips = tripRepository.findByDifficultyOverlap(min, max);
        trips.sort(Comparator.comparingInt(
                t -> -(Math.min(t.getMaxDifficultyCategory().ordinal(), max.ordinal())
                        - Math.max(t.getMinDifficultyCategory().ordinal(), min.ordinal()))
        ));
        return trips;
    }

    public List<Trip> findAllByTripType(TripType tripType){
        return tripRepository.findAllByTripType(tripType);
    }

    public List<Trip> findAllByTripTypeId(int id){
        return tripRepository.findAllByTripTypeId(id);
    }

    public List<Trip> findAllByDifficultyOverlapAndTripType(DifficultyCategory min, DifficultyCategory max, TripType tripType){
        List<Trip> trips = tripRepository.findByDifficultyOverlapAndHikeTypeOrdered(min, max, tripType);
        trips.sort(Comparator.comparingInt(
                t -> -(Math.min(t.getMaxDifficultyCategory().ordinal(), max.ordinal())
                        - Math.max(t.getMinDifficultyCategory().ordinal(), min.ordinal()))
        ));
        return trips;
    }

    public List<Trip> findAllByDifficultyOverlapAndTripTypeId(DifficultyCategory min, DifficultyCategory max, int tripId){
        List<Trip> trips = tripRepository.findByDifficultyOverlapAndHikeTypeIdOrdered(min, max, tripId);
        trips.sort(Comparator.comparingInt(
                t -> -(Math.min(t.getMaxDifficultyCategory().ordinal(), max.ordinal())
                        - Math.max(t.getMinDifficultyCategory().ordinal(), min.ordinal()))
        ));
        return trips;
    }

    /*public List<Trip> findTripByDifficultyOverlap(List<TripTypeDifCatDesc> tripTypeDifCatDesc) {
        return tripRepository.findTripByDifficultyOverlap(tripTypeDifCatDesc.stream()
                .map(TripTypeDifCatDesc::getDifficultyCategory).collect(Collectors.toList()));
    }

    public List<Trip> findHikesByDifficultyOverlapAndTripType(List<DifficultyCategories> inputCategories, List<Integer> tripTypeIds){

    }

    public List<Trip> findHikesByTripType(List<Integer> tripTypeIds){

    }
*/
    /*public List<Trip> findAllByDifficultyCategory(DifficultyCategory difficultyCategory) {
        return tripRepository.findAllByDifficultyCategory(difficultyCategory);
    }

    public List<Trip> findAllByTripType(TripType tripType) {
        return tripRepository.findAllByTripType(tripType);
    }

    public List<Trip> findAllByDifficultyCategoryAndTripType(DifficultyCategory difficultyCategory, TripType tripType) {
        return tripRepository.findAllByDifficultyCategoryAndTripType(difficultyCategory, tripType);
    }
*/
    public List<Trip> findAllByRoute(Route route) {
        return tripRepository.findAllByRoute(route);
    }

    public Trip create(Trip trip) {
        if (Objects.isNull(trip.getName())){
            throw new IllegalArgumentException("");
        }
        if (trip.getDistance() <= 0){
            throw new IllegalArgumentException("");
        }
        if (trip.getDuration() <= 0){
            throw new IllegalArgumentException("");
        }
        if (Objects.isNull(trip.getTripType())){
            throw new IllegalArgumentException("");
        }
        if (Objects.isNull(trip.getMinDifficultyCategory())){
            throw new IllegalArgumentException("");
        }
        if (Objects.isNull(trip.getMaxDifficultyCategory())){
            throw new IllegalArgumentException("");
        }
        if (Objects.isNull(trip.getRoute())){
            throw new IllegalArgumentException("");
        }
        /*if (Objects.isNull(trip.getDifficultyCategory())){
            throw new IllegalArgumentException("");
        }*/

        /*Set<TripTypeDifCatDesc> tripTypeDifCatDescs = new HashSet<>();
        for(TripTypeDifCatDesc tripTypeDifCatDesc : trip.getTripTypeDifCatDescs()){
            tripTypeDifCatDescs.add(tripTypeDifCatDescService.findById(tripTypeDifCatDesc.getId()).orElseThrow());
        }*/
        var tripType = tripTypeService.findById(trip.getTripType().getId()).orElseThrow();
//        var difficultyCategory = difficultyCategoryService.findById(trip.getDifficultyCategory().getId()).orElseThrow();
        var route = routeService.findById(trip.getRoute().getId()).orElseThrow();

        trip.setTripType(tripType);
//        trip.setDifficultyCategory(difficultyCategory);
        trip.setRoute(route);
        //trip.setTripTypeDifCatDescs(tripTypeDifCatDescs);
        return tripRepository.save(trip);
    }

    public Trip update(Trip trip) {
        if (Objects.isNull(trip.getName())){
            throw new IllegalArgumentException("");
        }
        if (trip.getDistance() <= 0){
            throw new IllegalArgumentException("");
        }
        if (trip.getDuration() <= 0){
            throw new IllegalArgumentException("");
        }

        if (Objects.isNull(trip.getTripType())){
            throw new IllegalArgumentException("");
        }
        if (Objects.isNull(trip.getMinDifficultyCategory())){
            throw new IllegalArgumentException("");
        }
        if (Objects.isNull(trip.getMaxDifficultyCategory())){
            throw new IllegalArgumentException("");
        }
        /*if (Objects.isNull(trip.getDifficultyCategory())){
            throw new IllegalArgumentException("");
        }*/
        if (Objects.isNull(trip.getRoute())){
            throw new IllegalArgumentException("");
        }
        var tripDb = tripRepository.findById(trip.getId()).orElseThrow();
        tripDb.setName(trip.getName());
        tripDb.setTripType(trip.getTripType());
        tripDb.setMinDifficultyCategory(trip.getMinDifficultyCategory());
        tripDb.setMaxDifficultyCategory(trip.getMaxDifficultyCategory());
//        tripDb.setDifficultyCategory(trip.getDifficultyCategory());
//        tripDb.setTripTypeDifCatDescs(trip.getTripTypeDifCatDescs());
        tripDb.setRoute(trip.getRoute());
        tripDb.setDistance(trip.getDistance());
        tripDb.setDuration(trip.getDuration());
        return tripRepository.save(trip);
    }

    public void delete(Trip trip) {
        tripRepository.delete(trip);
    }

    public void deleteById(UUID id) {
        tripRepository.deleteById(id);
    }
}
