package com.gestalmacen.demo.service;

import com.gestalmacen.demo.model.Inventario;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class InventarioServiceTest {
    private InventarioService sut;

    @BeforeEach
    void setUp() {
        // Inicializa la memoria con el Arroz (Tienda=50, Depósito=200) y la Inka Kola (Tienda=24)
        sut = new InventarioService();
    }

    /**
     * TEST 1: Consultar Stock Específico
     */
    @Test
    void whenObtenerInventarioEspecifico_shouldReturnCorrectData() {
        // Arrange
        Long productoId = 1L; // Arroz
        Long almacenId = 2L;  // Depósito
        Long empresaId = 1L;

        // Act
        Inventario result = sut.obtenerInventarioEspecifico(productoId, almacenId, empresaId);

        // Assert
        assertNotNull(result);
        assertEquals(200.0, result.getStockActual());
    }

    /**
     * TEST 2: Consultar Stock Global (La suma matemática)
     * Debe sumar el Arroz de la Tienda (50) + el Arroz del Depósito (200)
     */
    @Test
    void whenConsultarStockGlobal_shouldReturnSumOfAllAlmacenes() {
        // Arrange
        Long productoId = 1L; // Arroz
        Long empresaId = 1L;

        // Act
        Double result = sut.consultarStockGlobal(productoId, empresaId);

        // Assert
        assertEquals(250.0, result); // 50 + 200 = 250
    }

    /**
     * TEST 3: Sumar Stock (Entrada de mercadería)
     */
    @Test
    void whenSumarStock_withExistingProduct_shouldIncreaseStock() {
        // Arrange
        Long productoId = 2L; // Inka Kola
        Long almacenId = 1L;  // Tienda (Tiene 24 botellas inicialmente)
        Long empresaId = 1L;
        Double cantidadIngresa = 12.0;

        // Act
        sut.sumarStock(productoId, almacenId, empresaId, cantidadIngresa);

        // Assert
        Inventario result = sut.obtenerInventarioEspecifico(productoId, almacenId, empresaId);
        assertEquals(36.0, result.getStockActual()); // 24 + 12 = 36
    }

    /**
     * TEST 4: Sumar Stock (Producto Nuevo en el Almacén)
     * Verificamos que si no existe, lo crea de cero.
     */
    @Test
    void whenSumarStock_withNewProduct_shouldCreateNewRecord() {
        // Arrange
        Long productoId = 99L; // Producto que no existe en inventario
        Long almacenId = 1L;
        Long empresaId = 1L;
        Double cantidadIngresa = 100.0;

        // Act
        sut.sumarStock(productoId, almacenId, empresaId, cantidadIngresa);

        // Assert
        Inventario result = sut.obtenerInventarioEspecifico(productoId, almacenId, empresaId);
        assertNotNull(result);
        assertEquals(100.0, result.getStockActual());
    }

    /**
     * TEST 5: Restar Stock (Camino Feliz - Venta normal)
     */
    @Test
    void whenRestarStock_withSufficientStock_shouldDecreaseStockAndReturnTrue() {
        // Arrange
        Long productoId = 1L; // Arroz
        Long almacenId = 1L;  // Tienda (Tiene 50 kg)
        Long empresaId = 1L;
        Double cantidadSale = 15.0;

        // Act
        boolean exito = sut.restarStock(productoId, almacenId, empresaId, cantidadSale);

        // Assert
        assertTrue(exito);
        Inventario result = sut.obtenerInventarioEspecifico(productoId, almacenId, empresaId);
        assertEquals(35.0, result.getStockActual()); // 50 - 15 = 35
    }

    /**
     * TEST 6: Restar Stock (Regla de Negocio: Stock Insuficiente)
     * Alguien intenta sacar 300 kilos de arroz de la tienda, pero solo hay 50.
     */
    @Test
    void whenRestarStock_withInsufficientStock_shouldThrowException() {
        // Arrange
        Long productoId = 1L; // Arroz
        Long almacenId = 1L;  // Tienda (Tiene 50 kg)
        Long empresaId = 1L;
        Double cantidadSale = 300.0; // ¡Mucho más del que existe!

        // Act & Assert
        IllegalArgumentException excepcion = assertThrows(
            IllegalArgumentException.class, 
            () -> sut.restarStock(productoId, almacenId, empresaId, cantidadSale)
        );

        assertEquals("Error: Stock insuficiente en el almacén seleccionado.", excepcion.getMessage());
        
        // Verificación extra: El stock original no debió ser tocado
        Inventario result = sut.obtenerInventarioEspecifico(productoId, almacenId, empresaId);
        assertEquals(50.0, result.getStockActual()); 
    }
} 
