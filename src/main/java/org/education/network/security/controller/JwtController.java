package org.education.network.security.controller;

import lombok.RequiredArgsConstructor;
import org.education.network.db.model.dto.UserDto;
import org.education.network.security.services.JwtControllerService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/network/jwt")
public class JwtController {

    private final JwtControllerService jcs;

    @PostMapping("/accessToken")
    public String getAccessToken(@RequestBody UserDto userDto){
        return jcs.updateAccess(userDto).toString();
    }

    @PostMapping ("/refreshToken")
    public String getRefreshToken(@RequestBody UserDto userDto){

        return jcs.updateRefresh(userDto).toString();

    }

}
