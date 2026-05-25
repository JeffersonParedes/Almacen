package com.gestalmacen.demo.mapper;

import com.gestalmacen.demo.dto.request.AlmacenRequestDTO;
import com.gestalmacen.demo.dto.response.AlmacenResponseDTO;
import com.gestalmacen.demo.entity.Almacen;

public class AlmacenMapper {

    // De DTO (Internet) a Entity (Base de Datos)
    public static Almacen toEntity(AlmacenRequestDTO dto, Long empresaId) {
        Almacen entity = new Almacen();
        entity.setEmpresaId(empresaId);
        entity.setNombre(dto.getNombre());
        entity.setDireccion(dto.getDireccion());
        return entity;
    }

    // De Entity (Base de Datos) a DTO (Internet)
    public static AlmacenResponseDTO toDto(Almacen entity) {
        AlmacenResponseDTO dto = new AlmacenResponseDTO();
        dto.setId(entity.getId());
        dto.setNombre(entity.getNombre());
        dto.setDireccion(entity.getDireccion());
        dto.setEstado(entity.getEstado());
        return dto;
    }
}
