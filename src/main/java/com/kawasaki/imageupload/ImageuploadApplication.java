package com.kawasaki.imageupload;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@SpringBootApplication
@ConfigurationPropertiesScan
public class ImageuploadApplication {

    public static void main(String[] args) {
        SpringApplication.run(ImageuploadApplication.class, args);
    }

}
