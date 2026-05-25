package com.gestalmacen.demo.controller;
import com.gestalmacen.demo.dto.request.InventarioRequestDTO;
import com.gestalmacen.demo.dto.response.InventarioResponseDTO;
import com.gestalmacen.demo.service.InventarioService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/inventarios")
public class InventarioController {
 private final InventarioService inventarioService;

    public InventarioController(InventarioService inventarioService) {
        this.inventarioService = inventarioService;
    }

    /**
     * POST / PUT: Guardar o modificar el stock
     */
    @PostMapping
    public ResponseEntity<InventarioResponseDTO> registrarOActualizar(
            @RequestHeader("empresa-id") Long empresaId,
            @RequestBody InventarioRequestDTO dto) {
        
        InventarioResponseDTO resultado = inventarioService.registrarOActualizarInventario(dto, empresaId);
        return new ResponseEntity<>(resultado, HttpStatus.OK);
    }

    /**
     * GET: /api/inventarios/producto/5/almacen/2
     * Útil para cuando estás en la pantalla de un almacén y pasas el lector de código de barras.
     */
    @GetMapping("/producto/{productoId}/almacen/{almacenId}")
    public ResponseEntity<InventarioResponseDTO> obtenerStockEspecifico(
            @PathVariable Long productoId,
            @PathVariable Long almacenId,
            @RequestHeader("empresa-id") Long empresaId) {
        
        return ResponseEntity.ok(inventarioService.consultarStockEnAlmacen(productoId, almacenId, empresaId));
    }

    /**
     * GET: /api/inventarios/producto/5
     * Útil para la pantalla principal del producto, para ver en qué locales tienes disponibilidad.
     */
    @GetMapping("/producto/{productoId}")
    public ResponseEntity<List<InventarioResponseDTO>> obtenerStockGlobal(
            @PathVariable Long productoId,
            @RequestHeader("empresa-id") Long empresaId) {
        
        return ResponseEntity.ok(inventarioService.consultarStockGlobal(productoId, empresaId));
    }
}  
 