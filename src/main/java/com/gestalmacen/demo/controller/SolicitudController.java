package com.gestalmacen.demo.controller;

import com.gestalmacen.demo.dto.request.SolicitudRequestDTO;
import com.gestalmacen.demo.dto.response.SolicitudResponseDTO;
import com.gestalmacen.demo.service.SolicitudService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/solicitudes")
public class SolicitudController {

    private final SolicitudService solicitudService;

    public SolicitudController(SolicitudService solicitudService) {
        this.solicitudService = solicitudService;
    }

    @PostMapping
    public ResponseEntity<SolicitudResponseDTO> registrar(
            @RequestHeader("empresa-id") Long empresaId,
            @RequestBody SolicitudRequestDTO dto) {
        return new ResponseEntity<>(solicitudService.registrarMovimiento(dto, empresaId), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<SolicitudResponseDTO>> listarTodo(
            @RequestHeader("empresa-id") Long empresaId) {
        return ResponseEntity.ok(solicitudService.listarHistorialEmpresa(empresaId));
    }

    @GetMapping("/almacen/{almacenId}")
    public ResponseEntity<List<SolicitudResponseDTO>> listarPorAlmacen(
            @PathVariable Long almacenId,
            @RequestHeader("empresa-id") Long empresaId) {
        return ResponseEntity.ok(solicitudService.listarHistorialPorAlmacen(empresaId, almacenId));
    }

    @GetMapping("/{id}")
    public ResponseEntity<SolicitudResponseDTO> obtenerDetalle(
            @PathVariable Long id,
            @RequestHeader("empresa-id") Long empresaId) {
        return ResponseEntity.ok(solicitudService.obtenerDetalleMovimiento(id, empresaId));
    }
} 