package org.education.network.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class LoginRes {

    private String email;
    private String accessToken;
    private String refreshToken;

    @Override
    public String toString() {
        return "{" +
                " \"email\" : \"" + email + "\"" +
                ", \"accessToken\" : \"" + accessToken + "\"" +
                ", \"refreshToken\" : \"" + refreshToken + "\"" +
                "}";
    }
}
