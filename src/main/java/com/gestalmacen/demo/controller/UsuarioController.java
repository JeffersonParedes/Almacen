package com.gestalmacen.demo.controller;

import com.gestalmacen.demo.dto.request.UsuarioRequestDTO;
import com.gestalmacen.demo.dto.response.UsuarioResponseDTO;
import com.gestalmacen.demo.service.UsuarioService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/usuarios")

public class UsuarioController {
   private final UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    // POST: /api/usuarios/login?usuario=admin&contrasena=1234
    @PostMapping("/login")
    public ResponseEntity<UsuarioResponseDTO> login(
            @RequestParam String usuario, 
            @RequestParam String contrasena) {
        
        UsuarioResponseDTO response = usuarioService.autenticarUsuario(usuario, contrasena);
        return ResponseEntity.ok(response);
    }

    // POST: /api/usuarios
    @PostMapping
    public ResponseEntity<UsuarioResponseDTO> registrarUsuario(
            @RequestHeader("empresa-id") Long empresaId,
            @RequestBody UsuarioRequestDTO dto) {
        
        UsuarioResponseDTO creado = usuarioService.registrarUsuario(dto, empresaId);
        return new ResponseEntity<>(creado, HttpStatus.CREATED);
    }

    // GET: /api/usuarios
    @GetMapping
    public ResponseEntity<List<UsuarioResponseDTO>> listarUsuarios(
            @RequestHeader("empresa-id") Long empresaId) {
        
        return ResponseEntity.ok(usuarioService.listarUsuariosPorEmpresa(empresaId));
    }

    // DELETE: /api/usuarios/1
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> desactivarUsuario(
            @PathVariable Long id,
            @RequestHeader("empresa-id") Long empresaId) {
        
        usuarioService.desactivarUsuario(id, empresaId);
        return ResponseEntity.noContent().build(); // Devuelve 204 No Content
    } 
}  
