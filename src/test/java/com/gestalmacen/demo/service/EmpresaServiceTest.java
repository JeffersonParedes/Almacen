package com.gestalmacen.demo.service;

import com.gestalmacen.demo.model.Empresa;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class EmpresaServiceTest {
    // Probamos el servicio real, sin Mocks
    private EmpresaService sut;

    @BeforeEach
    void setUp() {
        // Inicializa la lista y crea la "Bodega Central" con ID 1 antes de cada prueba
        sut = new EmpresaService();
    }

    /**
     * TEST 1: Registrar Empresa (Camino Feliz)
     * Validamos que asigne ID consecutivo, la active y ponga las fechas.
     */
    @Test
    void whenRegistrarEmpresa_withValidRuc_shouldAssignIdAndSave() {
        // Arrange
        Empresa nuevaEmpresa = new Empresa();
        nuevaEmpresa.setRuc("10445566778"); // RUC válido de 11 dígitos
        nuevaEmpresa.setRazonSocial("Minimarket Las Gaviotas");

        // Act
        Empresa result = sut.registrarNuevaEmpresa(nuevaEmpresa);

        // Assert
        assertEquals(2L, result.getId()); // La Bodega Central ya ocupó el 1
        assertEquals("ACTIVO", result.getEstado());
        assertNotNull(result.getFechaSuscripcion());
        
        // Verificamos que ahora hay 2 empresas en total
        assertEquals(2, sut.listarTodasLasEmpresas().size());
    }

    /**
     * TEST 2: Registrar Empresa (Prueba de Regla de Negocio / Excepción)
     * Forzamos un error enviando un RUC corto para validar la barrera de seguridad.
     */
    @Test
    void whenRegistrarEmpresa_withInvalidRuc_shouldThrowException() {
        // Arrange
        Empresa empresaInvalida = new Empresa();
        empresaInvalida.setRuc("123"); // RUC Inválido

        // Act & Assert
        // assertThrows verifica que el código explote exactamente con el error que definimos
        IllegalArgumentException excepcion = assertThrows(
            IllegalArgumentException.class, 
            () -> sut.registrarNuevaEmpresa(empresaInvalida)
        );

        // Verificamos que el mensaje del error sea idéntico al de tu regla de negocio
        assertEquals("El RUC debe tener 11 dígitos.", excepcion.getMessage());
    }

    /**
     * TEST 3: Obtener Datos de una Empresa
     */
    @Test
    void whenObtenerDatosEmpresa_shouldReturnEmpresa() {
        // Act: Buscamos la empresa creada por defecto en tu constructor
        Empresa result = sut.obtenerDatosEmpresa(1L);

        // Assert
        assertNotNull(result);
        assertEquals("Bodega Central Nuevo Chimbote", result.getRazonSocial());
        assertEquals("20123456789", result.getRuc());
    }

    /**
     * TEST 4: Actualizar Datos Generales
     */
    @Test
    void whenActualizarDatos_shouldModifyOnlyAllowedFields() {
        // Arrange
        Long id = 1L;
        String nuevaDireccion = "Av. Country Mz. B";
        String nuevoTelefono = "943111222";

        // Act
        Empresa result = sut.actualizarDatosGenerales(id, nuevaDireccion, nuevoTelefono);

        // Assert
        assertEquals(nuevaDireccion, result.getDireccionPrincipal());
        assertEquals(nuevoTelefono, result.getTelefonoContacto());
        // Verificamos que el RUC no haya cambiado por error
        assertEquals("20123456789", result.getRuc()); 
    }

    /**
     * TEST 5: Cambiar Estado
     */
    @Test
    void whenCambiarEstado_shouldUpdateEstado() {
        // Arrange
        Long id = 1L;
        String nuevoEstado = "SUSPENDIDO POR FALTA DE PAGO";

        // Act
        Empresa result = sut.cambiarEstado(id, nuevoEstado);

        // Assert
        assertEquals(nuevoEstado, result.getEstado());
    }
}
 