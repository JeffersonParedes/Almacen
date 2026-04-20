package com.gestalmacen.demo.controller;

import com.gestalmacen.demo.model.Usuario;
import com.gestalmacen.demo.service.UsuarioService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {
    private final UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    /**
     * URL: POST http://localhost:8080/api/usuarios/login?username=admin_bodega&password=1234
     * Propósito: Iniciar sesión.
     * Nota: Aquí NO pedimos el empresa-id, porque justamente el objetivo 
     * de loguearse es que el sistema te devuelva tus datos (incluyendo tu empresaId).
     */
    @PostMapping("/login")
    public Usuario login(@RequestParam String username, @RequestParam String password) {
        Usuario usuarioValidado = usuarioService.autenticarUsuario(username, password);
        
        if (usuarioValidado == null) {
            // En un proyecto más avanzado aquí lanzaríamos un Error 401 (Unauthorized)
            throw new RuntimeException("Error: Credenciales incorrectas o usuario desactivado.");
        }
        
        return usuarioValidado;
    }

    /**
     * URL: GET http://localhost:8080/api/usuarios
     * Propósito: Listar todos los trabajadores de MI bodega.
     * Magia SaaS: Usamos @RequestHeader para capturar el ID de la empresa de forma invisible.
     */
    @GetMapping
    public List<Usuario> listarUsuariosDeMiEmpresa(@RequestHeader("empresa-id") Long empresaId) {
        return usuarioService.listarUsuariosPorEmpresa(empresaId);
    }

    /**
     * URL: POST http://localhost:8080/api/usuarios
     * Propósito: Registrar un nuevo empleado.
     */
    @PostMapping
    public Usuario registrarNuevoEmpleado(@RequestHeader("empresa-id") Long empresaId, 
                                          @RequestBody Usuario nuevoUsuario) {
        
        // Regla de Oro de Seguridad: Forzamos a que el nuevo usuario 
        // pertenezca a la misma empresa del jefe que lo está creando.
        nuevoUsuario.setEmpresaId(empresaId);
        
        usuarioService.registrarUsuario(nuevoUsuario);
        return nuevoUsuario;
    }

    /**
     * URL: DELETE http://localhost:8080/api/usuarios/2
     * Propósito: Desactivar a un trabajador (Borrado Lógico).
     */
    @DeleteMapping("/{id}")
    public String desactivarEmpleado(@RequestHeader("empresa-id") Long empresaId, @PathVariable Long id) {
        usuarioService.desactivarUsuario(id, empresaId);
        return "Usuario desactivado correctamente.";
    }
}  
