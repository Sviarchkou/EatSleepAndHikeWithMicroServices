package com.example.app.entity;


import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.UUID;

@Data
@Entity
@Table(name="route_points")
public class RoutePoint implements Comparable<RoutePoint>{

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name="id")
    private UUID id;

    // широта
    @Column(name="latitude", nullable = false)
    private double latitude;

    // долгота
    @Column(name="longitude", nullable = false)
    private double longitude;

    @Column(name="sequenceNumber", nullable = false)
    private int sequenceNumber;

    @Column(name="description", columnDefinition = "TEXT", nullable = true)
    private String description;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @ManyToOne
    @JoinColumn(name="route_id", nullable = false)
    private Route route;

    @Override
    public int compareTo(RoutePoint o) {
        return Integer.compare(this.getSequenceNumber(), o.getSequenceNumber());
    }
}
