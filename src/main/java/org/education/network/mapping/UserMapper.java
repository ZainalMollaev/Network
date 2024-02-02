package org.education.network.mapping;

import org.education.network.model.User;
import org.education.network.dto.bd.UserDto;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public abstract class UserMapper {

    public abstract UserDto userToUserDto(User user);

    public abstract User userDtoToUser(UserDto user);

}
