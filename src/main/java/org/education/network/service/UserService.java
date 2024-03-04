package org.education.network.service;

import lombok.RequiredArgsConstructor;
import org.education.network.dao.UserDao;
import org.education.network.dto.bd.UserDto;
import org.education.network.mapping.UserMapper;
import org.education.network.model.User;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserMapper userMapper;
    private final UserDao userDao;

    public UserDto getUserDtoByEmail(String email) {
        User user = userDao.getUserByEmail(email);
        return userMapper.userToUserDto(user);
    }

    public void updateRefreshToken(String username, String refreshToken) {
        userDao.updateRefreshToken(username, refreshToken);
    }

    public String getRefreshTokenByEmail(String email) {
        return userDao.getRefreshTokenByEmail(email);
    }

}
