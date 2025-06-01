package com.example.app.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Data
@Entity
@Table(name="profiles")
public class Profile {
    @Id
    @Column(name="id")
    private Long id;

    @OneToOne(cascade=CascadeType.ALL)
    @JoinColumn(name="user_id")
    private User user;

    @Column(name="date_of_birth")
    private Date dateOfBirth;

    @Column(name="profile_header")
    private String profileHeader;

    @Column(name="profile_description")
    private String profileDescription;

}
