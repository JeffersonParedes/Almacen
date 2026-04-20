package com.gestalmacen.demo.controller;

import com.gestalmacen.demo.model.Producto;
import com.gestalmacen.demo.service.ProductoService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/productos")
public class ProductoController {
  private final ProductoService productoService;

    public ProductoController(ProductoService productoService) {
        this.productoService = productoService;
    }

    /**
     * URL: GET http://localhost:8080/api/productos
     * Propósito: Ver todo el catálogo de la bodega.
     */
    @GetMapping
    public List<Producto> listarProductos(@RequestHeader("empresa-id") Long empresaId) {
        return productoService.listarProductosPorEmpresa(empresaId);
    }

    /**
     * URL: GET http://localhost:8080/api/productos/1
     * Propósito: Ver el detalle de un solo producto.
     */
    @GetMapping("/{id}")
    public Producto obtenerProducto(@RequestHeader("empresa-id") Long empresaId, @PathVariable Long id) {
        return productoService.obtenerProducto(id, empresaId);
    }

    /**
     * URL: POST http://localhost:8080/api/productos
     * Propósito: Agregar un nuevo producto al catálogo.
     */
    @PostMapping
    public Producto registrarProducto(@RequestHeader("empresa-id") Long empresaId, 
                                      @RequestBody Producto nuevoProducto) {
        // Aseguramos el aislamiento de datos
        nuevoProducto.setEmpresaId(empresaId);
        return productoService.registrarProducto(nuevoProducto);
    }

    /**
     * URL: PUT http://localhost:8080/api/productos/1
     * Propósito: Actualizar el precio, código de barras, etc.
     */
    @PutMapping("/{id}")
    public Producto actualizarProducto(@RequestHeader("empresa-id") Long empresaId,
                                       @PathVariable Long id,
                                       @RequestBody Producto datosActualizados) {
        return productoService.actualizarProducto(id, empresaId, datosActualizados);
    }

    /**
     * URL: DELETE http://localhost:8080/api/productos/1
     * Propósito: Enviar el producto a la papelera (inactivarlo).
     */
    @DeleteMapping("/{id}")
    public String inactivarProducto(@RequestHeader("empresa-id") Long empresaId, @PathVariable Long id) {
        boolean exito = productoService.inactivarProducto(id, empresaId);
        if (exito) {
            // Nota para tu proyecto: Aquí en un futuro podrías llamar 
            // al PapeleraProductoService para registrar quién lo borró.
            return "Producto inactivado correctamente.";
        }
        return "Error: Producto no encontrado.";
    }  
}
  