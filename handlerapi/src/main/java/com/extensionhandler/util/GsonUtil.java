package com.extensionhandler.util;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import org.springframework.stereotype.Component;

/**
 * Created by ridkapoor on 5/8/17.
 */
@Component
public class GsonUtil {

    private final Gson gson = new Gson();

    /**
     * util class to wrap Gson Object for the complete application
     *
     * @param json
     * @param classType
     * @param <T>
     * @return
     */
    public <T> T fromJson(String json, Class<T> classType) throws JsonSyntaxException {
        return gson.fromJson(json, classType);
    }

    /**
     * @param object
     * @return
     */
    public String toJSon(Object object) {
        return gson.toJson(object);
    }
}
