package com.gestalmacen.demo.controller;

import com.gestalmacen.demo.model.Producto;
import com.gestalmacen.demo.service.ProductoService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ProductoControllerTest {
    @Mock
    private ProductoService productoService;

    @InjectMocks
    private ProductoController sut;

    // --- MÉTODO AUXILIAR PARA CREAR PRODUCTOS ---
    private Producto crearProductoSeguro(Long id, Long empresaId, Long categoriaId, String codigoBarras, String nombre, Double precio, String estado) {
        Producto producto = new Producto();
        producto.setId(id);
        producto.setEmpresaId(empresaId);
        producto.setCategoriaId(categoriaId);
        producto.setCodigoBarras(codigoBarras);
        producto.setNombre(nombre);
        producto.setPrecio(precio);
        producto.setEstado(estado);
        return producto;
    }

    /**
     * TEST 1: Listar Productos (Catálogo)
     */
    @Test
    void whenListarProductos_shouldCallServiceAndReturnList() {
        // Arrange
        Long empresaId = 1L;
        List<Producto> expectedList = new ArrayList<>();
        expectedList.add(crearProductoSeguro(1L, empresaId, 1L, "123456789", "Arroz Costeño", 3.50, "ACTIVO"));
        expectedList.add(crearProductoSeguro(2L, empresaId, 1L, "987654321", "Azúcar Rubia", 2.80, "ACTIVO"));

        when(productoService.listarProductosPorEmpresa(empresaId)).thenReturn(expectedList);

        // Act
        List<Producto> result = sut.listarProductos(empresaId);

        // Assert
        assertEquals(2, result.size());
        verify(productoService, times(1)).listarProductosPorEmpresa(empresaId);
    }

    /**
     * TEST 2: Obtener Detalle de Producto
     */
    @Test
    void whenObtenerProducto_shouldReturnProducto() {
        // Arrange
        Long empresaId = 1L;
        Long productoId = 1L;
        Producto expectedProducto = crearProductoSeguro(productoId, empresaId, 1L, "123456789", "Arroz Costeño", 3.50, "ACTIVO");

        when(productoService.obtenerProducto(productoId, empresaId)).thenReturn(expectedProducto);

        // Act
        Producto result = sut.obtenerProducto(empresaId, productoId);

        // Assert
        assertEquals("Arroz Costeño", result.getNombre());
        verify(productoService, times(1)).obtenerProducto(productoId, empresaId);
    }

    /**
     * TEST 3: Registrar Producto (Aislamiento Seguro)
     */
    @Test
    void whenRegistrarProducto_shouldSetEmpresaIdAndReturnSavedProducto() {
        // Arrange
        Long empresaId = 1L;
        // Simulamos un producto que el frontend envía SIN empresaId
        Producto nuevoProducto = crearProductoSeguro(null, null, 1L, "111222333", "Aceite Primor", 7.50, "ACTIVO");
        Producto productoGuardado = crearProductoSeguro(1L, empresaId, 1L, "111222333", "Aceite Primor", 7.50, "ACTIVO");

        when(productoService.registrarProducto(nuevoProducto)).thenReturn(productoGuardado);

        // Act
        Producto result = sut.registrarProducto(empresaId, nuevoProducto);

        // Assert
        // Verificamos que el controlador le inyectó el ID de la empresa para aislarlo correctamente
        assertEquals(empresaId, nuevoProducto.getEmpresaId());
        assertEquals(1L, result.getId());
        verify(productoService, times(1)).registrarProducto(nuevoProducto);
    }

    /**
     * TEST 4: Actualizar Producto
     */
    @Test
    void whenActualizarProducto_shouldCallServiceAndReturnUpdatedProducto() {
        // Arrange
        Long empresaId = 1L;
        Long productoId = 1L;
        Producto datosNuevos = crearProductoSeguro(null, null, 1L, "123456789", "Arroz Costeño 1KG", 4.00, "ACTIVO");
        Producto productoActualizado = crearProductoSeguro(productoId, empresaId, 1L, "123456789", "Arroz Costeño 1KG", 4.00, "ACTIVO");

        when(productoService.actualizarProducto(productoId, empresaId, datosNuevos)).thenReturn(productoActualizado);

        // Act
        Producto result = sut.actualizarProducto(empresaId, productoId, datosNuevos);

        // Assert
        assertEquals(4.00, result.getPrecio());
        assertEquals("Arroz Costeño 1KG", result.getNombre());
        verify(productoService, times(1)).actualizarProducto(productoId, empresaId, datosNuevos);
    }

    /**
     * TEST 5: Inactivar Producto - Caso Exitoso
     */
    @Test
    void whenInactivarProducto_withSuccess_shouldReturnSuccessMessage() {
        // Arrange
        Long empresaId = 1L;
        Long productoId = 1L;
        // Simulamos que el servicio logró encontrar y borrar el producto (devuelve true)
        when(productoService.inactivarProducto(productoId, empresaId)).thenReturn(true);

        // Act
        String result = sut.inactivarProducto(empresaId, productoId);

        // Assert
        assertEquals("Producto inactivado correctamente.", result);
        verify(productoService, times(1)).inactivarProducto(productoId, empresaId);
    }

    /**
     * TEST 6: Inactivar Producto - Caso Fallido (No encontrado)
     */
    @Test
    void whenInactivarProducto_withFailure_shouldReturnErrorMessage() {
        // Arrange
        Long empresaId = 1L;
        Long productoId = 99L; // Un ID que no existe
        // Simulamos que el servicio falló al encontrarlo (devuelve false)
        when(productoService.inactivarProducto(productoId, empresaId)).thenReturn(false);

        // Act
        String result = sut.inactivarProducto(empresaId, productoId);

        // Assert
        assertEquals("Error: Producto no encontrado.", result);
        verify(productoService, times(1)).inactivarProducto(productoId, empresaId);
    }   
}
