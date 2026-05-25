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

    /**
     * POST /api/usuarios
     * Crea un nuevo usuario (Vendedor o Almacenero) protegiendo su contraseña.
     */
    @PostMapping
    public ResponseEntity<UsuarioResponseDTO> crearUsuario(
            @RequestHeader("empresa-id") Long empresaId,
            @RequestBody UsuarioRequestDTO dto) {
        
        UsuarioResponseDTO nuevoUsuario = usuarioService.registrarUsuario(dto, empresaId);
        return new ResponseEntity<>(nuevoUsuario, HttpStatus.CREATED);
    }

    /**
     * GET /api/usuarios
     * Lista a todo el personal de la empresa usando DTOs para no exponer contraseñas encriptadas.
     */
    @GetMapping
    public ResponseEntity<List<UsuarioResponseDTO>> listarUsuarios(
            @RequestHeader("empresa-id") Long empresaId) {
        
        return ResponseEntity.ok(usuarioService.listarUsuariosPorEmpresa(empresaId));
    }

    /**
     * PATCH /api/usuarios/{id}/estado?activo=true/false
     * Activa o desactiva a un empleado (Baja lógica).
     */
    @PatchMapping("/{id}/estado")
    public ResponseEntity<Void> cambiarEstado(
            @PathVariable Long id,
            @RequestHeader("empresa-id") Long empresaId,
            @RequestParam boolean activo) {
        
        usuarioService.cambiarEstadoActivo(id, empresaId, activo);
        return ResponseEntity.noContent().build();
    }
}  
