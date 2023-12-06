package org.education.network.security.services;

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

    public Object getObject(String json, Class clazz) {
        return gson.fromJson(json, clazz);
    }

}
