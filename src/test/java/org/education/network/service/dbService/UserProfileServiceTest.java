package org.education.network.service.dbService;

import org.education.network.dto.bd.UserProfileDto;
import org.education.network.mapping.UserProfileMapper;
import org.education.network.model.profile.UserProfile;
import org.education.network.model.repository.UserProfileRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.InOrder;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.inOrder;

@ExtendWith(SpringExtension.class)
class UserProfileServiceTest {

    @Spy
    private BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
    @Spy
    private UserProfileMapper userProfileMapper = Mappers.getMapper(UserProfileMapper.class);
    @Mock
    private UserProfileRepository profileRepository;
    @InjectMocks
    private UserProfileService userProfileService;

    private UserProfileDto profileDto;

    @BeforeEach
    void init() {
        profileDto = UserProfileDto.builder()
                .email("johndoe@mail.ru")
                .password("pass")
                .name("john")
                .lastname("doe")
                .birthDate("2000-06-05")
                .gender(true)
                .phoneNumber("111-222")
                .build();
    }

    @Test
    void saveUserProfileTest() {

        userProfileService.saveUserProfile(profileDto);
        InOrder inOrder = inOrder(bCryptPasswordEncoder, userProfileMapper, profileRepository);
        inOrder.verify(bCryptPasswordEncoder).encode(anyString());
        inOrder.verify(userProfileMapper).toEntity(any());
        inOrder.verify(profileRepository).save(any());
    }

    @Test
    void saveUserProfileByEmaiTest() {
        Mockito.when(profileRepository.findByEmail(anyString())).thenReturn(new UserProfile());
        userProfileService.saveUserProfileByEmail(profileDto);
        InOrder inOrder = inOrder(userProfileMapper, profileRepository);
        inOrder.verify(profileRepository).findByEmail(anyString());
        inOrder.verify(userProfileMapper).partialUpdate(any(), any());
        inOrder.verify(profileRepository).save(any());
    }

    @Test
    void getUserProfileTest() {

        userProfileService.getUserProfile("mail");
        InOrder inOrder = inOrder(userProfileMapper, profileRepository);
        inOrder.verify(profileRepository).findByEmail(anyString());
        inOrder.verify(userProfileMapper).toDto(any());
    }
}
