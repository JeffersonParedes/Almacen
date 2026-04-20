package com.gestalmacen.demo.controller;

import com.gestalmacen.demo.model.Empresa;
import com.gestalmacen.demo.service.EmpresaService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)   
public class EmpresaControllerTest {
 @Mock
    private EmpresaService empresaService; // El "Mock" del servicio

    @InjectMocks
    private EmpresaController sut; // System Under Test (El controlador)

    /**
     * TEST 1: Listar todas las empresas
     * Valida que el controlador devuelva la lista que el servicio le entrega.
     */
    @Test
    void whenListarEmpresas_shouldReturnList() {
        // Arrange: Preparamos los datos siguiendo el constructor de 9 parámetros
        List<Empresa> listaFicticia = new ArrayList<>();
        listaFicticia.add(new Empresa(
            1L, "20123456789", "Bodega Central Chimbote", 
            "Av. Pardo 123", "943000111", LocalDate.now(), 
            "ACTIVO", LocalDateTime.now(), LocalDateTime.now()
        ));

        // Configuramos el Mock
        when(empresaService.listarTodasLasEmpresas()).thenReturn(listaFicticia);  

        // Act: Ejecutamos el método del controlador
        List<Empresa> resultado = sut.listarEmpresas();

        // Assert: Verificamos
        assertEquals(1, resultado.size());
        assertEquals("Bodega Central Chimbote", resultado.get(0).getRazonSocial());
        verify(empresaService, times(1)).listarTodasLasEmpresas();  
    }

    /**
     * TEST 2: Obtener empresa por ID
     */
    @Test
    void whenObtenerEmpresa_shouldReturnEmpresa() {
        // Arrange
        Long idBusqueda = 1L;
        Empresa empresaFicticia = new Empresa(
            idBusqueda, "20123456789", "Minimarket Casuarinas", 
            "Nuevo Chimbote", "943888999", LocalDate.now(), 
            "ACTIVO", LocalDateTime.now(), LocalDateTime.now()
        );

        when(empresaService.obtenerDatosEmpresa(idBusqueda)).thenReturn(empresaFicticia);

        // Act
        Empresa resultado = sut.obtenerEmpresa(idBusqueda);

        // Assert
        assertEquals("20123456789", resultado.getRuc());
        verify(empresaService, times(1)).obtenerDatosEmpresa(idBusqueda);
    }

    /**
     * TEST 3: Registrar nueva empresa (POST)
     */
    @Test
    void whenRegistrarEmpresa_shouldReturnSavedEmpresa() {
        // Arrange
        Empresa entrada = new Empresa(); 
        entrada.setRuc("20987654321");
        entrada.setRazonSocial("Tienda de Prueba");

        Empresa salidaSimulada = new Empresa(
            2L, "20987654321", "Tienda de Prueba", 
            "Calle Lima", "912345678", LocalDate.now(), 
            "ACTIVO", LocalDateTime.now(), LocalDateTime.now()
        );

        when(empresaService.registrarNuevaEmpresa(entrada)).thenReturn(salidaSimulada);

        // Act
        Empresa resultado = sut.registrarEmpresa(entrada);

        // Assert
        assertEquals(2L, resultado.getId());
        assertEquals("ACTIVO", resultado.getEstado());
        verify(empresaService, times(1)).registrarNuevaEmpresa(entrada);
    }

    /**
     * TEST 4: Cambiar estado (PUT)
     */
    @Test
    void whenCambiarEstado_shouldReturnUpdatedEmpresa() {
        // Arrange
        Long id = 1L;
        String nuevoEstado = "SUSPENDIDO";
        Empresa empresaActualizada = new Empresa(
            id, "20123456789", "Bodega Central", 
            "Av. Pardo", "943000111", LocalDate.now(), 
            nuevoEstado, LocalDateTime.now(), LocalDateTime.now()
        );

        when(empresaService.cambiarEstado(id, nuevoEstado)).thenReturn(empresaActualizada);

        // Act
        Empresa resultado = sut.cambiarEstado(id, nuevoEstado);

        // Assert
        assertEquals("SUSPENDIDO", resultado.getEstado());
        verify(empresaService, times(1)).cambiarEstado(id, nuevoEstado);
    }
}   
