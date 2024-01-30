package org.education.network.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(value = "properties.jwt")
@Data
public class JwtProperties {

    private String secretKey;
    private long accessTokenValidity;
    private long refreshTokenValidity;
    private String tokenHeader;
    private String tokenPrefix;
}
