package org.education.network.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CommonResponse <T>{

    private boolean hasErrors;
    private T body;
    private String createdAt = Instant.now().toString();

    @Override
    public String toString() {
        return "{" +
                "\"hasErrors\" : \"" + hasErrors + "\"" +
                ", \"body\" : " + body +
                ", \"localDateTime\" : \"" + createdAt + "\" " +
                "}";
    }
}
