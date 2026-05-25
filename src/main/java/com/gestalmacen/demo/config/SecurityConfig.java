package com.gestalmacen.demo.config;

import com.gestalmacen.demo.security.JwtRequestFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final JwtRequestFilter jwtRequestFilter;

    public SecurityConfig(JwtRequestFilter jwtRequestFilter) {
        this.jwtRequestFilter = jwtRequestFilter;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(authorizeRequests -> authorizeRequests
                        // Permite acceso sin autenticación al login y registro de empresa
                        .requestMatchers(HttpMethod.POST, "/api/usuarios/login").permitAll()
                        .requestMatchers(HttpMethod.POST, "/api/empresas").permitAll()

                        // Roles específicos
                        .requestMatchers("/api/empresas/**").hasRole("ADMINISTRADOR")
                        .requestMatchers("/api/usuarios/**").hasRole("ADMINISTRADOR")

                        .requestMatchers("/api/almacenes/**").hasAnyRole("ALMACENERO", "VENDEDOR", "ADMINISTRADOR")
                        .requestMatchers("/api/productos/**").hasAnyRole("ALMACENERO", "VENDEDOR", "ADMINISTRADOR")
                        .requestMatchers("/api/categorias/**").hasAnyRole("ALMACENERO", "VENDEDOR", "ADMINISTRADOR")
                        .requestMatchers("/api/inventarios/**").hasAnyRole("ALMACENERO", "VENDEDOR", "ADMINISTRADOR")
                        .requestMatchers("/api/solicitudes/**").hasAnyRole("ALMACENERO", "VENDEDOR", "ADMINISTRADOR")
                        .requestMatchers("/api/papelera/**").hasAnyRole("ALMACENERO", "VENDEDOR", "ADMINISTRADOR")

                        .anyRequest().authenticated())
                // Política de sesión sin estado (Stateless)
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        // Añadimos el filtro de JWT antes del filtro de autenticación de usuario y
        // contraseña
        http.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration)
            throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }
}
