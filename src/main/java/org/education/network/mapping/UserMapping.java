package org.education.network.mapping;

import org.education.network.model.User;
import org.education.network.dto.bd.UserDto;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public interface UserMapping {

    UserDto userToUserDto(User user);

    User userDtoToUser(UserDto user);

}
