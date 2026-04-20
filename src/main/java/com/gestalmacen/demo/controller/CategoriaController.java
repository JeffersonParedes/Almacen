package com.gestalmacen.demo.controller;

import com.gestalmacen.demo.model.Categoria;
import com.gestalmacen.demo.service.CategoriaService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categorias")
public class CategoriaController {
    private final CategoriaService categoriaService;

    public CategoriaController(CategoriaService categoriaService) {
        this.categoriaService = categoriaService;
    }

    /**
     * URL: GET http://localhost:8080/api/categorias
     * Propósito: Listar las categorías activas de la bodega que hace la consulta.
     */
    @GetMapping
    public List<Categoria> listarCategorias(@RequestHeader("empresa-id") Long empresaId) {
        return categoriaService.listarCategoriasPorEmpresa(empresaId);
    }

    /**
     * URL: POST http://localhost:8080/api/categorias
     * Propósito: Crear una nueva categoría (ej. "Lácteos").
     */
    @PostMapping
    public Categoria registrarCategoria(@RequestHeader("empresa-id") Long empresaId, 
                                        @RequestBody Categoria nuevaCategoria) {
        // Regla de arquitectura SaaS: Atamos el nuevo registro a la empresa del usuario
        nuevaCategoria.setEmpresaId(empresaId);
        return categoriaService.registrarCategoria(nuevaCategoria);
    }

    /**
     * URL: PUT http://localhost:8080/api/categorias/1
     * Propósito: Corregir un nombre o descripción.
     */
    @PutMapping("/{id}")
    public Categoria actualizarCategoria(@RequestHeader("empresa-id") Long empresaId,
                                         @PathVariable Long id,
                                         @RequestBody Categoria datosActualizados) {
        return categoriaService.actualizarCategoria(id, empresaId, 
                                                    datosActualizados.getNombre(), 
                                                    datosActualizados.getDescripcion());
    }

    /**
     * URL: DELETE http://localhost:8080/api/categorias/1
     * Propósito: Inactivar una categoría.
     */
    @DeleteMapping("/{id}")
    public String inactivarCategoria(@RequestHeader("empresa-id") Long empresaId, @PathVariable Long id) {
        boolean exito = categoriaService.inactivarCategoria(id, empresaId);
        if (exito) {
            return "Categoría inactivada correctamente.";
        }
        return "Error: Categoría no encontrada o no pertenece a su empresa.";
    }
}
  