package org.education.network.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@ConfigurationProperties("properties.minio")
@Data
public class MinioAppProperties {

    private String bucket;
    private String avatarLink;
    private String backImgLink;
    private String accessToken;
    private String secretToken;
    private String host;
    private boolean secure;
    private int port;
    private String jpegType;
    private List<String> buckets;
}
