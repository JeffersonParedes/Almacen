package com.gestalmacen.demo.controller;

import com.gestalmacen.demo.dto.request.ProductoRequestDTO;
import com.gestalmacen.demo.dto.response.ProductoResponseDTO;
import com.gestalmacen.demo.service.ProductoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/productos")
public class ProductoController {

    private final ProductoService productoService;

    public ProductoController(ProductoService productoService) {
        this.productoService = productoService;
    }

    @PostMapping
    public ResponseEntity<ProductoResponseDTO> crear(
            @RequestHeader("empresa-id") Long empresaId,
            @RequestBody ProductoRequestDTO dto) {
        return new ResponseEntity<>(productoService.crearProducto(dto, empresaId), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<ProductoResponseDTO>> listarCatologo(
            @RequestHeader("empresa-id") Long empresaId) {
        return ResponseEntity.ok(productoService.listarCatologoPorEmpresa(empresaId));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductoResponseDTO> obtener(
            @PathVariable Long id,
            @RequestHeader("empresa-id") Long empresaId) {
        return ResponseEntity.ok(productoService.obtenerPorId(id, empresaId));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductoResponseDTO> actualizar(
            @PathVariable Long id,
            @RequestHeader("empresa-id") Long empresaId,
            @RequestBody ProductoRequestDTO dto) {
        return ResponseEntity.ok(productoService.actualizarProducto(id, empresaId, dto));
    }

    @PatchMapping("/{id}/estado")
    public ResponseEntity<Void> cambiarEstado(
            @PathVariable Long id,
            @RequestHeader("empresa-id") Long empresaId,
            @RequestParam String estado) {
        productoService.cambiarEstado(id, empresaId, estado);
        return ResponseEntity.noContent().build();
    }
}
  