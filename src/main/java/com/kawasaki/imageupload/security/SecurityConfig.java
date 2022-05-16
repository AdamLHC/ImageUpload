package com.kawasaki.imageupload.security;

import com.kawasaki.imageupload.security.model.SecuritySetting;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.Arrays;
import java.util.List;

// @Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final PasswordEncoder pwEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();

    @Autowired
    private DBUserDetailService userDetailService;

    @Autowired
    private SecuritySetting securitySetting;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailService);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .cors(Customizer.withDefaults())
                .authorizeRequests()
                .mvcMatchers(HttpMethod.GET,"/member").authenticated()
                .mvcMatchers(HttpMethod.PUT,"/member").authenticated()
                .mvcMatchers("/submission/owned").authenticated()
                .mvcMatchers("/submission/subscribed").authenticated()
                .mvcMatchers(HttpMethod.POST,"/submission/**").authenticated()
                .mvcMatchers("/subscribe").authenticated()
                .and().httpBasic()
                .and().csrf().disable(); //Disabling this because this app is a web api, and it interferes with post request.
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource(){
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowedOrigins(securitySetting.getCorsURLs());
        config.setAllowedMethods(Arrays.asList("GET","POST"));
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**",config); // Setting for whole site.
        return source;
    }
}
