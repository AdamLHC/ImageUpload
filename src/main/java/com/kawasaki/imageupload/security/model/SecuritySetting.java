package com.kawasaki.imageupload.security.model;

import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;

import java.util.List;

@ConfigurationProperties(prefix = "security")
@ConstructorBinding
@Getter
public class SecuritySetting {
    private final List<String> corsURLs;

    public SecuritySetting(List<String> corsURLs) {
        this.corsURLs = corsURLs;
    }
}
