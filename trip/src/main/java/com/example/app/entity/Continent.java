package com.example.app.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.HashSet;
import java.util.Set;


@Data
@Entity
@Table(name="continets")
public class Continent {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private int id;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @Column(name="name", nullable = false, length = 50)
    private String name;


    @OneToMany(mappedBy = "continent")
    private Set<Country> countries = new HashSet<>();

}
