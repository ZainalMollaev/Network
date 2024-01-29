package org.education.network.service;

import lombok.RequiredArgsConstructor;
import org.education.network.mapping.UserMapping;
import org.education.network.model.User;
import org.education.network.dto.bd.UserDto;
import org.education.network.model.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserMapping userMapping;

    public UserDto getUserByEmail(String email) {
        User user = userRepository.findByEmail(email);
        return userMapping.userToUserDto(user);
    }

    public Boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    public void updateRefreshToken(String subject, String refreshToken) {
        userRepository.updateRefreshByEmail(subject, refreshToken);
    }

    public String getRefreshTokenByEmail(String email) {
        return userRepository.findRefreshByEmail(email);
    }

}
