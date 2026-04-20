package com.gestalmacen.demo.service;

import com.gestalmacen.demo.model.Usuario;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class UsuarioServiceTest {
    private UsuarioService sut;

    @BeforeEach
    void setUp() {
        // Inicializa la memoria con el admin_bodega (ID 1) y el vendedor1 (ID 2)
        sut = new UsuarioService();
    }

    /**
     * TEST 1: Autenticar Usuario (Camino Feliz)
     * Verifica que con credenciales correctas devuelva al usuario.
     */
    @Test
    void whenAutenticarUsuario_withValidCredentials_shouldReturnUsuario() {
        // Arrange
        String username = "admin_bodega";
        String password = "1234";

        // Act
        Usuario result = sut.autenticarUsuario(username, password);

        // Assert
        assertNotNull(result);
        assertEquals("Juan Perez", result.getNombreCompleto());
        assertNotNull(result.getUltimoAcceso()); // Verifica que se actualizó el login
    }

    /**
     * TEST 2: Autenticar Usuario (Credenciales Incorrectas)
     * Demuestra la seguridad ante un intento de hackeo o error de tipeo.
     */
    @Test
    void whenAutenticarUsuario_withInvalidPassword_shouldReturnNull() {
        // Arrange
        String username = "admin_bodega";
        String password = "clave_equivocada";

        // Act
        Usuario result = sut.autenticarUsuario(username, password);

        // Assert
        assertNull(result); // El sistema lo bloquea
    }

    /**
     * TEST 3: Listar Usuarios (Aislamiento por Empresa)
     */
    @Test
    void whenListarUsuarios_shouldReturnOnlyEmpresaRecords() {
        // Arrange
        Long empresaId = 1L;

        // Act
        List<Usuario> result = sut.listarUsuariosPorEmpresa(empresaId);

        // Assert
        assertEquals(2, result.size()); // Admin y Vendedor
        
        // Verificamos que la empresa 2 (imaginaria) no tenga usuarios
        assertEquals(0, sut.listarUsuariosPorEmpresa(2L).size());
    }

    /**
     * TEST 4: Registrar Usuario
     */
    @Test
    void whenRegistrarUsuario_shouldAssignIdAndSetActive() {
        // Arrange
        Usuario nuevo = new Usuario();
        nuevo.setEmpresaId(1L);
        nuevo.setUsuario("cajero1");
        nuevo.setContrasena("abc");
        nuevo.setNombreCompleto("Luis Miguel");
        nuevo.setRol("CAJERO");

        // Act
        sut.registrarUsuario(nuevo);

        // Assert
        // Buscamos la lista actualizada
        List<Usuario> lista = sut.listarUsuariosPorEmpresa(1L);
        assertEquals(3, lista.size()); // Ahora deben ser 3
        
        // Verificamos el último agregado
        Usuario guardado = lista.get(2);
        assertEquals(3L, guardado.getId());
        assertTrue(guardado.getActivo()); // Por defecto debe nacer activo
    }

    /**
     * TEST 5: Desactivar Usuario (Borrado Lógico)
     */
    @Test
    void whenDesactivarUsuario_withValidIdAndEmpresa_shouldSetActivoToFalse() {
        // Arrange
        Long id = 2L; // vendedor1
        Long empresaId = 1L;

        // Act
        sut.desactivarUsuario(id, empresaId);

        // Assert
        // Primero lo buscamos en la lista para ver cómo quedó
        List<Usuario> lista = sut.listarUsuariosPorEmpresa(empresaId);
        Usuario vendedorModificado = lista.stream()
                                          .filter(u -> u.getId().equals(id))
                                          .findFirst()
                                          .orElse(null);
        
        assertNotNull(vendedorModificado);
        assertFalse(vendedorModificado.getActivo()); // Ahora está inactivo
    }

    /**
     * TEST 6: Regla de Negocio - Usuario Desactivado NO puede hacer Login
     * Esta es la prueba más importante para la seguridad de la empresa.
     */
    @Test
    void whenAutenticarUsuario_withDeactivatedUser_shouldReturnNull() {
        // Arrange
        Long id = 2L; // vendedor1
        Long empresaId = 1L;
        String username = "vendedor1";
        String password = "1234"; // La clave es correcta

        // Primero desactivamos al usuario (Simulamos que fue despedido)
        sut.desactivarUsuario(id, empresaId);

        // Act: El ex-empleado intenta ingresar con su clave correcta
        Usuario result = sut.autenticarUsuario(username, password);

        // Assert
        // Aunque la clave es correcta, el sistema debe devolver null porque está inactivo
        assertNull(result); 
    }
}
