package org.education.network.service.dbService;

import org.education.network.dto.bd.UserDto;
import org.education.network.mapping.UserMapping;
import org.education.network.mapping.UserMappingImpl;
import org.education.network.model.User;
import org.education.network.model.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@Import(UserMappingImpl.class)
class UserServiceTest {
    @Spy
    UserMapping mapping = Mappers.getMapper(UserMapping.class);
    @Mock
    UserRepository userRepository;
    @InjectMocks
    UserService service;

    User user1;
    String email = "email";
    String refreshToken = "refreshToken123";
    String password = "pass123";

    @BeforeEach
    void init() {
        user1 = User.builder()
                .email(email)
                .refreshToken(refreshToken)
                .password(password)
                .build();
        userRepository.save(user1);
    }

    @Test
    void checkMappingTest() {

        when(userRepository.existsByEmail(""));

        when(userRepository.findByEmail(email))
                .thenReturn(user1);

        UserDto user = service.getUserByEmail(email);
        assertEquals(user.getEmail(), email);
        Mockito.verify(userRepository).findByEmail(email);
    }

    @Test
    void getUserByEmailTest() {
        when(userRepository.existsByEmail(email))
                .thenReturn(true);
        assertTrue(service.existsByEmail(email));
        Mockito.verify(userRepository).existsByEmail(email);
    }

}