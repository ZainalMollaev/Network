package org.education.network.service;

import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class JsonServices {
    private final Gson gson;

    public JsonServices() {
        gson = new Gson();
    }

    public <T> T getObject(String json, Class clazz) {
        return (T) gson.fromJson(json, clazz);
    }
}
