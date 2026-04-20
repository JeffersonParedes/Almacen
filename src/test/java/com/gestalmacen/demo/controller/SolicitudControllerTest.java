package com.gestalmacen.demo.controller;

import com.gestalmacen.demo.model.Solicitud;
import com.gestalmacen.demo.service.SolicitudService;
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
public class SolicitudControllerTest {
   @Mock
    private SolicitudService solicitudService;

    @InjectMocks
    private SolicitudController sut;

    // --- MÉTODO AUXILIAR PARA CREAR SOLICITUDES ---
    private Solicitud crearSolicitudSegura(Long id, Long empresaId, Long productoId, Long almacenId, String tipo, Double cantidad, String motivo) {
        Solicitud solicitud = new Solicitud();
        solicitud.setId(id);
        solicitud.setEmpresaId(empresaId);
        solicitud.setProductoId(productoId);
        solicitud.setAlmacenId(almacenId);
        solicitud.setTipoSolicitud(tipo);
        solicitud.setCantidad(cantidad);
        solicitud.setMotivo(motivo);
        return solicitud;
    }

    /**
     * TEST 1: Ver Historial (Kardex)
     */
    @Test
    void whenListarHistorial_shouldCallServiceAndReturnList() {
        // Arrange
        Long empresaId = 1L;
        List<Solicitud> expectedList = new ArrayList<>();
        expectedList.add(crearSolicitudSegura(1L, empresaId, 1L, 1L, "ENTRADA", 50.0, "Compra a proveedor"));
        expectedList.add(crearSolicitudSegura(2L, empresaId, 1L, 1L, "SALIDA", 10.0, "Venta al por menor"));

        when(solicitudService.listarHistorialPorEmpresa(empresaId)).thenReturn(expectedList);

        // Act
        List<Solicitud> result = sut.listarHistorial(empresaId);

        // Assert
        assertEquals(2, result.size());
        assertEquals("ENTRADA", result.get(0).getTipoSolicitud());
        verify(solicitudService, times(1)).listarHistorialPorEmpresa(empresaId);
    }

    /**
     * TEST 2: Registrar Entrada de Mercadería
     */
    @Test
    void whenRegistrarEntrada_shouldSetEmpresaIdAndReturnSavedSolicitud() {
        // Arrange
        Long empresaId = 1L;
        // Solicitud que viene del frontend SIN el ID de la empresa
        Solicitud nuevaEntrada = crearSolicitudSegura(null, null, 1L, 1L, "ENTRADA", 100.0, "Nuevo lote");
        Solicitud entradaGuardada = crearSolicitudSegura(1L, empresaId, 1L, 1L, "ENTRADA", 100.0, "Nuevo lote");

        when(solicitudService.registrarEntrada(nuevaEntrada)).thenReturn(entradaGuardada);

        // Act
        Solicitud result = sut.registrarEntrada(empresaId, nuevaEntrada);

        // Assert
        assertEquals(empresaId, nuevaEntrada.getEmpresaId()); // Verificamos inyección de seguridad
        assertEquals("ENTRADA", result.getTipoSolicitud());
        verify(solicitudService, times(1)).registrarEntrada(nuevaEntrada);
    }

    /**
     * TEST 3: Registrar Salida (Venta/Merma)
     */
    @Test
    void whenRegistrarSalida_shouldSetEmpresaIdAndReturnSavedSolicitud() {
        // Arrange
        Long empresaId = 1L;
        // Solicitud que viene del frontend
        Solicitud nuevaSalida = crearSolicitudSegura(null, null, 1L, 1L, "SALIDA", 25.0, "Venta a cliente");
        Solicitud salidaGuardada = crearSolicitudSegura(2L, empresaId, 1L, 1L, "SALIDA", 25.0, "Venta a cliente");

        when(solicitudService.registrarSalida(nuevaSalida)).thenReturn(salidaGuardada);

        // Act
        Solicitud result = sut.registrarSalida(empresaId, nuevaSalida);

        // Assert
        assertEquals(empresaId, nuevaSalida.getEmpresaId()); // Verificamos inyección de seguridad
        assertEquals(25.0, result.getCantidad());
        verify(solicitudService, times(1)).registrarSalida(nuevaSalida);
    } 
} 
