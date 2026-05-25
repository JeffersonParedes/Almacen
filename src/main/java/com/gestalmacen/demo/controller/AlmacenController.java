package com.gestalmacen.demo.controller;
import com.gestalmacen.demo.dto.request.AlmacenRequestDTO;
import com.gestalmacen.demo.dto.response.AlmacenResponseDTO;
import com.gestalmacen.demo.service.AlmacenService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/almacenes")
public class AlmacenController {
   private final AlmacenService almacenService;

    public AlmacenController(AlmacenService almacenService) {
        this.almacenService = almacenService;
    }

    @PostMapping
    public ResponseEntity<AlmacenResponseDTO> crear(
            @RequestHeader("empresa-id") Long empresaId,
            @RequestBody AlmacenRequestDTO dto) {
        return new ResponseEntity<>(almacenService.crearAlmacen(dto, empresaId), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<AlmacenResponseDTO>> listar(
            @RequestHeader("empresa-id") Long empresaId) {
        return ResponseEntity.ok(almacenService.listarPorEmpresa(empresaId));
    }

    @GetMapping("/{id}")
    public ResponseEntity<AlmacenResponseDTO> obtener(
            @PathVariable Long id,
            @RequestHeader("empresa-id") Long empresaId) {
        return ResponseEntity.ok(almacenService.obtenerPorId(id, empresaId));
    }

    @PutMapping("/{id}")
    public ResponseEntity<AlmacenResponseDTO> actualizar(
            @PathVariable Long id,
            @RequestHeader("empresa-id") Long empresaId,
            @RequestBody AlmacenRequestDTO dto) {
        return ResponseEntity.ok(almacenService.actualizarAlmacen(id, empresaId, dto));
    }

    @PatchMapping("/{id}/estado")
    public ResponseEntity<Void> cambiarEstado(
            @PathVariable Long id,
            @RequestHeader("empresa-id") Long empresaId,
            @RequestParam String estado) {
        almacenService.cambiarEstado(id, empresaId, estado);
        return ResponseEntity.noContent().build();
    }  
} 
