package com.gestalmacen.demo.service;

import com.gestalmacen.demo.model.Usuario;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class UsuarioService {

    // Simulamos la base de datos en memoria usando una Lista
    private List<Usuario> usuarios;
    private Long contadorId = 1L; // Simulamos el AUTO_INCREMENT de la BD

    public UsuarioService() {
        usuarios = new ArrayList<>();
        // Agregamos un par de usuarios por defecto para que puedas hacer pruebas en Postman
        usuarios.add(new Usuario(contadorId++, 1L, "admin_bodega", "1234", "Juan Perez", 
                                 "ADMINISTRADOR", LocalDateTime.now(), true, 
                                 LocalDateTime.now(), LocalDateTime.now()));
                                 
        usuarios.add(new Usuario(contadorId++, 1L, "vendedor1", "1234", "Maria Gomez", 
                                 "VENDEDOR", LocalDateTime.now(), true, 
                                 LocalDateTime.now(), LocalDateTime.now()));
    }

    /**
     * 1. Autenticar Usuario (Login)
     * Retorna el usuario si las credenciales son correctas y está activo.
     */
    public Usuario autenticarUsuario(String nombreUsuario, String contrasena) {
        for (Usuario u : usuarios) {
            if (u.getUsuario().equals(nombreUsuario) && 
                u.getContrasena().equals(contrasena) && 
                u.getActivo()) {
                
                // Actualizamos su fecha de último acceso
                u.setUltimoAcceso(LocalDateTime.now());
                return u; 
            }
        }
        return null; // En el Controller evaluaremos si es null para mandar un error 401
    }

    /**
     * 2. Registrar Usuario
     */
    public void registrarUsuario(Usuario nuevoUsuario) {
        nuevoUsuario.setId(contadorId++);
        nuevoUsuario.setActivo(true);
        nuevoUsuario.setCreatedAt(LocalDateTime.now());
        nuevoUsuario.setUpdatedAt(LocalDateTime.now());
        
        usuarios.add(nuevoUsuario);
    }

    /**
     * 3. Listar Usuarios (Con Aislamiento de Datos por Empresa)
     */
    public List<Usuario> listarUsuariosPorEmpresa(Long empresaId) {
        List<Usuario> listaFiltrada = new ArrayList<>();
        
        for (Usuario u : usuarios) {
            // Solo devolvemos los usuarios que pertenezcan a la bodega solicitada
            if (u.getEmpresaId().equals(empresaId)) {
                listaFiltrada.add(u);
            }
        }
        return listaFiltrada;
    }

    /**
     * 4. Desactivar Usuario (Borrado lógico)
     */
    public void desactivarUsuario(Long id, Long empresaId) {
        for (Usuario u : usuarios) {
            // Verificamos que el usuario exista y que realmente pertenezca a esa empresa
            if (u.getId().equals(id) && u.getEmpresaId().equals(empresaId)) {
                u.setActivo(false);
                u.setUpdatedAt(LocalDateTime.now());
                return; // Terminamos la ejecución porque ya lo encontramos
            }
        }
    }
      
}
