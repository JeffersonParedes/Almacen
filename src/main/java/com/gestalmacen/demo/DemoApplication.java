package com.gestalmacen.demo;

import com.gestalmacen.demo.repository.UsuarioRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class DemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }

    // Este pequeño "parche" se ejecuta automáticamente al darle Play al proyecto
    @Bean
    CommandLineRunner corregirContrasenas(UsuarioRepository usuarioRepo, PasswordEncoder passwordEncoder) {
        return args -> {
            // Buscamos a tu usuario y le aplicamos la encriptación matemática real
            usuarioRepo.findByUsuario("admin_empresa1").ifPresent(user -> {
                user.setContrasena(passwordEncoder.encode("propietario123"));
                usuarioRepo.save(user);
                System.out.println("✅ Contraseña de admin_empresa1 reparada y encriptada correctamente.");
            });
        };
    }
}  
  