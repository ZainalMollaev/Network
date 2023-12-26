package org.education.network.service.controllerService;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.education.network.dto.bd.UserProfileDto;
import org.education.network.dto.response.CommonResponse;
import org.education.network.service.dbService.UserProfileService;
import org.education.network.service.dbService.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.time.Instant;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;

@ExtendWith(MockitoExtension.class)
class LogSignServiceTest {

    @Mock
    private UserService userService;
    @Mock
    private UserProfileService profileService;
    @Spy
    private ObjectMapper mapper = new ObjectMapper();
    @InjectMocks
    private LogSignService service;

    @SneakyThrows
    @Test
    void login() {
        String request = "{\"hasErrors\" : \"false\", \"body\" : { \"email\" : \"johndoe@mail.ru\", \"accessToken\" : \"eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJqb2huZG9lQG1haWwucnUiLCJleHAiOjY4ODc1MTc2ODl9.b3VTZ2_FEYbOXBuYeNbC4BawVZUSZLLL9WJQnfwG0-0\", \"refreshToken\" : \"eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJqb2huZG9lQG1haWwucnUiLCJleHAiOjc5NDYzNTE3Njg5fQ.7HwAzCI2_cWmWq78Q10xBeO9sk36HsOghK2l2egKx40\"}}";
        CommonResponse commonResponse = mapper.readValue(request, CommonResponse.class);
        ResponseEntity<CommonResponse> ok = ResponseEntity.ok(commonResponse);
        Mockito.when(mapper.readValue(request, CommonResponse.class)).thenReturn(ok.getBody());
        assertEquals(ok, service.login(request));
        assertThrows(RuntimeException.class, () -> service.login(""));
    }

    @Test
    void registerUser() {
        Mockito
                .when(userService.existsByEmail(anyString()))
                .thenReturn(true, false);

        assertEquals(CommonResponse.builder()
                .hasErrors(false)
                .body("Email is already exist!")
                .createdAt(Instant.now().toString())
                .build(), service.registerUser(UserProfileDto.builder().email("").build()).getBody());

        assertEquals(CommonResponse.builder()
                .hasErrors(false)
                .body("User is registered successfully!")
                .createdAt(Instant.now().toString())
                .build(), service.registerUser(UserProfileDto.builder().email("").build()).getBody());
    }
}