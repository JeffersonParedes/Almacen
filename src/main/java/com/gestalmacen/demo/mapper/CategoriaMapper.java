package com.gestalmacen.demo.mapper;

import com.gestalmacen.demo.dto.request.CategoriaRequestDTO;
import com.gestalmacen.demo.dto.response.CategoriaResponseDTO;
import com.gestalmacen.demo.entity.Categoria;

public class CategoriaMapper {

    // De DTO (Internet) a Entity (Base de Datos)
    public static Categoria toEntity(CategoriaRequestDTO dto, Long empresaId) {
        Categoria entity = new Categoria();
        entity.setEmpresaId(empresaId);
        entity.setNombre(dto.getNombre());
        entity.setDescripcion(dto.getDescripcion());
        return entity;
    }

    // De Entity (Base de Datos) a DTO (Internet)
    public static CategoriaResponseDTO toDto(Categoria entity) {
        CategoriaResponseDTO dto = new CategoriaResponseDTO();
        dto.setId(entity.getId());
        dto.setNombre(entity.getNombre());
        dto.setDescripcion(entity.getDescripcion());
        dto.setEstado(entity.getEstado());
        return dto;
    }
} 
