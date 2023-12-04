package org.education.network.db.mapping;

import org.education.network.db.model.User;
import org.education.network.db.model.dto.UserDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapping {

    @Mapping(target = "email", source = "email")
    @Mapping(target = "password", source = "password")
    UserDto userToUserDto(User user);

    @Mapping(target = "email", source = "email")
    @Mapping(target = "password", source = "password")
    User userDtoToUser(UserDto user);

}
