package com.example.app.mapper;

import com.example.app.entity.Profile;
import com.example.app.web.dto.ProfileDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = UserMapper.class)
public interface ProfileMapper {
    @Mapping(source = "userDto", target = "user")
    Profile toEntity(ProfileDto profileDto);

    @Mapping(source = "user", target = "userDto")
    ProfileDto toDto(Profile profile);
}
