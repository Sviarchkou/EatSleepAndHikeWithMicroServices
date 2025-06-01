package com.example.app.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Data
@Entity
@Table(name="trips")
public class Trip {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name="name")
    private String name;


    @Column(name="description", columnDefinition = "TEXT", nullable = true)
    private String description;

    // days
    @Column(name="duration", nullable = false)
    private int duration;

    // km
    @Column(name="distance", nullable = false)
    private double distance;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @ManyToOne
    @JoinColumn(name="route_id", nullable = false)
    private Route route;

    @ManyToOne
    @JoinColumn(name="trip_type_id", nullable = false)
    private TripType tripType;

/*
    @ManyToOne()
    @JoinColumn(name="difficulty_category_id", nullable = false)
    private DifficultyCategory difficultyCategory;*/

/*    @ManyToMany
    @JoinTable(
            name="trip_type_difficulty_category_description_trips",
            joinColumns = @JoinColumn(name="trip_id"),
            inverseJoinColumns = @JoinColumn(name="trip_type_difficulty_category_description_id")
    )
    private Set<TripTypeDifCatDesc> tripTypeDifCatDescs = new HashSet<>();*/

    @Column(name="min_difficulty_category", nullable = false)
    @Enumerated(EnumType.STRING)
    private DifficultyCategory minDifficultyCategory;

    @Column(name="max_difficulty_category", nullable = false)
    @Enumerated(EnumType.STRING)
    private DifficultyCategory maxDifficultyCategory;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @OneToMany(mappedBy = "trip")
    private Set<Report> reports = new HashSet<>();

}
