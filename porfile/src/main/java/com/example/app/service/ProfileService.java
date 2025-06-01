package com.example.app.service;

import com.example.app.entity.Profile;
import com.example.app.repository.ProfileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class ProfileService {
    private final ProfileRepository profileRepository;
    private final UserService userService;

    public Optional<Profile> findById(long id) {
        return profileRepository.findById(id);
    }

    public Optional<Profile> findByUserId(String name) {
        return profileRepository.findByUserUsername(name);
    }

    public Optional<Profile> findByUserEmail(String name) {
        return profileRepository.findByUserUsername(name);
    }

    public Optional<Profile> findByUserUsername(String name) {
        return profileRepository.findByUserUsername(name);
    }

    public Profile create(Profile profile) {
        if(Objects.isNull(profile.getUser())) {
            throw new IllegalArgumentException("");
        }
        var user = userService.findById(profile.getUser().getId()).orElseThrow();
        profile.setUser(user);
        return profileRepository.save(profile);
    }

    public Profile update(Profile profile) {
        if(Objects.isNull(profile.getUser())) {
            throw new IllegalArgumentException("");
        }
        var profileDb = profileRepository.findById(profile.getId()).orElseThrow();

        profileDb.setUser(profile.getUser());
        profileDb.setDateOfBirth(profile.getDateOfBirth());
        profileDb.setProfileHeader(profile.getProfileHeader());
        profileDb.setProfileDescription(profile.getProfileDescription());
        return profileRepository.save(profileDb);
    }

    public void deleteById(Long id){
        profileRepository.deleteById(id);
    }

    public void delete(Profile profile){
        profileRepository.delete(profile);
    }
    
}
