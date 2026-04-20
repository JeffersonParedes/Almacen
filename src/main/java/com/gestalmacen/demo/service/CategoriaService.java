package com.gestalmacen.demo.service;

import com.gestalmacen.demo.model.Categoria;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class CategoriaService {
    // Simulamos la tabla de base de datos
    private List<Categoria> categorias;
    private Long contadorId = 1L;

    public CategoriaService() {
        categorias = new ArrayList<>();
        
        // Datos de prueba para la "Bodega Central" (empresaId = 1)
        categorias.add(new Categoria(contadorId++, 1L, "Abarrotes", "Arroz, azúcar, fideos, etc.", 
                                     "ACTIVO", LocalDateTime.now(), LocalDateTime.now()));
        categorias.add(new Categoria(contadorId++, 1L, "Bebidas", "Gaseosas, aguas, jugos", 
                                     "ACTIVO", LocalDateTime.now(), LocalDateTime.now()));
        categorias.add(new Categoria(contadorId++, 1L, "Limpieza", "Detergentes, lejía, jabones", 
                                     "ACTIVO", LocalDateTime.now(), LocalDateTime.now()));
                                     
        // Dato de prueba para una segunda bodega imaginaria (empresaId = 2)
        categorias.add(new Categoria(contadorId++, 2L, "Licores", "Cervezas y vinos", 
                                     "ACTIVO", LocalDateTime.now(), LocalDateTime.now()));
    }

    /**
     * 1. Registrar una nueva categoría
     */
    public Categoria registrarCategoria(Categoria nuevaCategoria) {
        nuevaCategoria.setId(contadorId++);
        nuevaCategoria.setEstado("ACTIVO");
        nuevaCategoria.setCreatedAt(LocalDateTime.now());
        nuevaCategoria.setUpdatedAt(LocalDateTime.now());
        
        categorias.add(nuevaCategoria);
        return nuevaCategoria;
    }

    /**
     * 2. Listar categorías (Aislamiento de Datos por Empresa)
     */
    public List<Categoria> listarCategoriasPorEmpresa(Long empresaId) {
        List<Categoria> listaFiltrada = new ArrayList<>();
        
        for (Categoria c : categorias) {
            // El filtro clave: Solo categorías de la empresa y que estén activas
            if (c.getEmpresaId().equals(empresaId) && c.getEstado().equals("ACTIVO")) {
                listaFiltrada.add(c);
            }
        }
        return listaFiltrada;
    }

    /**
     * 3. Obtener una categoría específica (validando que pertenezca a la empresa)
     */
    public Categoria obtenerCategoria(Long id, Long empresaId) {
        for (Categoria c : categorias) {
            if (c.getId().equals(id) && c.getEmpresaId().equals(empresaId)) {
                return c;
            }
        }
        return null;
    }

    /**
     * 4. Actualizar nombre o descripción
     */
    public Categoria actualizarCategoria(Long id, Long empresaId, String nuevoNombre, String nuevaDescripcion) {
        Categoria categoria = obtenerCategoria(id, empresaId);
        
        if (categoria != null) {
            categoria.setNombre(nuevoNombre);
            categoria.setDescripcion(nuevaDescripcion);
            categoria.setUpdatedAt(LocalDateTime.now());
        }
        
        return categoria;
    }

    /**
     * 5. Inactivar Categoría (Borrado lógico)
     */
    public boolean inactivarCategoria(Long id, Long empresaId) {
        Categoria categoria = obtenerCategoria(id, empresaId);
        
        if (categoria != null) {
            categoria.setEstado("INACTIVO");
            categoria.setUpdatedAt(LocalDateTime.now());
            return true;
        }
        return false;
    }  
}  
