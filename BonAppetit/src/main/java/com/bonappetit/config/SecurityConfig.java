package com.bonappetit.config;

import com.bonappetit.config.JWT.JWTAuthFilter;
import com.bonappetit.config.JWT.UserAuthProvider;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    private final UserAuthProvider userAuthProvider;

    public SecurityConfig(UserAuthProvider userAuthProvider) {
        this.userAuthProvider = userAuthProvider;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{


        String[] staticResources = {
                "/css/**",
                "/img/**",
                "/fonts/**",
                "/js/**",
                "/info/**"
        };
        http.csrf(AbstractHttpConfigurer::disable)
                .addFilterBefore(new JWTAuthFilter(userAuthProvider), BasicAuthenticationFilter.class)
                .sessionManagement(customizer -> customizer.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests()
                .requestMatchers(PathRequest.toStaticResources().atCommonLocations()).permitAll()
                .antMatchers(HttpMethod.POST, "/api/user/login", "/api/user/register").permitAll()
                .antMatchers("/api/recipes/**").permitAll()
                .antMatchers("/users/**").permitAll()
                .antMatchers("/").permitAll()
                .antMatchers(staticResources).permitAll()
                .anyRequest().authenticated();

        return http.build();
    }

}
