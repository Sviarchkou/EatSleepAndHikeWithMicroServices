package com.example.app.web.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProfileDto {
    private Long id;
    private UserDto userDto;
    private Date dateOfBirth;
    private String profileHeader;
    private String profileDescription;
}
