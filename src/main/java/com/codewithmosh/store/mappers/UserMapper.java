package com.codewithmosh.store.mappers;

import com.codewithmosh.store.dtos.UserDto;
import com.codewithmosh.store.dtos.UserDtoRequest;
import com.codewithmosh.store.entities.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.springframework.web.bind.annotation.ModelAttribute;

@Mapper(componentModel = "spring")
public interface UserMapper {
  UserDto toDto(User user);
  User fromDto(UserDtoRequest userDtoRequest);
//  @Mapping(target = "id",ignore = true)
  User update(@MappingTarget User user,  UserDtoRequest request);
}
