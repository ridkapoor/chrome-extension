package com.extensionhandler.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;

/**
 * Created by ridkapoor on 6/15/17.
 */
@Configuration
@PropertySource("classpath:HotelIdMapping.properties")
public class AppConfig {

    @Autowired
    private Environment env;

    public String getProperty(String prop) {
        return env.getProperty(prop);
    }
}
