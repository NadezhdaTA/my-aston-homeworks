package com.example.model.mapper;

import com.example.model.dto.UserRequest;
import com.example.model.dto.UserResponse;
import com.example.model.User;
import com.example.model.dto.UserUpdateRequest;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface UserMapper {
    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    User toUser(UserRequest userRequest);
    UserResponse toUserResponse(User user);
    User toUserFromUpdateRequest(UserUpdateRequest userUpdateRequest);
}
