package com.gestalmacen.demo.controller;
import com.gestalmacen.demo.dto.request.LoginRequestDTO;
import com.gestalmacen.demo.dto.response.LoginResponseDTO;
import com.gestalmacen.demo.security.JwtUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/usuarios")
public class AuthController {
    private final AuthenticationManager authenticationManager;
    private final UserDetailsService userDetailsService;
    private final JwtUtils jwtUtils;

    public AuthController(AuthenticationManager authenticationManager, 
                          UserDetailsService userDetailsService, 
                          JwtUtils jwtUtils) {
        this.authenticationManager = authenticationManager;
        this.userDetailsService = userDetailsService;
        this.jwtUtils = jwtUtils;
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@RequestBody LoginRequestDTO request) {
        // 1. Spring Security verifica las credenciales en MySQL
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getUsuario(), request.getContrasena())
        );

        // 2. Cargamos los datos del usuario (roles, etc.)
        final UserDetails userDetails = userDetailsService.loadUserByUsername(request.getUsuario());

        // 3. El JwtUtils genera el Token firmado
        final String jwt = jwtUtils.generateToken(userDetails);

        // 4. Lo devolvemos usando TU propio DTO de respuesta
        return ResponseEntity.ok(new LoginResponseDTO(jwt));
    }
}
  