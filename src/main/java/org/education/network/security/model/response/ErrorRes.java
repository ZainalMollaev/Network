package org.education.network.security.model.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ErrorRes {

    private String httpDescription;
    private String message;

    @Override
    public String toString() {
        return "{" +
                "httpDescription='" + httpDescription + '\'' +
                ", message='" + message + '\'' +
                '}';
    }
}
