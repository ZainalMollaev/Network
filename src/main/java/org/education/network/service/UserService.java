package org.education.network.service;

import lombok.RequiredArgsConstructor;
import org.education.network.mapping.UserMapping;
import org.education.network.model.User;
import org.education.network.dto.UserDto;
import org.education.network.repository.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserMapping userMapping;
    private final BCryptPasswordEncoder passwordEncoder;

    public UserDto getUserByEmail(String email) {
        User user = userRepository.findByEmail(email);
        return userMapping.userToUserDto(user);
    }

    public Boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    public void saveUser(UserDto userDto) {
        userDto.setPassword(passwordEncoder.encode(userDto.getPassword()));
        User user = userMapping.userDtoToUser(userDto);
        userRepository.save(user);
    }

    public void updateRefreshToken(UserDto userDto) {
        userRepository.updateRefreshByEmail(userDto.getEmail(), userDto.getRefreshToken());
    }

    public String getRefreshTokenByEmail(UserDto userDto) {
        return userRepository.findRefreshByEmail(userDto.getEmail());
    }

}
