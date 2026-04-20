package com.gestalmacen.demo.controller;

import com.gestalmacen.demo.model.Almacen;
import com.gestalmacen.demo.service.AlmacenService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/almacenes")
public class AlmacenController {
    private final AlmacenService almacenService;

    public AlmacenController(AlmacenService almacenService) {
        this.almacenService = almacenService;
    }

    /**
     * URL: GET http://localhost:8080/api/almacenes
     * Propósito: Ver todos los almacenes de MI bodega.
     */
    @GetMapping
    public List<Almacen> listarAlmacenes(@RequestHeader("empresa-id") Long empresaId) {
        return almacenService.listarAlmacenesPorEmpresa(empresaId);
    }

    /**
     * URL: POST http://localhost:8080/api/almacenes
     * Propósito: Registrar un nuevo lugar para guardar mercadería.
     */
    @PostMapping
    public Almacen registrarAlmacen(@RequestHeader("empresa-id") Long empresaId, 
                                    @RequestBody Almacen nuevoAlmacen) {
        nuevoAlmacen.setEmpresaId(empresaId);
        return almacenService.registrarAlmacen(nuevoAlmacen);
    }

    /**
     * URL: PUT http://localhost:8080/api/almacenes/1
     * Propósito: Actualizar el nombre o dirección del almacén.
     */
    @PutMapping("/{id}")
    public Almacen actualizarAlmacen(@RequestHeader("empresa-id") Long empresaId,
                                     @PathVariable Long id,
                                     @RequestBody Almacen datosActualizados) {
        return almacenService.actualizarAlmacen(id, empresaId, 
                                                datosActualizados.getNombre(), 
                                                datosActualizados.getDireccion());
    }

    /**
     * URL: PUT http://localhost:8080/api/almacenes/1/estado?nuevoEstado=EN_MANTENIMIENTO
     * Propósito: Cambiar el estado si el almacén no se puede usar temporalmente.
     */
    @PutMapping("/{id}/estado")
    public Almacen cambiarEstado(@RequestHeader("empresa-id") Long empresaId,
                                 @PathVariable Long id,
                                 @RequestParam String nuevoEstado) {
        return almacenService.cambiarEstado(id, empresaId, nuevoEstado);
    }
} 
