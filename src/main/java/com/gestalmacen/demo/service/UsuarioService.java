package com.gestalmacen.demo.service;

import com.gestalmacen.demo.dto.request.UsuarioRequestDTO;
import com.gestalmacen.demo.dto.response.UsuarioResponseDTO;
import com.gestalmacen.demo.entity.Usuario;
import com.gestalmacen.demo.exception.RecursoNoEncontradoException;
import com.gestalmacen.demo.exception.ReglaNegocioException;
import com.gestalmacen.demo.mapper.UsuarioMapper;
import com.gestalmacen.demo.repository.UsuarioRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors; 

@Service
public class UsuarioService {

// 1. Inyectamos el Repository (Nuestro buscador oficial en MySQL)
    private final UsuarioRepository usuarioRepository;

    public UsuarioService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    /**
     * 1. Autenticar Usuario (Login)
     * Ahora devuelve un DTO seguro (sin contraseña) y lanza error si falla.
     */
    public UsuarioResponseDTO autenticarUsuario(String nombreUsuario, String contrasena) {
        // Le pedimos a MySQL que busque exactamente ese usuario, con esa clave y que esté activo
        Usuario usuario = usuarioRepository.findByUsuarioAndContrasenaAndActivoTrue(nombreUsuario, contrasena)
                .orElseThrow(() -> new ReglaNegocioException("Credenciales incorrectas o usuario inactivo."));

        // Si lo encuentra, actualizamos su fecha de acceso
        usuario.setUltimoAcceso(LocalDateTime.now());
        usuarioRepository.save(usuario); // Guardamos la hora en BD

        // Devolvemos la versión segura sin contraseña
        return UsuarioMapper.toDto(usuario);
    }

    /**
     * 2. Registrar Usuario
     * Recibe un DTO y el ID de la empresa por separado por seguridad.
     */
    public UsuarioResponseDTO registrarUsuario(UsuarioRequestDTO dto, Long empresaId) {
        // El Mapper se encarga de convertir el DTO a Entity y asignarle la empresa
        Usuario nuevoUsuario = UsuarioMapper.toEntity(dto, empresaId);
        
        // Lo guardamos en MySQL
        Usuario usuarioGuardado = usuarioRepository.save(nuevoUsuario);
        
        // Devolvemos el DTO con el ID que MySQL le acaba de generar
        return UsuarioMapper.toDto(usuarioGuardado);
    }

    /**
     * 3. Listar Usuarios (Aislamiento por Empresa)
     */
    public List<UsuarioResponseDTO> listarUsuariosPorEmpresa(Long empresaId) {
        // Buscamos en MySQL
        List<Usuario> usuariosBD = usuarioRepository.findByEmpresaId(empresaId);
        
        // Convertimos la lista de Entities a lista de DTOs seguros usando programación funcional (Streams)
        return usuariosBD.stream()
                .map(UsuarioMapper::toDto)
                .collect(Collectors.toList());
    }

    /**
     * 4. Desactivar Usuario (Borrado lógico)
     */
    public void desactivarUsuario(Long id, Long empresaId) {
        // Buscamos al usuario validando que exista y pertenezca a esa empresa
        Usuario usuario = usuarioRepository.findByIdAndEmpresaId(id, empresaId)
                .orElseThrow(() -> new RecursoNoEncontradoException("Usuario no encontrado en su organización."));
        
        // Le quitamos el acceso
        usuario.setActivo(false);
        
        // Actualizamos en MySQL
        usuarioRepository.save(usuario);
    }  
      
}  
