package com.gestalmacen.demo.mapper;

import com.gestalmacen.demo.dto.request.UsuarioRequestDTO;
import com.gestalmacen.demo.dto.response.UsuarioResponseDTO;
import com.gestalmacen.demo.entity.Usuario;

public class UsuarioMapper {

    // Convierte lo que llega de Internet (DTO) a Formato Base de Datos (Entity)
    // Pasamos el empresaId como parámetro extra porque no viene en el DTO por seguridad
    public static Usuario toEntity(UsuarioRequestDTO dto, Long empresaId) {
        Usuario entity = new Usuario();
        entity.setEmpresaId(empresaId);
        entity.setUsuario(dto.getUsuario());
        entity.setContrasena(dto.getContrasena());
        entity.setNombreCompleto(dto.getNombreCompleto());
        entity.setRol(dto.getRol());
        return entity;
    }

    // Convierte lo que sale de la BD (Entity) a Formato Internet (DTO seguro)
    public static UsuarioResponseDTO toDto(Usuario entity) {
        UsuarioResponseDTO dto = new UsuarioResponseDTO();
        dto.setId(entity.getId());
        dto.setUsuario(entity.getUsuario());
        dto.setNombreCompleto(entity.getNombreCompleto());
        dto.setRol(entity.getRol());
        dto.setActivo(entity.getActivo());
        // ¡La contraseña se queda en el olvido, nunca pasa al DTO!
        return dto;
    }
}
