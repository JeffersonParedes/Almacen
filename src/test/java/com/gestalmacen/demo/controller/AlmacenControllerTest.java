package com.gestalmacen.demo.controller;

import com.gestalmacen.demo.model.Almacen;
import com.gestalmacen.demo.service.AlmacenService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
 
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
@ExtendWith(MockitoExtension.class)
public class AlmacenControllerTest {   
    // 1. Simulamos el servicio (El "Mock" que pide tu PDF)
    @Mock
    AlmacenService almacenService;

    // 2. Nuestro Sistema Bajo Prueba (SUT)
    @InjectMocks
    AlmacenController sut;

    /**
     * TEST 1: Probar listar almacenes (GET)
     */
    @Test
    void whenListarAlmacenes_shouldCallServiceAndReturnTheCorrectList() {
        // Arrange (Preparar)
        Long empresaId = 1L;
        List<Almacen> expectedList = this.generateAlmacenList(2, empresaId);
        
        // Configuramos el Mock: "Cuando te pidan listar, devuelve esta lista ficticia"
        doReturn(expectedList).when(almacenService).listarAlmacenesPorEmpresa(empresaId);

        // Act (Ejecutar)
        List<Almacen> result = sut.listarAlmacenes(empresaId);

        // Assert (Verificar)
        verify(almacenService, times(1)).listarAlmacenesPorEmpresa(empresaId);
        assertEquals(expectedList.size(), result.size());
        assertEquals(expectedList, result);
    }

    /**
     * TEST 2: Probar registrar un nuevo almacén (POST)
     */
    @Test
    void whenRegistrarAlmacen_shouldSetEmpresaIdAndCallService() {
        // Arrange
        Long empresaId = 1L;
        Almacen nuevoAlmacen = new Almacen(null, null, "Nuevo Depósito", "Calle Falsa 123", null, null, null);
        Almacen expectedAlmacen = new Almacen(1L, empresaId, "Nuevo Depósito", "Calle Falsa 123", "ACTIVO", LocalDateTime.now(), LocalDateTime.now());
        
        doReturn(expectedAlmacen).when(almacenService).registrarAlmacen(nuevoAlmacen);

        // Act
        Almacen result = sut.registrarAlmacen(empresaId, nuevoAlmacen);

        // Assert
        verify(almacenService, times(1)).registrarAlmacen(nuevoAlmacen);
        assertEquals(empresaId, nuevoAlmacen.getEmpresaId()); // Verificamos que el controller asigne el ID de la empresa
        assertEquals(expectedAlmacen, result);
    }

    /**
     * TEST 3: Probar actualizar un almacén (PUT)
     */
    @Test
    void whenActualizarAlmacen_shouldCallServiceWithCorrectParameters() {
        // Arrange
        Long empresaId = 1L;
        Long almacenId = 1L;
        Almacen datosActualizados = new Almacen(null, null, "Nombre Corregido", "Dirección Corregida", null, null, null);
        Almacen expectedAlmacen = new Almacen(almacenId, empresaId, "Nombre Corregido", "Dirección Corregida", "ACTIVO", LocalDateTime.now(), LocalDateTime.now());

        doReturn(expectedAlmacen).when(almacenService).actualizarAlmacen(almacenId, empresaId, datosActualizados.getNombre(), datosActualizados.getDireccion());

        // Act
        Almacen result = sut.actualizarAlmacen(empresaId, almacenId, datosActualizados);

        // Assert
        verify(almacenService, times(1)).actualizarAlmacen(almacenId, empresaId, datosActualizados.getNombre(), datosActualizados.getDireccion());
        assertEquals(expectedAlmacen, result);
    }

    /**
     * TEST 4: Probar cambiar el estado de un almacén (PUT)
     */
    @Test
    void whenCambiarEstado_shouldCallServiceWithNewState() {
        // Arrange
        Long empresaId = 1L;
        Long almacenId = 1L;
        String nuevoEstado = "EN_MANTENIMIENTO";
        Almacen expectedAlmacen = new Almacen(almacenId, empresaId, "Depósito", "Av. Central", nuevoEstado, LocalDateTime.now(), LocalDateTime.now());

        doReturn(expectedAlmacen).when(almacenService).cambiarEstado(almacenId, empresaId, nuevoEstado);

        // Act
        Almacen result = sut.cambiarEstado(empresaId, almacenId, nuevoEstado);

        // Assert
        verify(almacenService, times(1)).cambiarEstado(almacenId, empresaId, nuevoEstado);
        assertEquals(expectedAlmacen, result);
        assertEquals(nuevoEstado, result.getEstado());
    }

    /**
     * MÉTODO AUXILIAR (Refactorización)
     * Crea una lista de almacenes ficticios para no repetir código, tal como pide el PDF.
     */
    private List<Almacen> generateAlmacenList(int wantedSize, Long empresaId) {
        List<Almacen> almacenes = new ArrayList<>();
        for (int i = 0; i < wantedSize; i++) {
            Almacen almacen = new Almacen(
                    (long) i,
                    empresaId,
                    "Almacén " + i,
                    "Dirección " + i,
                    "ACTIVO",
                    LocalDateTime.now(),
                    LocalDateTime.now()
            );
            almacenes.add(almacen);
        }
        return almacenes;
    }
       
}
