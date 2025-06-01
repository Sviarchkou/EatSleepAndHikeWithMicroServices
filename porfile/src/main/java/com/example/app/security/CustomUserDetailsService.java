package com.example.app.security;

import com.example.app.entity.Role;
import com.example.app.repository.UserRepository;
import com.example.app.service.UserService;
import jakarta.annotation.PostConstruct;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CustomUserDetailsService implements UserDetailsService {

    @Value("${app.default.user.login}")
    String defaultUsername;
    @Value("${app.default.user.password}")
    String defaultPassword;
    @Value("${app.default.user.role}")
    Role defaultRole;
    @Value("${app.default.user.email}")
    String defaultEmail;

    final UserRepository userRepository;
    final UserService userService;
    final PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        var user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Username not found"));
        return new User(user.getUsername(), user.getPassword(), mapRolesToAuthorities(List.of(user.getRole())));
    }

    private Collection<GrantedAuthority> mapRolesToAuthorities(List<Role> roles) {
        return roles.stream().map(role -> new SimpleGrantedAuthority(role.name())).collect(Collectors.toList());
    }

    @PostConstruct
    private void createDefaultUser() {
        if(userService.userAlreadyExists(defaultUsername)) {
            var userDb = userService.findByUsername(defaultUsername).orElseThrow();
            userDb.setPassword(passwordEncoder.encode(defaultPassword));
            userDb.setRole(defaultRole);
            userDb.setEmail(defaultEmail);
            userService.update(userDb);
            return;
        }
        var user = new com.example.app.entity.User();
        user.setUsername(defaultUsername);
        user.setEmail(defaultEmail);
        user.setPassword(passwordEncoder.encode(defaultPassword));
        user.setRole(defaultRole);

        userService.create(user);
    }
}
