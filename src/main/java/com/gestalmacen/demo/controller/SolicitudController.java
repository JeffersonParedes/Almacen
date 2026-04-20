package com.gestalmacen.demo.controller;

import com.gestalmacen.demo.model.Solicitud;
import com.gestalmacen.demo.service.SolicitudService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/solicitudes")
public class SolicitudController {
    private final SolicitudService solicitudService;

    public SolicitudController(SolicitudService solicitudService) {
        this.solicitudService = solicitudService;
    }

    /**
     * URL: GET http://localhost:8080/api/solicitudes
     * Propósito: Ver el historial completo de movimientos (Kardex) de la bodega.
     */
    @GetMapping
    public List<Solicitud> listarHistorial(@RequestHeader("empresa-id") Long empresaId) {
        return solicitudService.listarHistorialPorEmpresa(empresaId);
    }

    /**
     * URL: POST http://localhost:8080/api/solicitudes/entrada
     * Propósito: Registrar que llega nueva mercadería y SUMAR stock.
     */
    @PostMapping("/entrada")
    public Solicitud registrarEntrada(@RequestHeader("empresa-id") Long empresaId, 
                                      @RequestBody Solicitud nuevaEntrada) {
        nuevaEntrada.setEmpresaId(empresaId);
        // Al ejecutar esto, el stock aumentará automáticamente
        return solicitudService.registrarEntrada(nuevaEntrada);
    }

    /**
     * URL: POST http://localhost:8080/api/solicitudes/salida
     * Propósito: Registrar una venta/merma y RESTAR stock.
     */
    @PostMapping("/salida")
    public Solicitud registrarSalida(@RequestHeader("empresa-id") Long empresaId, 
                                     @RequestBody Solicitud nuevaSalida) {
        nuevaSalida.setEmpresaId(empresaId);
        // Si no hay stock suficiente, esto lanzará un error 500 y no guardará nada
        return solicitudService.registrarSalida(nuevaSalida);
    }
}
  