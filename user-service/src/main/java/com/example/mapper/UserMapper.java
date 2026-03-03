package com.example.mapper;

import com.example.model.dto.UserRequest;
import com.example.model.dto.UserResponse;
import com.example.model.dto.UserUpdateRequest;
import com.example.model.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper {

    User toUser(UserRequest userRequest);
    UserResponse toUserResponse(User user);

    @Mapping(target = "id", source = "userId")
    User toUserFromUpdateRequest(UserUpdateRequest userUpdateRequest);
}
