package com.gestalmacen.demo.controller;

import com.gestalmacen.demo.model.Empresa;
import com.gestalmacen.demo.service.EmpresaService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

// Le decimos a Spring Boot que esta clase recibirá peticiones web y devolverá JSON
@RestController
// Definimos la URL base para todos los métodos de este controlador
@RequestMapping("/api/empresas")
public class EmpresaController {
    // Inyectamos el servicio (nuestro "cocinero")
    private final EmpresaService empresaService;

    public EmpresaController(EmpresaService empresaService) {
        this.empresaService = empresaService;
    }

    /**
     * URL: GET http://localhost:8080/api/empresas
     * Propósito: Listar todas las empresas registradas
     */
    @GetMapping
    public List<Empresa> listarEmpresas() {
        return empresaService.listarTodasLasEmpresas();
    }

    /**
     * URL: GET http://localhost:8080/api/empresas/1
     * Propósito: Obtener los datos de una empresa específica por su ID
     */
    @GetMapping("/{id}")
    public Empresa obtenerEmpresa(@PathVariable Long id) {
        return empresaService.obtenerDatosEmpresa(id);
    }

    /**
     * URL: POST http://localhost:8080/api/empresas
     * Propósito: Registrar una nueva bodega en el sistema
     */
    @PostMapping
    public Empresa registrarEmpresa(@RequestBody Empresa nuevaEmpresa) {
        return empresaService.registrarNuevaEmpresa(nuevaEmpresa);
    }

    /**
     * URL: PUT http://localhost:8080/api/empresas/1/estado
     * Propósito: Suspender o reactivar una empresa
     */
    @PutMapping("/{id}/estado")
    public Empresa cambiarEstado(@PathVariable Long id, @RequestParam String nuevoEstado) {
        return empresaService.cambiarEstado(id, nuevoEstado);
    }
}
  