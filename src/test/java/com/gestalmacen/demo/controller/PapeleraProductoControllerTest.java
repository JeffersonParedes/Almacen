package com.gestalmacen.demo.controller;

import com.gestalmacen.demo.model.PapeleraProducto;
import com.gestalmacen.demo.service.PapeleraProductoService;
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
public class PapeleraProductoControllerTest {
    @Mock
    private PapeleraProductoService papeleraService;

    @InjectMocks
    private PapeleraProductoController sut;

    // --- MÉTODO AUXILIAR PARA CREAR REGISTROS DE PAPELERA ---
    private PapeleraProducto crearRegistroPapeleraSeguro(Long id, Long empresaId, Long productoIdOriginal, String nombre) {
        PapeleraProducto papelera = new PapeleraProducto();
        papelera.setId(id);
        papelera.setEmpresaId(empresaId);
        papelera.setProductoIdOriginal(productoIdOriginal);
        papelera.setNombre(nombre);
        // Omitimos fecha y otros datos no críticos para la prueba del Controller
        return papelera;
    }

    /**
     * TEST 1: Ver Papelera (GET)
     */
    @Test
    void whenVerPapelera_shouldReturnList() {
        // Arrange
        Long empresaId = 1L;
        List<PapeleraProducto> expectedList = new ArrayList<>();
        expectedList.add(crearRegistroPapeleraSeguro(1L, empresaId, 15L, "Producto Viejo 1"));
        expectedList.add(crearRegistroPapeleraSeguro(2L, empresaId, 22L, "Producto Dañado 2"));

        when(papeleraService.listarPapeleraPorEmpresa(empresaId)).thenReturn(expectedList);

        // Act
        List<PapeleraProducto> result = sut.verPapelera(empresaId);

        // Assert
        assertEquals(2, result.size());
        assertEquals("Producto Viejo 1", result.get(0).getNombre());
        verify(papeleraService, times(1)).listarPapeleraPorEmpresa(empresaId);
    }

    /**
     * TEST 2: Restaurar Producto - Éxito
     */
    @Test
    void whenRestaurarProducto_success_shouldReturnSuccessMessage() {
        // Arrange
        Long empresaId = 1L;
        Long idPapelera = 1L;
        when(papeleraService.restaurarProducto(idPapelera, empresaId)).thenReturn(true);

        // Act
        String result = sut.restaurarProducto(empresaId, idPapelera);

        // Assert
        assertEquals("Producto sacado de la papelera con éxito. (Falta reactivar en Catálogo)", result);
        verify(papeleraService, times(1)).restaurarProducto(idPapelera, empresaId);
    }

    /**
     * TEST 3: Restaurar Producto - Fallo
     */
    @Test
    void whenRestaurarProducto_failure_shouldReturnErrorMessage() {
        // Arrange
        Long empresaId = 1L;
        Long idPapelera = 99L; // No existe
        when(papeleraService.restaurarProducto(idPapelera, empresaId)).thenReturn(false);

        // Act
        String result = sut.restaurarProducto(empresaId, idPapelera);

        // Assert
        assertEquals("Error: No se pudo restaurar.", result);
        verify(papeleraService, times(1)).restaurarProducto(idPapelera, empresaId);
    }

    /**
     * TEST 4: Eliminar Definitivamente - Éxito
     */
    @Test
    void whenEliminarDefinitivamente_success_shouldReturnSuccessMessage() {
        // Arrange
        Long empresaId = 1L;
        Long idPapelera = 1L;
        when(papeleraService.eliminarDefinitivamente(idPapelera, empresaId)).thenReturn(true);

        // Act
        String result = sut.eliminarDefinitivamente(empresaId, idPapelera);

        // Assert
        assertEquals("Eliminado para siempre.", result);
        verify(papeleraService, times(1)).eliminarDefinitivamente(idPapelera, empresaId);
    }

    /**
     * TEST 5: Eliminar Definitivamente - Fallo
     */
    @Test
    void whenEliminarDefinitivamente_failure_shouldReturnErrorMessage() {
        // Arrange
        Long empresaId = 1L;
        Long idPapelera = 99L;
        when(papeleraService.eliminarDefinitivamente(idPapelera, empresaId)).thenReturn(false);

        // Act
        String result = sut.eliminarDefinitivamente(empresaId, idPapelera);

        // Assert
        assertEquals("No se encontró el registro.", result);
        verify(papeleraService, times(1)).eliminarDefinitivamente(idPapelera, empresaId);
    }

    /**
     * TEST 6: Vaciar Papelera Completamente
     */
    @Test
    void whenVaciarPapelera_shouldCallServiceAndReturnMessage() {
        // Arrange
        Long empresaId = 1L;
        doNothing().when(papeleraService).vaciarPapelera(empresaId);

        // Act
        String result = sut.vaciarMiPapelera(empresaId);

        // Assert
        assertEquals("La papelera ha sido vaciada completamente.", result);
        verify(papeleraService, times(1)).vaciarPapelera(empresaId);
    }
}   
