package com.example.app.web.controller;

import com.example.app.mapper.UserMapper;
import com.example.app.service.UserService;
import com.example.app.web.dto.UserDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController("user")
public class UserController {
    private final UserService userService;
    private final UserMapper userMapper;

    @GetMapping("{id}")
    public ResponseEntity<UserDto> getUser(@PathVariable Long id) {
        return ResponseEntity.ok(userMapper.toDto(userService.findById(id).orElseThrow()));
    }

    @PutMapping
    public ResponseEntity<UserDto> updateUser(@RequestBody UserDto userDto) {
        return ResponseEntity.ok(
                userMapper.toDto(userService.update(userMapper.toEntity(userDto))));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteUserById(@PathVariable Long id) {
        userService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteUser(@RequestBody UserDto userDto) {
        userService.delete(userMapper.toEntity(userDto));
        return ResponseEntity.noContent().build();
    }
}
