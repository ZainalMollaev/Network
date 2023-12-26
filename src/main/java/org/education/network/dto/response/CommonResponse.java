package org.education.network.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;
import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CommonResponse<?> response = (CommonResponse<?>) o;
        return hasErrors == response.hasErrors && Objects.equals(body, response.body);
    }

    @Override
    public int hashCode() {
        return Objects.hash(hasErrors, body, createdAt);
    }
}
