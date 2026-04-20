package com.gestalmacen.demo.controller;

import com.gestalmacen.demo.model.Inventario;
import com.gestalmacen.demo.service.InventarioService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class InventarioControllerTest {
    @Mock
    private InventarioService inventarioService;

    @InjectMocks
    private InventarioController sut;

    // --- MÉTODO AUXILIAR PARA CREAR INVENTARIO ---
    private Inventario crearInventarioSeguro(Long id, Long empresaId, Long productoId, Long almacenId, Double stockActual, Double stockMinimo) {
        Inventario inventario = new Inventario();
        inventario.setId(id);
        inventario.setEmpresaId(empresaId);
        inventario.setProductoId(productoId);
        inventario.setAlmacenId(almacenId);
        inventario.setStockActual(stockActual);
        inventario.setStockMinimo(stockMinimo);
        return inventario;
    }

    /**
     * TEST 1: Consultar Stock Global
     * Verifica que el controlador devuelva la suma total de stock (un número Double)
     */
    @Test
    void whenConsultarStockGlobal_shouldReturnTotalStockAsDouble() {
        // Arrange
        Long empresaId = 1L;
        Long productoId = 1L; // ID del Arroz
        Double stockTotalEsperado = 150.5; // Supongamos que hay 150.5 kilos en total

        when(inventarioService.consultarStockGlobal(productoId, empresaId)).thenReturn(stockTotalEsperado);

        // Act
        Double result = sut.consultarStockGlobal(empresaId, productoId);

        // Assert
        assertEquals(stockTotalEsperado, result);
        verify(inventarioService, times(1)).consultarStockGlobal(productoId, empresaId);
    }

    /**
     * TEST 2: Consultar Stock Específico
     * Verifica que el controlador devuelva el objeto Inventario detallado de un almacén exacto
     */
    @Test
    void whenConsultarStockEspecifico_shouldReturnInventarioObject() {
        // Arrange
        Long empresaId = 1L;
        Long productoId = 1L;
        Long almacenId = 2L;
        
        Inventario inventarioEsperado = crearInventarioSeguro(1L, empresaId, productoId, almacenId, 50.0, 10.0);

        when(inventarioService.obtenerInventarioEspecifico(productoId, almacenId, empresaId)).thenReturn(inventarioEsperado);

        // Act
        Inventario result = sut.consultarStockEspecifico(empresaId, productoId, almacenId);

        // Assert
        assertEquals(inventarioEsperado, result);
        assertEquals(50.0, result.getStockActual());
        verify(inventarioService, times(1)).obtenerInventarioEspecifico(productoId, almacenId, empresaId);
    }
}  
