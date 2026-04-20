package com.gestalmacen.demo.controller;

import com.gestalmacen.demo.model.PapeleraProducto;
import com.gestalmacen.demo.service.PapeleraProductoService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/papelera")
public class PapeleraProductoController {
    private final PapeleraProductoService papeleraService;

    public PapeleraProductoController(PapeleraProductoService papeleraService) {
        this.papeleraService = papeleraService;
    }

    /**
     * URL: GET http://localhost:8080/api/papelera
     * Propósito: Ver qué productos han sido eliminados en MI bodega.
     */
    @GetMapping
    public List<PapeleraProducto> verPapelera(@RequestHeader("empresa-id") Long empresaId) {
        return papeleraService.listarPapeleraPorEmpresa(empresaId);
    }

    /**
     * URL: POST http://localhost:8080/api/papelera/restaurar/1
     * Propósito: Sacar un producto de la papelera (para luego reactivarlo en el catálogo).
     */
    @PostMapping("/restaurar/{idPapelera}")
    public String restaurarProducto(@RequestHeader("empresa-id") Long empresaId, 
                                    @PathVariable Long idPapelera) {
        boolean exito = papeleraService.restaurarProducto(idPapelera, empresaId);
        if (exito) {
            return "Producto sacado de la papelera con éxito. (Falta reactivar en Catálogo)";
        }
        return "Error: No se pudo restaurar.";
    }

    /**
     * URL: DELETE http://localhost:8080/api/papelera/1
     * Propósito: Destruir el registro para siempre.
     */
    @DeleteMapping("/{idPapelera}")
    public String eliminarDefinitivamente(@RequestHeader("empresa-id") Long empresaId, 
                                          @PathVariable Long idPapelera) {
        boolean exito = papeleraService.eliminarDefinitivamente(idPapelera, empresaId);
        return exito ? "Eliminado para siempre." : "No se encontró el registro.";
    }

    /**
     * URL: DELETE http://localhost:8080/api/papelera/vaciar
     * Propósito: Limpiar toda la papelera de la empresa.
     */
    @DeleteMapping("/vaciar")
    public String vaciarMiPapelera(@RequestHeader("empresa-id") Long empresaId) {
        papeleraService.vaciarPapelera(empresaId);
        return "La papelera ha sido vaciada completamente.";
    }
}  
