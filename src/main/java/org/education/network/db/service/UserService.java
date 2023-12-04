package org.education.network.db.service;

import lombok.RequiredArgsConstructor;
import org.education.network.db.mapping.UserMapping;
import org.education.network.db.model.User;
import org.education.network.db.model.dto.UserDto;
import org.education.network.db.repository.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserMapping userMapping;

    public UserDto getUserByEmail(String email) {
        User user = userRepository.findByEmail(email);
        UserDto userDto = userMapping.userToUserDto(user);

        return userDto;
    }

    public Boolean existsByEmail(String email) {

        return userRepository.existsByEmail(email);
    }

    public void saveUser(UserDto userDto) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        userDto.setPassword(passwordEncoder.encode(userDto.getPassword()));
        User user = userMapping.userDtoToUser(userDto);

        userRepository.save(user);
    }
}
