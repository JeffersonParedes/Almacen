package com.gestalmacen.demo.controller;

import com.gestalmacen.demo.model.Inventario;
import com.gestalmacen.demo.service.InventarioService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/inventarios")
public class InventarioController {
    private final InventarioService inventarioService;

    public InventarioController(InventarioService inventarioService) {
        this.inventarioService = inventarioService;
    }

    /**
     * URL: GET http://localhost:8080/api/inventarios/global/1
     * Propósito: Saber cuánto stock total hay de un producto sumando todos los almacenes.
     * Ejemplo Postman: Si pones 1 (ID del Arroz), te sumará lo de la vitrina y el depósito.
     */
    @GetMapping("/global/{productoId}")
    public Double consultarStockGlobal(@RequestHeader("empresa-id") Long empresaId, 
                                       @PathVariable Long productoId) {
        return inventarioService.consultarStockGlobal(productoId, empresaId);
    }

    /**
     * URL: GET http://localhost:8080/api/inventarios/especifico?productoId=1&almacenId=2
     * Propósito: Saber exactamente cuánto stock hay de un producto en un almacén en concreto.
     */
    @GetMapping("/especifico")
    public Inventario consultarStockEspecifico(@RequestHeader("empresa-id") Long empresaId,
                                               @RequestParam Long productoId,
                                               @RequestParam Long almacenId) {
        return inventarioService.obtenerInventarioEspecifico(productoId, almacenId, empresaId);
    }
}  
