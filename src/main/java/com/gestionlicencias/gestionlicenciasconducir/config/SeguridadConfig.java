package com.gestionlicencias.gestionlicenciasconducir.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SeguridadConfig {

    // Bean para codificar contraseñas con BCrypt
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // Configuración de seguridad HTTP
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            // Permitir todas las solicitudes sin autenticación
            .authorizeHttpRequests(auth -> auth.anyRequest().permitAll())
            // Deshabilitar protección CSRF 
            .csrf().disable()
            // Deshabilitar formulario de login (sin autenticación)
            .formLogin().disable();

        return http.build();
    }
}


