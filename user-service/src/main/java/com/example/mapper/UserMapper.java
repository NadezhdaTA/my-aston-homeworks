package com.example.mapper;

import com.example.model.dto.UserRequest;
import com.example.model.dto.UserResponse;
import com.example.model.entity.User;
import com.example.model.dto.UserUpdateRequest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface UserMapper {
    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    User toUser(UserRequest userRequest);
    UserResponse toUserResponse(User user);

    @Mapping(target = "id", source = "userId")
    User toUserFromUpdateRequest(UserUpdateRequest userUpdateRequest);
}
