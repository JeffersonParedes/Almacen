package com.gestalmacen.demo.mapper;

import com.gestalmacen.demo.dto.request.ProductoRequestDTO;
import com.gestalmacen.demo.dto.response.ProductoResponseDTO;
import com.gestalmacen.demo.entity.Producto;

public class ProductoMapper {

    public static Producto toEntity(ProductoRequestDTO dto, Long empresaId) {
        Producto entity = new Producto();
        entity.setEmpresaId(empresaId);
        entity.setCategoriaId(dto.getCategoriaId());
        entity.setCodigoBarras(dto.getCodigoBarras());
        entity.setNombre(dto.getNombre());
        entity.setDescripcion(dto.getDescripcion());
        entity.setPrecio(dto.getPrecio());
        entity.setImagenUrl(dto.getImagenUrl());
        return entity;
    }

    public static ProductoResponseDTO toDto(Producto entity) {
        ProductoResponseDTO dto = new ProductoResponseDTO();
        dto.setId(entity.getId());
        dto.setNombre(entity.getNombre());
        dto.setDescripcion(entity.getDescripcion());
        dto.setPrecio(entity.getPrecio());
        dto.setEstado(entity.getEstado());
        return dto;
    }
}
     