package com.gestalmacen.demo.service;

import com.gestalmacen.demo.model.Producto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class ProductoServiceTest {
    private ProductoService sut;

    @BeforeEach
    void setUp() {
        // Inicializamos el servicio. Por defecto trae el Arroz (ID 1) y la Inka Kola (ID 2)
        sut = new ProductoService();
    }

    /**
     * TEST 1: Listar Productos (Catálogo)
     * Verifica que devuelva solo los productos de la empresa que estén activos.
     */
    @Test
    void whenListarProductos_shouldReturnActivosByEmpresa() {
        // Arrange
        Long empresaId = 1L;

        // Act
        List<Producto> result = sut.listarProductosPorEmpresa(empresaId);

        // Assert
        assertEquals(2, result.size());
        assertEquals("Arroz Costeño 1kg", result.get(0).getNombre());
    }

    /**
     * TEST 2: Registrar Producto
     */
    @Test
    void whenRegistrarProducto_shouldAssignIdAndStatus() {
        // Arrange
        Producto nuevo = new Producto();
        nuevo.setEmpresaId(1L);
        nuevo.setNombre("Aceite Primor");
        nuevo.setPrecio(8.50);

        // Act
        Producto result = sut.registrarProducto(nuevo);

        // Assert
        assertEquals(3L, result.getId()); // Hereda el ID 3
        assertEquals("ACTIVO", result.getEstado());
        assertEquals(3, sut.listarProductosPorEmpresa(1L).size());
    }

    /**
     * TEST 3: Obtener Producto Específico (Camino Feliz)
     */
    @Test
    void whenObtenerProducto_withValidId_shouldReturnProducto() {
        // Arrange
        Long idBuscado = 2L; // Inka Kola
        Long empresaId = 1L;

        // Act
        Producto result = sut.obtenerProducto(idBuscado, empresaId);

        // Assert
        assertNotNull(result);
        assertEquals("Inka Kola 3L", result.getNombre());
        assertEquals(11.00, result.getPrecio());
    }

    /**
     * TEST 4: Obtener Producto (Filtro de Seguridad / Hackeo)
     * La empresa 2 intenta ver un producto de la empresa 1.
     */
    @Test
    void whenObtenerProducto_withWrongEmpresa_shouldReturnNull() {
        // Arrange
        Long idBuscado = 1L; // Le pertenece a la empresa 1
        Long empresaEspia = 2L;

        // Act
        Producto result = sut.obtenerProducto(idBuscado, empresaEspia);

        // Assert
        assertNull(result); // El sistema lo bloquea y devuelve null
    }

    /**
     * TEST 5: Actualizar Producto
     */
    @Test
    void whenActualizarProducto_shouldModifyFields() {
        // Arrange
        Long id = 1L;
        Long empresaId = 1L;
        Producto datosNuevos = new Producto();
        datosNuevos.setNombre("Arroz Costeño 750g"); // Cambio de nombre
        datosNuevos.setPrecio(5.00); // Subió el precio
        
        // Act
        Producto result = sut.actualizarProducto(id, empresaId, datosNuevos);

        // Assert
        assertNotNull(result);
        assertEquals("Arroz Costeño 750g", result.getNombre());
        assertEquals(5.00, result.getPrecio());
    }

    /**
     * TEST 6: Inactivar Producto (Borrado Lógico)
     * Verifica que desaparezca de la lista de productos activos.
     */
    @Test
    void whenInactivarProducto_shouldReturnTrueAndHideFromCatalog() {
        // Arrange
        Long id = 2L; // Inka Kola
        Long empresaId = 1L;

        // Act: La inactivamos
        boolean exito = sut.inactivarProducto(id, empresaId);

        // Assert: Confirmamos la operación exitosa
        assertTrue(exito);

        // Verificamos que su estado interno ahora es "INACTIVO"
        Producto inactivado = sut.obtenerProducto(id, empresaId);
        assertEquals("INACTIVO", inactivado.getEstado());

        // La prueba de fuego: Al listar el catálogo, ya no debe salir (de 2 baja a 1)
        List<Producto> catalogo = sut.listarProductosPorEmpresa(empresaId);
        assertEquals(1, catalogo.size());
        assertEquals("Arroz Costeño 1kg", catalogo.get(0).getNombre()); // Solo quedó el arroz
    }
}
