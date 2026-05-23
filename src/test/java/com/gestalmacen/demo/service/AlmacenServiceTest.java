package com.gestalmacen.demo.service;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.gestalmacen.demo.model.Almacen;
public class AlmacenServiceTest {
    // Aquí NO usamos @Mock ni @InjectMocks. Probamos el objeto real.
    private AlmacenService sut;

    @BeforeEach
    void setUp() {
        // Antes de CADA test, creamos un servicio nuevecito.
        // Esto garantiza que los almacenes que agregue un test no afecten a los demás.
        sut = new AlmacenService();
    }

    /**
     * TEST 1: Listar Almacenes (Verificando la data por defecto)
     */
    @Test
    void whenListarAlmacenes_shouldReturnOnlyActivosForEmpresa() {
        // Arrange
        Long empresaId = 1L;

        // Act
        List<Almacen> result = sut.listarAlmacenesPorEmpresa(empresaId);

        // Assert
        // Tu constructor ya crea 2 almacenes por defecto (Vitrina y Depósito)
        assertEquals(2, result.size()); 
        assertEquals("Tienda Principal (Vitrina)", result.get(0).getNombre());
    }

    /**
     * TEST 2: Registrar un nuevo Almacén
     */
    @Test
    void whenRegistrarAlmacen_shouldAssignIdAndStatus() {
        // Arrange
        Almacen nuevoAlmacen = new Almacen();
        nuevoAlmacen.setEmpresaId(1L);
        nuevoAlmacen.setNombre("Nuevo Depósito Chimbote");
        nuevoAlmacen.setDireccion("Av. Meiggs");

        // Act
        Almacen result = sut.registrarAlmacen(nuevoAlmacen);

        // Assert
        assertEquals(3L, result.getId()); // Como ya hay 2, este hereda el ID 3
        assertEquals("ACTIVO", result.getEstado());
        assertNotNull(result.getCreatedAt()); // Verificamos que se asignó la fecha
        
        // Comprobamos que ahora la lista total tiene 3 elementos
        assertEquals(3, sut.listarAlmacenesPorEmpresa(1L).size());
    }

    /**
     * TEST 3: Obtener un almacén específico
     */
    @Test
    void whenObtenerAlmacen_shouldReturnCorrectAlmacen() {
        // Arrange
        Long idBuscado = 2L;
        Long empresaId = 1L;

        // Act
        Almacen result = sut.obtenerAlmacen(idBuscado, empresaId);

        // Assert
        assertNotNull(result);
        assertEquals("Depósito Trasero", result.getNombre());
    }

    /**
     * TEST 4: Actualizar Almacén
     */
    @Test
    void whenActualizarAlmacen_shouldModifyFields() {
        // Arrange
        Long id = 1L;
        Long empresaId = 1L;
        String nuevoNombre = "Sede Principal Remodelada";
        String nuevaDireccion = "Av. Pacífico Mz. A Lt. 10";

        // Act
        Almacen result = sut.actualizarAlmacen(id, empresaId, nuevoNombre, nuevaDireccion);

        // Assert
        assertNotNull(result);
        assertEquals(nuevoNombre, result.getNombre());
        assertEquals(nuevaDireccion, result.getDireccion());
    }

    /**
     * TEST 5: Cambiar Estado (Prueba de Regla de Negocio Crítica)
     * Demostramos que si lo inactivamos, desaparece de la lista principal.
     */
    @Test
    void whenCambiarEstado_toInactivo_shouldNotBeListed() {
        // Arrange
        Long id = 2L; // Agarramos el "Depósito Trasero"
        Long empresaId = 1L;

        // Act: Lo mandamos a estado INACTIVO
        Almacen result = sut.cambiarEstado(id, empresaId, "INACTIVO");

        // Assert: Primero verificamos que el estado cambió
        assertNotNull(result);
        assertEquals("INACTIVO", result.getEstado());

        // Verificación estrella: Pedimos la lista y comprobamos el filtro
        List<Almacen> listaFiltrada = sut.listarAlmacenesPorEmpresa(empresaId);
        
        // De los 2 originales, como borramos 1, solo debe quedar 1.
        assertEquals(1, listaFiltrada.size()); 
        // Y el que queda obligatoriamente tiene que ser el ID 1
        assertEquals(1L, listaFiltrada.get(0).getId()); 
    }

}
