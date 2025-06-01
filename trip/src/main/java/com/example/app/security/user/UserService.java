package com.example.app.security.user;

import com.example.app.security.user.User;
import com.example.app.security.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class UserService {
    private final UserRepository userRepository;

    public Optional<User> findById(Long id) {
        return userRepository.findById(id);
    }

    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public Boolean userAlreadyExists(String username) {
        return userRepository.existsByUsername(username);
    }

    public User create(User user){
        if(Objects.isNull(user.getEmail())){
            throw new IllegalArgumentException("");
        }
        if(Objects.isNull(user.getUsername())){
            throw new IllegalArgumentException("");
        }
        if(Objects.isNull(user.getRole())){
            throw new IllegalArgumentException("");
        }
        return userRepository.save(user);
    }

    public User update(User user){
        if(Objects.isNull(user.getId())){
            throw new IllegalArgumentException("");
        }
        if(Objects.isNull(user.getRole())){
            throw new IllegalArgumentException("");
        }
        if(Objects.isNull(user.getUsername())){
            throw new IllegalArgumentException("");
        }
        if(Objects.isNull(user.getEmail())){
            throw new IllegalArgumentException("");
        }
        var userDb = userRepository.findById(user.getId()).orElseThrow();
        userDb.setEmail(user.getEmail());
        userDb.setUsername(user.getUsername());
        userDb.setRole(user.getRole());
        return userRepository.save(user);
    }

    public void delete(Long id){
        userRepository.deleteById(id);
    }

    public void delete(User user){
        userRepository.delete(user);
    }

}
