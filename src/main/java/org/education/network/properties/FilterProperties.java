package org.education.network.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(value = "properties.filter")
@Data
public class FilterProperties {

    private String attributeName;
    private String loginUrl;

}
