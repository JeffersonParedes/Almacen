package com.gestalmacen.demo.controller;

import com.gestalmacen.demo.model.Categoria;
import com.gestalmacen.demo.service.CategoriaService;
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
public class CategoriaControllerTest {
   @Mock
    CategoriaService categoriaService;

    @InjectMocks
    CategoriaController sut;

    /**
     * TEST 1: Listar Categorías
     */
    @Test
    void whenListarCategorias_shouldCallServiceAndReturnTheCorrectList() {
        // Arrange
        Long empresaId = 1L;
        List<Categoria> expectedList = this.generateCategoriesList(3, empresaId);
        doReturn(expectedList).when(categoriaService).listarCategoriasPorEmpresa(empresaId);

        // Act
        List<Categoria> result = sut.listarCategorias(empresaId);

        // Assert
        verify(categoriaService, times(1)).listarCategoriasPorEmpresa(empresaId);
        assertEquals(expectedList.size(), result.size());
        assertEquals(expectedList, result);
    }

    /**
     * TEST 2: Registrar Categoría (Asegurando la inyección del empresaId)
     */
    @Test
    void whenRegistrarCategoria_shouldSetEmpresaIdAndCallService() {
        // Arrange
        Long empresaId = 1L;
        Categoria nuevaCategoria = new Categoria(null, null, "Lácteos", "Leche y quesos", null, null, null);
        Categoria expectedCategoria = new Categoria(1L, empresaId, "Lácteos", "Leche y quesos", "ACTIVO", LocalDateTime.now(), LocalDateTime.now());
        
        doReturn(expectedCategoria).when(categoriaService).registrarCategoria(nuevaCategoria);

        // Act
        Categoria result = sut.registrarCategoria(empresaId, nuevaCategoria);

        // Assert
        verify(categoriaService, times(1)).registrarCategoria(nuevaCategoria);
        assertEquals(empresaId, nuevaCategoria.getEmpresaId()); // Verificamos el aislamiento de datos
        assertEquals(expectedCategoria, result);
    }

    /**
     * TEST 3: Actualizar Categoría
     */
    @Test
    void whenActualizarCategoria_shouldCallServiceWithCorrectParameters() {
        // Arrange
        Long empresaId = 1L;
        Long categoriaId = 1L;
        Categoria datosActualizados = new Categoria(null, null, "Lácteos Premium", "Solo quesos importados", null, null, null);
        Categoria expectedCategoria = new Categoria(categoriaId, empresaId, "Lácteos Premium", "Solo quesos importados", "ACTIVO", LocalDateTime.now(), LocalDateTime.now());

        doReturn(expectedCategoria).when(categoriaService).actualizarCategoria(categoriaId, empresaId, datosActualizados.getNombre(), datosActualizados.getDescripcion());

        // Act
        Categoria result = sut.actualizarCategoria(empresaId, categoriaId, datosActualizados);

        // Assert
        verify(categoriaService, times(1)).actualizarCategoria(categoriaId, empresaId, datosActualizados.getNombre(), datosActualizados.getDescripcion());
        assertEquals(expectedCategoria, result);
    }

    /**
     * TEST 4: Inactivar Categoría (Borrado Lógico)
     */
    @Test
    void whenInactivarCategoria_shouldReturnSuccessMessage() {
        // Arrange
        Long empresaId = 1L;
        Long categoriaId = 1L;
        doReturn(true).when(categoriaService).inactivarCategoria(categoriaId, empresaId);

        // Act
        String result = sut.inactivarCategoria(empresaId, categoriaId);

        // Assert
        verify(categoriaService, times(1)).inactivarCategoria(categoriaId, empresaId);
        assertEquals("Categoría inactivada correctamente.", result);
    }

    /**
     * MÉTODO AUXILIAR
     */
    private List<Categoria> generateCategoriesList(int wantedSize, Long empresaId) {
        List<Categoria> categories = new ArrayList<>();
        for (int i = 0; i < wantedSize; i++) {
            Categoria category = new Categoria(
                    (long) i,
                    empresaId,
                    "Categoría " + i,
                    "Descripción " + i,
                    "ACTIVO",
                    LocalDateTime.now(),
                    LocalDateTime.now()
            );
            categories.add(category);
        }
        return categories;
    } 
}   
