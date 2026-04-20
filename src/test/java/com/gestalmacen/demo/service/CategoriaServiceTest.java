package com.gestalmacen.demo.service;

import com.gestalmacen.demo.model.Categoria;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class CategoriaServiceTest {
    // Probamos el servicio real, sin Mocks
    private CategoriaService sut;

    @BeforeEach
    void setUp() {
        // Reiniciamos la "base de datos" en memoria antes de cada test
        sut = new CategoriaService();
    }

    /**
     * TEST 1: Listar Categorías (Prueba de Aislamiento Multi-Tenant)
     * Verificamos que la Bodega 1 solo vea sus 3 categorías y la Bodega 2 solo vea la suya.
     */
    @Test
    void whenListarCategorias_shouldIsolateDataByEmpresaId() {
        // Act
        List<Categoria> categoriasBodega1 = sut.listarCategoriasPorEmpresa(1L);
        List<Categoria> categoriasBodega2 = sut.listarCategoriasPorEmpresa(2L);

        // Assert
        // La Bodega 1 debe tener Abarrotes, Bebidas y Limpieza (3)
        assertEquals(3, categoriasBodega1.size()); 
        
        // La Bodega 2 imaginaria solo debe tener Licores (1)
        assertEquals(1, categoriasBodega2.size());
        assertEquals("Licores", categoriasBodega2.get(0).getNombre());
    }

    /**
     * TEST 2: Registrar Categoría
     */
    @Test
    void whenRegistrarCategoria_shouldAssignIdAndStatus() {
        // Arrange
        Categoria nueva = new Categoria();
        nueva.setEmpresaId(1L);
        nueva.setNombre("Lácteos");
        nueva.setDescripcion("Leche, yogur, quesos");

        // Act
        Categoria result = sut.registrarCategoria(nueva);

        // Assert
        // Como el constructor ya creó 4 categorías (3 para emp1, 1 para emp2), el ID debe ser 5
        assertEquals(5L, result.getId()); 
        assertEquals("ACTIVO", result.getEstado());
        
        // Verificamos que ahora la Bodega 1 tiene 4 categorías en total
        assertEquals(4, sut.listarCategoriasPorEmpresa(1L).size());
    }

    /**
     * TEST 3: Obtener Categoría (Camino Feliz)
     */
    @Test
    void whenObtenerCategoria_shouldReturnCorrectData() {
        // Arrange
        Long idBuscado = 1L; // Abarrotes
        Long empresaId = 1L;

        // Act
        Categoria result = sut.obtenerCategoria(idBuscado, empresaId);

        // Assert
        assertNotNull(result);
        assertEquals("Abarrotes", result.getNombre());
    }

    /**
     * TEST 4: Obtener Categoría (Prueba de Seguridad / Hackeo)
     * ¿Qué pasa si la Bodega 2 intenta acceder al ID 1 que le pertenece a la Bodega 1?
     */
    @Test
    void whenObtenerCategoria_withWrongEmpresa_shouldReturnNull() {
        // Arrange
        Long idBuscado = 1L; // Este ID le pertenece a la empresa 1
        Long empresaEspia = 2L; // La empresa 2 intenta buscarlo

        // Act
        Categoria result = sut.obtenerCategoria(idBuscado, empresaEspia);

        // Assert
        // El sistema de seguridad del if (c.getEmpresaId().equals(empresaId)) debe bloquearlo
        assertNull(result); 
    }

    /**
     * TEST 5: Actualizar Categoría
     */
    @Test
    void whenActualizarCategoria_shouldModifyAllowedFields() {
        // Arrange
        Long id = 3L; // Limpieza
        Long empresaId = 1L;
        String nuevoNombre = "Limpieza y Aseo Personal";
        String nuevaDesc = "Detergentes y jabones de tocador";

        // Act
        Categoria result = sut.actualizarCategoria(id, empresaId, nuevoNombre, nuevaDesc);

        // Assert
        assertNotNull(result);
        assertEquals(nuevoNombre, result.getNombre());
        assertEquals(nuevaDesc, result.getDescripcion());
    }

    /**
     * TEST 6: Inactivar Categoría (Borrado Lógico)
     * Comprobamos que el método devuelve TRUE y que la categoría desaparece de la lista.
     */
    @Test
    void whenInactivarCategoria_shouldReturnTrueAndHideFromList() {
        // Arrange
        Long id = 2L; // Bebidas
        Long empresaId = 1L;

        // Act
        boolean exito = sut.inactivarCategoria(id, empresaId);

        // Assert
        assertTrue(exito); // El método debe devolver true

        // Verificamos que el estado interno cambió a INACTIVO
        Categoria inactiva = sut.obtenerCategoria(id, empresaId);
        assertEquals("INACTIVO", inactiva.getEstado());

        // Verificamos que al listar, ya no aparezca (De 3 bajan a 2)
        List<Categoria> listaRestante = sut.listarCategoriasPorEmpresa(empresaId);
        assertEquals(2, listaRestante.size());
    }
}
 