package com.gestalmacen.demo.mapper;

import com.gestalmacen.demo.dto.request.InventarioRequestDTO;
import com.gestalmacen.demo.dto.response.InventarioResponseDTO;
import com.gestalmacen.demo.entity.Inventario;

public class InventarioMapper {

    public static Inventario toEntity(InventarioRequestDTO dto, Long empresaId) {
        Inventario entity = new Inventario();
        entity.setEmpresaId(empresaId);
        entity.setProductoId(dto.getProductoId());
        entity.setAlmacenId(dto.getAlmacenId());
        entity.setStockMinimo(dto.getStockMinimo());
        entity.setStockActual(0.0); // Por defecto inicia vacío hasta que registre una entrada
        return entity;
    }

    public static InventarioResponseDTO toDto(Inventario entity) {
        InventarioResponseDTO dto = new InventarioResponseDTO();
        dto.setId(entity.getId());
        dto.setProductoId(entity.getProductoId());
        dto.setAlmacenId(entity.getAlmacenId());
        dto.setStockActual(entity.getStockActual());
        dto.setStockMinimo(entity.getStockMinimo());
        dto.setUltimaActualizacion(entity.getUltimaActualizacion());
        return dto;
    }
}
