package org.education.network.security.auth.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class JwtDto {

    private String accessToken;
    private String refreshToken;

    @Override
    public String toString() {
        return "{" +
                "accessToken='" + accessToken + '\'' +
                ", refreshToken='" + refreshToken + '\'' +
                '}';
    }
}
