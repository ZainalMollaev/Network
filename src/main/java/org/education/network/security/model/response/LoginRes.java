package org.education.network.security.model.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class LoginRes {
    private String email;
    private String accessToken;
    private String refreshToken;

    @Override
    public String toString() {
        return "{" +
                "email='" + email + '\'' +
                ", accessToken='" + accessToken + '\'' +
                ", refreshToken='" + refreshToken + '\'' +
                '}';
    }
}
