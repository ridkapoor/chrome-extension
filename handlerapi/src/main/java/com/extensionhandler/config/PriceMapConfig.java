package com.extensionhandler.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;

/**
 * Created by ridkapoor on 6/20/17.
 */
@Configuration
@PropertySource("classpath:PriceMap.properties")
public class PriceMapConfig {

    @Autowired
    private Environment env;

    public Double getPrice(String prop) {
        Double price = null;
        final String property = env.getProperty(prop);
        if (property != null)
            price = Double.parseDouble(property);
        return price;
    }
}

