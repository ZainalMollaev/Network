package org.education.network.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@ConfigurationProperties("properties.file")
@Data
public class FileProperties {

    private List<Compress> compresses;

    @Data
    public static class Compress {
        private String name;
        private int height;
        private int width;
    }

    public List<Compress> getProperCompress(String group) {
        return compresses
                .stream()
                .filter(i -> i.getName().contains(group))
                .toList();
    }
}
