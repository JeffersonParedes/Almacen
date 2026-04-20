package com.gestalmacen.demo.controller;

import com.gestalmacen.demo.model.Usuario;
import com.gestalmacen.demo.service.UsuarioService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UsuarioControllerTest {
@Mock
    private UsuarioService usuarioService;

    @InjectMocks
    private UsuarioController sut;

    // --- MÉTODO AUXILIAR CORREGIDO CON TUS SETTERS EN ESPAÑOL ---
    private Usuario crearUsuarioSeguro(Long id, Long empresaId, String usuarioNombre, String contrasena, Boolean activo) {
        Usuario usuario = new Usuario();
        usuario.setId(id);
        usuario.setEmpresaId(empresaId);
        usuario.setUsuario(usuarioNombre); // Corregido: setUsuario
        usuario.setContrasena(contrasena); // Corregido: setContrasena
        usuario.setActivo(activo);         // Corregido: setActivo (es Boolean)
        return usuario;
    }

    /**
     * TEST 1: Login Exitoso (Camino Feliz)
     */
    @Test
    void whenLogin_withValidCredentials_shouldReturnUsuario() {
        // Arrange
        String username = "admin_bodega";
        String password = "123";
        Usuario expectedUsuario = crearUsuarioSeguro(1L, 1L, username, password, true);
        
        when(usuarioService.autenticarUsuario(username, password)).thenReturn(expectedUsuario);

        // Act
        Usuario result = sut.login(username, password);

        // Assert
        assertEquals(expectedUsuario, result);
        verify(usuarioService, times(1)).autenticarUsuario(username, password);
    }

    /**
     * TEST 2: Login Fallido (Camino Triste - Excepción)
     */
    @Test
    void whenLogin_withInvalidCredentials_shouldThrowException() {
        // Arrange
        String username = "hacker";
        String password = "wrongpassword";
        
        when(usuarioService.autenticarUsuario(username, password)).thenReturn(null);

        // Act & Assert
        RuntimeException excepcion = assertThrows(
            RuntimeException.class, 
            () -> sut.login(username, password)
        );

        assertEquals("Error: Credenciales incorrectas o usuario desactivado.", excepcion.getMessage());
        verify(usuarioService, times(1)).autenticarUsuario(username, password);
    }

    /**
     * TEST 3: Listar Usuarios por Empresa (Aislamiento de Datos)
     */
    @Test
    void whenListarUsuarios_shouldCallServiceAndReturnList() {
        // Arrange
        Long empresaId = 1L;
        List<Usuario> expectedList = new ArrayList<>();
        expectedList.add(crearUsuarioSeguro(1L, empresaId, "juan_perez", "123", true));
        expectedList.add(crearUsuarioSeguro(2L, empresaId, "maria_gomez", "123", true));

        when(usuarioService.listarUsuariosPorEmpresa(empresaId)).thenReturn(expectedList);

        // Act
        List<Usuario> result = sut.listarUsuariosDeMiEmpresa(empresaId);

        // Assert
        assertEquals(2, result.size());
        verify(usuarioService, times(1)).listarUsuariosPorEmpresa(empresaId);
    }

    /**
     * TEST 4: Registrar Nuevo Empleado
     */
    @Test
    void whenRegistrarEmpleado_shouldSetEmpresaIdAndReturnUsuario() {
        // Arrange
        Long empresaId = 1L;
        Usuario nuevoUsuario = crearUsuarioSeguro(null, null, "nuevo_empleado", "abc", true);
        
        doNothing().when(usuarioService).registrarUsuario(nuevoUsuario);

        // Act
        Usuario result = sut.registrarNuevoEmpleado(empresaId, nuevoUsuario);

        // Assert
        assertEquals(empresaId, result.getEmpresaId()); 
        verify(usuarioService, times(1)).registrarUsuario(nuevoUsuario);
    }

    /**
     * TEST 5: Desactivar Empleado (Borrado Lógico)
     */
    @Test
    void whenDesactivarEmpleado_shouldCallServiceAndReturnMessage() {
        // Arrange
        Long empresaId = 1L;
        Long empleadoId = 2L;
        
        doNothing().when(usuarioService).desactivarUsuario(empleadoId, empresaId);

        // Act
        String result = sut.desactivarEmpleado(empresaId, empleadoId);

        // Assert
        assertEquals("Usuario desactivado correctamente.", result);
        verify(usuarioService, times(1)).desactivarUsuario(empleadoId, empresaId);
    }  
}   
