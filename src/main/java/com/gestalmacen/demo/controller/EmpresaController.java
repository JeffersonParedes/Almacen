package com.gestalmacen.demo.controller;

import com.gestalmacen.demo.dto.request.EmpresaRequestDTO;
import com.gestalmacen.demo.dto.response.EmpresaResponseDTO;
import com.gestalmacen.demo.service.EmpresaService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/empresas")
public class EmpresaController {

    private final EmpresaService empresaService;

    public EmpresaController(EmpresaService empresaService) {
        this.empresaService = empresaService;
    }

    /**
     * POST: /api/empresas
     */
    @PostMapping
    public ResponseEntity<EmpresaResponseDTO> registrarEmpresa(@RequestBody EmpresaRequestDTO dto) {
        EmpresaResponseDTO creada = empresaService.registrarNuevaEmpresa(dto);
        return new ResponseEntity<>(creada, HttpStatus.CREATED);
    }

    /**
     * GET: /api/empresas/{id}
     */
    @GetMapping("/{id}")
    public ResponseEntity<EmpresaResponseDTO> obtenerEmpresa(@PathVariable Long id) {
        return ResponseEntity.ok(empresaService.obtenerDatosEmpresa(id));
    }

    /**
     * GET: /api/empresas
     */
    @GetMapping
    public ResponseEntity<List<EmpresaResponseDTO>> listarEmpresas() {
        return ResponseEntity.ok(empresaService.listarTodasLasEmpresas());
    }

    /**
     * PUT: /api/empresas/{id}
     */
    @PutMapping("/{id}")
    public ResponseEntity<EmpresaResponseDTO> actualizarDatos(
            @PathVariable Long id,
            @RequestParam String direccion,
            @RequestParam String telefono) {
        
        return ResponseEntity.ok(empresaService.actualizarDatosGenerales(id, direccion, telefono));
    }

    /**
     * PATCH: /api/empresas/{id}/estado
     */
    @PatchMapping("/{id}/estado")
    public ResponseEntity<EmpresaResponseDTO> cambiarEstadoEmpresa(
            @PathVariable Long id,
            @RequestParam String nuevoEstado) {
        
        return ResponseEntity.ok(empresaService.cambiarEstado(id, nuevoEstado));
    }
}  