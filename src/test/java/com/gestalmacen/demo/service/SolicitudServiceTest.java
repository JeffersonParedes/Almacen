package com.gestalmacen.demo.service;

import com.gestalmacen.demo.model.Solicitud;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)

public class SolicitudServiceTest {
    // Creamos un "Bodeguero de cartón" falso para aislar nuestra prueba
    @Mock
    private InventarioService inventarioService;

    // Inyectamos el mock dentro de nuestro servicio real (El Cajero)
    @InjectMocks
    private SolicitudService sut;

    /**
     * TEST 1: Listar Historial
     * Verifica que devuelva el dato por defecto cargado en el constructor.
     */
    @Test
    void whenListarHistorial_shouldReturnRecordsByEmpresa() {
        // Arrange
        Long empresaId = 1L;

        // Act
        List<Solicitud> result = sut.listarHistorialPorEmpresa(empresaId);

        // Assert
        assertEquals(1, result.size());
        assertEquals("ENTRADA", result.get(0).getTipoSolicitud());
        assertEquals("Llegada de camión repartidor", result.get(0).getMotivo());
    }

    /**
     * TEST 2: Registrar Entrada (Camino Feliz)
     * Verifica que asigne los datos, guarde en el historial y LLAME al Inventario.
     */
    @Test
    void whenRegistrarEntrada_shouldSaveAndCallSumarStock() {
        // Arrange
        Solicitud entrada = new Solicitud();
        entrada.setEmpresaId(1L);
        entrada.setProductoId(1L); // Arroz
        entrada.setAlmacenId(2L);  // Depósito
        entrada.setCantidad(50.0);
        entrada.setMotivo("Compra a proveedor");

        // Act
        Solicitud result = sut.registrarEntrada(entrada);

        // Assert
        assertEquals(2L, result.getId()); // Hereda el ID 2
        assertEquals("ENTRADA", result.getTipoSolicitud());
        
        // Verificamos que el historial creció de 1 a 2
        assertEquals(2, sut.listarHistorialPorEmpresa(1L).size());

        // LA PRUEBA DE ORO: Verificamos que el servicio intentó comunicarse con el Inventario
        verify(inventarioService, times(1)).sumarStock(1L, 2L, 1L, 50.0);
    }

    /**
     * TEST 3: Registrar Salida (Camino Feliz)
     */
    @Test
    void whenRegistrarSalida_shouldSaveAndCallRestarStock() {
        // Arrange
        Solicitud salida = new Solicitud();
        salida.setEmpresaId(1L);
        salida.setProductoId(1L);
        salida.setAlmacenId(1L);
        salida.setCantidad(10.0);
        salida.setMotivo("Venta mostrador");

        // Simulamos que el bodeguero dice "Sí hay stock, operación exitosa"
        when(inventarioService.restarStock(1L, 1L, 1L, 10.0)).thenReturn(true);

        // Act
        Solicitud result = sut.registrarSalida(salida);

        // Assert
        assertEquals(2L, result.getId());
        assertEquals("SALIDA", result.getTipoSolicitud());
        assertEquals(2, sut.listarHistorialPorEmpresa(1L).size());
        
        // Verificamos la comunicación
        verify(inventarioService, times(1)).restarStock(1L, 1L, 1L, 10.0);
    }

    /**
     * TEST 4: Registrar Salida sin Stock (Prueba Crítica de Seguridad)
     * Si no hay stock, el inventario lanza error. Verificamos que la solicitud NO se guarde.
     */
    @Test
    void whenRegistrarSalida_withNoStock_shouldThrowExceptionAndNotSaveRecord() {
        // Arrange
        Solicitud salidaFalsa = new Solicitud();
        salidaFalsa.setEmpresaId(1L);
        salidaFalsa.setProductoId(1L);
        salidaFalsa.setAlmacenId(1L);
        salidaFalsa.setCantidad(9999.0); // Cantidad absurda
        salidaFalsa.setMotivo("Intento de robo o error");

        // Simulamos que el bodeguero entra en pánico y lanza la excepción
        doThrow(new IllegalArgumentException("Error: Stock insuficiente en el almacén seleccionado."))
            .when(inventarioService).restarStock(1L, 1L, 1L, 9999.0);

        // Act & Assert
        IllegalArgumentException excepcion = assertThrows(
            IllegalArgumentException.class, 
            () -> sut.registrarSalida(salidaFalsa)
        );

        assertEquals("Error: Stock insuficiente en el almacén seleccionado.", excepcion.getMessage());

        // LA VERIFICACIÓN MÁS IMPORTANTE: 
        // Como hubo error, la línea "solicitudes.add(nuevaSalida)" nunca debió ejecutarse.
        // El historial debe seguir teniendo solo 1 elemento.
        assertEquals(1, sut.listarHistorialPorEmpresa(1L).size());
    }
} 
