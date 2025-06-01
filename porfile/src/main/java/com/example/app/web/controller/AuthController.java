package com.example.app.web.controller;

import com.example.app.entity.Role;
import com.example.app.entity.User;
import com.example.app.mapper.UserMapper;
import com.example.app.security.JWTGenerator;
import com.example.app.security.request.LoginRequest;
import com.example.app.security.request.RegisterRequest;
import com.example.app.security.response.AuthResponse;
import com.example.app.service.UserService;
import com.example.app.web.dto.UserDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Random;

@RequiredArgsConstructor
@RestController
public class AuthController {
    private final AuthenticationManager authenticationManager;
    private final UserService userService;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    private final JWTGenerator jwtGenerator;
    private final JavaMailSender mailSender;

    private final Map<String, Map<String, LocalDateTime>> emailConfirmations = new HashMap<>();

    @Value("${spring.mail.username}")
    private String emailUsername;

    @PostMapping("login")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest loginRequest){
        var user = userService.findByUsername(loginRequest.usernameOrEmail()).orElseGet(
                () -> userService.findByEmail(loginRequest.usernameOrEmail()).orElseThrow());

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        user.getUsername(),
                        loginRequest.password()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token = jwtGenerator.generateToken(authentication);
        return new ResponseEntity<>(new AuthResponse(token, user.getRole()), HttpStatus.OK);
    }

    @PostMapping("email-check")
    public ResponseEntity<Void> sendToEmail(@RequestBody RegisterRequest registerRequest) {
        if (userService.userAlreadyExists(registerRequest.username())) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        SimpleMailMessage simpleMail = new SimpleMailMessage();
        simpleMail.setFrom(emailUsername);
        simpleMail.setTo(registerRequest.email());
        simpleMail.setSubject("Eat, Sleep and Hike logging in");
        Random random = new Random();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 6; i++)
            sb.append(random.nextInt(0, 9));
        simpleMail.setText("That's your authentication key. Do not share it with anybody.\r\n" + sb.toString());
        try{
            mailSender.send(simpleMail);
        } catch(MailException me){
            System.out.println(me);
            return ResponseEntity.badRequest().build();
        }

        if (emailConfirmations.containsKey(registerRequest.email())){
            var map = emailConfirmations.get(registerRequest.email());
            map.clear();
            map.put(sb.toString(),LocalDateTime.now());
        }
        else{
            var map = new HashMap<String, LocalDateTime>();
            map.put(sb.toString(),LocalDateTime.now());
            emailConfirmations.put(registerRequest.email(), map);
        }

        return ResponseEntity.status(HttpStatus.MOVED_PERMANENTLY).build();
    }

    @PostMapping("register")
    public ResponseEntity<UserDto> register(@RequestBody RegisterRequest registerRequest) {
        if (userService.userAlreadyExists(registerRequest.username())) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        /*if (userService.userAlreadyExists(registerRequest.username())) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        SimpleMailMessage simpleMail = new SimpleMailMessage();
        simpleMail.setFrom(emailUsername);
        simpleMail.setTo(registerRequest.email());
        simpleMail.setSubject("Eat, Sleep and Hike logging in");
        Random random = new Random();
        StringBuilder sb = new StringBuilder();
        sb.append(random.nextInt(0, 9));
        simpleMail.setText("That's your authentication key. Do not share it with anybody.\r\n" + sb.toString());
*/
        var localDateTime = emailConfirmations.get(registerRequest.email()).get(registerRequest.confirmNum());
        if (Objects.isNull(localDateTime))
            throw new IllegalArgumentException("Confirmation number is incorrect");
        LocalDateTime limitTime = LocalDateTime.now().minusMinutes(2);
        if (limitTime.isAfter(localDateTime))
            throw new IllegalArgumentException("The waiting time is up. Repeat the operation");

        User user = new User();
        user.setUsername(registerRequest.username());
        user.setEmail(registerRequest.email());
        user.setPassword(passwordEncoder.encode((registerRequest.password())));
        user.setRole(Role.TOURIST);

        var userDb = userService.create(user);
        return new ResponseEntity<>(userMapper.toDto(userDb), HttpStatus.OK);
    }


    @GetMapping("user/{accessToken}")
    public ResponseEntity<UserDto> getUserByToken(@PathVariable String accessToken) {
        if(StringUtils.hasText(accessToken) && jwtGenerator.validateToken(accessToken)) {
            String username = jwtGenerator.getUsernameFromJWT(accessToken);
            return ResponseEntity.ok(userMapper.toDto(userService.findByUsername(username)
                    .orElseThrow(() -> new UsernameNotFoundException("User not found"))));
        }
        throw new UsernameNotFoundException("User not found");
    }
}
