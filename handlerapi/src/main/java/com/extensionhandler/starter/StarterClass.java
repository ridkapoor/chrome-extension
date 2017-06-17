package com.extensionhandler.starter;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * Created by ridkapoor on 6/17/17.
 */
@SpringBootApplication
@ComponentScan(basePackages = {"com.extensionhandler"})
public class StarterClass {

    public static void main(String[] args) throws Exception {
        SpringApplication.run(StarterClass.class, args);
    }
}
