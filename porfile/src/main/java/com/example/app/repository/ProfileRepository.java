package com.example.app.repository;

import com.example.app.entity.Profile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProfileRepository extends JpaRepository<Profile, Long> {
    public Profile findByUserId(Long id);
    public Optional<Profile> findByUserUsername(String username);
    public Optional<Profile> findByUserEmail(String email);

}
