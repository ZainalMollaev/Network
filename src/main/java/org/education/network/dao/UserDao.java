package org.education.network.dao;

import lombok.RequiredArgsConstructor;
import org.education.network.model.User;
import org.education.network.model.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserDao {

    private final UserRepository userRepository;

    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public void updateRefreshToken(String username, String refreshToken) {
        userRepository.updateRefreshByEmail(username, refreshToken);
    }

    public String getRefreshTokenByEmail(String email) {
        return userRepository.findRefreshByEmail(email);
    }

}
