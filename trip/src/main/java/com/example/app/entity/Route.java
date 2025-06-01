package com.example.app.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.*;

@Data
@Entity
@Table(name = "routes")
public class Route {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name="id")
    private UUID id;

    @Column(name="name", nullable = false)
    private String name;

    @Column(name="description", columnDefinition = "TEXT", nullable = true)
    private String description;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @OneToMany(mappedBy = "route")
    private List<RoutePoint> routePoints = new ArrayList<>();

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @ManyToMany
    @JoinTable(
            name="country_routes",
            joinColumns = @JoinColumn(name="route_id"),
            inverseJoinColumns = @JoinColumn(name="country_id")
    )
    private Set<Country> countries = new HashSet<>();

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @OneToMany(mappedBy = "route")
    private Set<Trip> trips = new HashSet<>();
}
