package org.education.network.util;

import org.education.network.dto.response.CommonResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.Instant;

public class ResponseEntityUtil {

    public static ResponseEntity get(HttpStatus status, Object msg) {
        return ResponseEntity.status(status).body(
                CommonResponse.builder()
                        .hasErrors(false)
                        .body(msg)
                        .createdAt(String.valueOf(Instant.now()))
                .build());
    }

}
