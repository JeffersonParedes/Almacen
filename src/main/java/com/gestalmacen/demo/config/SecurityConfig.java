package com.gestalmacen.demo.config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            // 1. Apagamos la protección CSRF. 
            // Si no hacemos esto, Postman NUNCA podrá hacer peticiones POST (Crear, Actualizar).
            .csrf(AbstractHttpConfigurer::disable)
            
            // 2. Le decimos que cualquier petición que llegue, la deje pasar sin pedir login.
            .authorizeHttpRequests(auth -> auth
                .anyRequest().permitAll()
            );

        return http.build();
    } 
}
