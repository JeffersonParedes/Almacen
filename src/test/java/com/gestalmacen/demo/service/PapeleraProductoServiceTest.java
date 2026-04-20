package com.gestalmacen.demo.service;

import com.gestalmacen.demo.model.PapeleraProducto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class PapeleraProductoServiceTest {
    private PapeleraProductoService sut;

    @BeforeEach
    void setUp() {
        // Inicializa la memoria con 1 producto (Galletas Oreo) para la Empresa 1
        sut = new PapeleraProductoService();
    }

    /**
     * TEST 1: Listar Papelera (Aislamiento de Empresa)
     */
    @Test
    void whenListarPapelera_shouldReturnOnlyEmpresaRecords() {
        // Act
        List<PapeleraProducto> papeleraEmp1 = sut.listarPapeleraPorEmpresa(1L);
        List<PapeleraProducto> papeleraEmp2 = sut.listarPapeleraPorEmpresa(2L); // Empresa imaginaria

        // Assert
        assertEquals(1, papeleraEmp1.size());
        assertEquals("Galletas Oreo", papeleraEmp1.get(0).getNombre());
        
        // La empresa 2 no debería ver la basura de la empresa 1
        assertEquals(0, papeleraEmp2.size()); 
    }

    /**
     * TEST 2: Enviar a Papelera (Agregar registro)
     */
    @Test
    void whenEnviarAPapelera_shouldAssignIdAndAddToList() {
        // Arrange
        PapeleraProducto nuevoBorrador = new PapeleraProducto();
        nuevoBorrador.setEmpresaId(1L);
        nuevoBorrador.setNombre("Gaseosa Vencida");

        // Act
        PapeleraProducto result = sut.enviarAPapelera(nuevoBorrador);

        // Assert
        assertEquals(2L, result.getId()); // Hereda el ID 2
        assertNotNull(result.getFechaEliminacion());
        
        // Comprobamos que la papelera ahora tiene 2 items
        assertEquals(2, sut.listarPapeleraPorEmpresa(1L).size());
    }

    /**
     * TEST 3: Restaurar Producto (Camino Feliz)
     */
    @Test
    void whenRestaurarProducto_withValidId_shouldRemoveFromPapeleraAndReturnTrue() {
        // Arrange
        Long idPapelera = 1L; // ID de las Galletas Oreo
        Long empresaId = 1L;

        // Act
        boolean exito = sut.restaurarProducto(idPapelera, empresaId);

        // Assert
        assertTrue(exito);
        
        // Verificamos que la papelera quedó vacía tras sacarlo
        assertEquals(0, sut.listarPapeleraPorEmpresa(empresaId).size());
    }

    /**
     * TEST 4: Restaurar Producto (Hackeo / Error de ID)
     * ¿Qué pasa si intento restaurar algo que no es mío?
     */
    @Test
    void whenRestaurarProducto_withWrongEmpresa_shouldReturnFalse() {
        // Arrange
        Long idPapelera = 1L; 
        Long empresaEspia = 2L; // Empresa incorrecta

        // Act
        boolean exito = sut.restaurarProducto(idPapelera, empresaEspia);

        // Assert
        assertFalse(exito); // Debe bloquearlo
        
        // Verificamos que las galletas siguen en la papelera de la empresa 1
        assertEquals(1, sut.listarPapeleraPorEmpresa(1L).size());
    }

    /**
     * TEST 5: Eliminar Definitivamente (Camino Feliz)
     */
    @Test
    void whenEliminarDefinitivamente_shouldRemoveRecordAndReturnTrue() {
        // Arrange
        Long idPapelera = 1L;
        Long empresaId = 1L;

        // Act
        boolean exito = sut.eliminarDefinitivamente(idPapelera, empresaId);

        // Assert
        assertTrue(exito);
        assertEquals(0, sut.listarPapeleraPorEmpresa(empresaId).size());
    }

    /**
     * TEST 6: Vaciar Papelera Completamente (Prueba de removeIf masivo)
     */
    @Test
    void whenVaciarPapelera_shouldClearOnlyEmpresaRecords() {
        // Arrange: Vamos a meter basura de dos empresas distintas para probar el filtro masivo
        PapeleraProducto basuraEmp1 = new PapeleraProducto();
        basuraEmp1.setEmpresaId(1L);
        sut.enviarAPapelera(basuraEmp1); // Emp 1 ahora tiene 2 items (Oreo + este)

        PapeleraProducto basuraEmp2 = new PapeleraProducto();
        basuraEmp2.setEmpresaId(2L);
        sut.enviarAPapelera(basuraEmp2); // Emp 2 ahora tiene 1 item

        // Act: Vaciamos SOLO la papelera de la empresa 1
        sut.vaciarPapelera(1L);

        // Assert
        // La empresa 1 debe estar en 0
        assertEquals(0, sut.listarPapeleraPorEmpresa(1L).size()); 
        
        // La empresa 2 DEBE conservar su basura intacta (aislamiento multi-tenant)
        assertEquals(1, sut.listarPapeleraPorEmpresa(2L).size()); 
    }
}
