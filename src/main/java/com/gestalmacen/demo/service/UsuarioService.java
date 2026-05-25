package com.gestalmacen.demo.service;

import com.gestalmacen.demo.dto.request.UsuarioRequestDTO;
import com.gestalmacen.demo.dto.response.UsuarioResponseDTO;
import com.gestalmacen.demo.entity.Usuario;
import com.gestalmacen.demo.exception.RecursoNoEncontradoException;
import com.gestalmacen.demo.mapper.UsuarioMapper;
import com.gestalmacen.demo.repository.UsuarioRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors; 

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    public UsuarioService(UsuarioRepository usuarioRepository, PasswordEncoder passwordEncoder) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder; 
    }

    /**
     * 1. Registrar Usuario (Fusionado)
     * Recibe DTO, convierte, ENCRIPTA LA CLAVE, guarda y devuelve DTO seguro.
     */
    public UsuarioResponseDTO registrarUsuario(UsuarioRequestDTO dto, Long empresaId) {
        Usuario nuevoUsuario = UsuarioMapper.toEntity(dto, empresaId);
        
        // Magia de seguridad: Encriptamos la clave cruda que viene del DTO
        String passwordEncriptada = passwordEncoder.encode(dto.getContrasena());
        nuevoUsuario.setContrasena(passwordEncriptada);
        
        nuevoUsuario.setActivo(true);
        Usuario usuarioGuardado = usuarioRepository.save(nuevoUsuario);
        
        return UsuarioMapper.toDto(usuarioGuardado);
    }

    /**
     * 2. Listar Usuarios (Usando DTOs por seguridad)
     */
    public List<UsuarioResponseDTO> listarUsuariosPorEmpresa(Long empresaId) {
        List<Usuario> usuariosBD = usuarioRepository.findByEmpresaId(empresaId);
        
        return usuariosBD.stream()
                .map(UsuarioMapper::toDto)
                .collect(Collectors.toList());
    }

    /**
     * 3. Cambiar Estado (Activar / Desactivar)
     */
    public void cambiarEstadoActivo(Long id, Long empresaId, boolean activo) {
        Usuario usuario = usuarioRepository.findByIdAndEmpresaId(id, empresaId)
                .orElseThrow(() -> new RecursoNoEncontradoException("Usuario no encontrado en su organización."));
        
        usuario.setActivo(activo);
        usuarioRepository.save(usuario);
    } 
}   
