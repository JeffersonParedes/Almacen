package com.gestalmacen.demo.service;

import com.gestalmacen.demo.model.Producto;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class ProductoService {
    private List<Producto> productos;
    private Long contadorId = 1L;

    public ProductoService() {
        productos = new ArrayList<>();
        
        // Datos de prueba para la "Bodega Central" (empresaId = 1)
        // categoriaId = 1 (Abarrotes), 2 (Bebidas)
        productos.add(new Producto(contadorId++, 1L, 1L, "7751234567890", 
                                   "Arroz Costeño 1kg", "Arroz superior embolsado", 
                                   4.50, "url_imagen_arroz.jpg", "ACTIVO", 
                                   LocalDateTime.now(), LocalDateTime.now()));
                                   
        productos.add(new Producto(contadorId++, 1L, 2L, "7759876543210", 
                                   "Inka Kola 3L", "Gaseosa sabor original", 
                                   11.00, "url_imagen_inka.jpg", "ACTIVO", 
                                   LocalDateTime.now(), LocalDateTime.now()));
    }

    /**
     * 1. Registrar un nuevo producto en el catálogo
     */
    public Producto registrarProducto(Producto nuevoProducto) {
        nuevoProducto.setId(contadorId++);
        nuevoProducto.setEstado("ACTIVO");
        nuevoProducto.setCreatedAt(LocalDateTime.now());
        nuevoProducto.setUpdatedAt(LocalDateTime.now());
        
        productos.add(nuevoProducto);
        return nuevoProducto;
    }

    /**
     * 2. Listar productos (Aislamiento de Datos por Empresa)
     */
    public List<Producto> listarProductosPorEmpresa(Long empresaId) {
        List<Producto> listaFiltrada = new ArrayList<>();
        
        for (Producto p : productos) {
            if (p.getEmpresaId().equals(empresaId) && p.getEstado().equals("ACTIVO")) {
                listaFiltrada.add(p);
            }
        }
        return listaFiltrada;
    }

    /**
     * 3. Obtener el detalle de un producto específico
     */
    public Producto obtenerProducto(Long id, Long empresaId) {
        for (Producto p : productos) {
            if (p.getId().equals(id) && p.getEmpresaId().equals(empresaId)) {
                return p;
            }
        }
        return null;
    }

    /**
     * 4. Actualizar información del producto (Ej: Cambio de precio o nombre)
     */
    public Producto actualizarProducto(Long id, Long empresaId, Producto datosActualizados) {
        Producto productoExistente = obtenerProducto(id, empresaId);
        
        if (productoExistente != null) {
            productoExistente.setNombre(datosActualizados.getNombre());
            productoExistente.setDescripcion(datosActualizados.getDescripcion());
            productoExistente.setPrecio(datosActualizados.getPrecio());
            productoExistente.setCodigoBarras(datosActualizados.getCodigoBarras());
            productoExistente.setCategoriaId(datosActualizados.getCategoriaId());
            productoExistente.setImagenUrl(datosActualizados.getImagenUrl());
            productoExistente.setUpdatedAt(LocalDateTime.now());
        }
        
        return productoExistente;
    }

    /**
     * 5. Inactivar Producto (Borrado lógico)
     */
    public boolean inactivarProducto(Long id, Long empresaId) {
        Producto producto = obtenerProducto(id, empresaId);
        
        if (producto != null) {
            producto.setEstado("INACTIVO");
            producto.setUpdatedAt(LocalDateTime.now());
            return true;
        }
        return false;
    }
}  
