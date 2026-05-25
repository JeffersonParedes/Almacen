package com.gestalmacen.demo.controller;
import com.gestalmacen.demo.dto.request.CategoriaRequestDTO;
import com.gestalmacen.demo.dto.response.CategoriaResponseDTO;
import com.gestalmacen.demo.service.CategoriaService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categorias")
public class CategoriaController {
 private final CategoriaService categoriaService;

    public CategoriaController(CategoriaService categoriaService) {
        this.categoriaService = categoriaService;
    }

    @PostMapping
    public ResponseEntity<CategoriaResponseDTO> crear(
            @RequestHeader("empresa-id") Long empresaId,
            @RequestBody CategoriaRequestDTO dto) {
        return new ResponseEntity<>(categoriaService.crearCategoria(dto, empresaId), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<CategoriaResponseDTO>> listar(
            @RequestHeader("empresa-id") Long empresaId) {
        return ResponseEntity.ok(categoriaService.listarPorEmpresa(empresaId));
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategoriaResponseDTO> obtener(
            @PathVariable Long id,
            @RequestHeader("empresa-id") Long empresaId) {
        return ResponseEntity.ok(categoriaService.obtenerPorId(id, empresaId));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CategoriaResponseDTO> actualizar(
            @PathVariable Long id,
            @RequestHeader("empresa-id") Long empresaId,
            @RequestBody CategoriaRequestDTO dto) {
        return ResponseEntity.ok(categoriaService.actualizarCategoria(id, empresaId, dto));
    }

    @PatchMapping("/{id}/estado")
    public ResponseEntity<Void> cambiarEstado(
            @PathVariable Long id,
            @RequestHeader("empresa-id") Long empresaId,
            @RequestParam String estado) {
        categoriaService.cambiarEstado(id, empresaId, estado);
        return ResponseEntity.noContent().build();
    }
}
    