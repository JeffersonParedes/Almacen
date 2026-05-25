
package com.gestalmacen.demo.controller;

import com.gestalmacen.demo.entity.PapeleraProducto;
import com.gestalmacen.demo.service.PapeleraProductoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/papelera")
public class PapeleraProductoController {

    private final PapeleraProductoService papeleraService;

    public PapeleraProductoController(PapeleraProductoService papeleraService) {
        this.papeleraService = papeleraService;
    }

    @GetMapping
    public ResponseEntity<List<PapeleraProducto>> listar(
            @RequestHeader("empresa-id") Long empresaId) {
        return ResponseEntity.ok(papeleraService.listarPapelera(empresaId));
    }

    @GetMapping("/{id}")
    public ResponseEntity<PapeleraProducto> obtener(
            @PathVariable Long id,
            @RequestHeader("empresa-id") Long empresaId) {
        return ResponseEntity.ok(papeleraService.obtenerDetalle(id, empresaId));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarDefinitivo(
            @PathVariable Long id,
            @RequestHeader("empresa-id") Long empresaId) {
        papeleraService.eliminarDefinitivamente(id, empresaId);
        return ResponseEntity.noContent().build(); 
    }

    @DeleteMapping("/vaciar")
    public ResponseEntity<Void> vaciar(
            @RequestHeader("empresa-id") Long empresaId) {
        papeleraService.vaciarPapelera(empresaId);
        return ResponseEntity.noContent().build();
    }
}  
