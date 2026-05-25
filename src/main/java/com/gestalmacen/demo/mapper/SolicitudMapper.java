package com.gestalmacen.demo.mapper;

import com.gestalmacen.demo.dto.request.SolicitudRequestDTO;
import com.gestalmacen.demo.dto.response.SolicitudResponseDTO;
import com.gestalmacen.demo.entity.Solicitud;

public class SolicitudMapper {

    public static Solicitud toEntity(SolicitudRequestDTO dto, Long empresaId) {
        Solicitud entity = new Solicitud();
        entity.setEmpresaId(empresaId);
        entity.setUsuarioId(dto.getUsuarioId());
        entity.setProductoId(dto.getProductoId());
        entity.setAlmacenId(dto.getAlmacenId());
        entity.setTipoSolicitud(dto.getTipoSolicitud());
        entity.setCantidad(dto.getCantidad());
        entity.setMotivo(dto.getMotivo());
        return entity;
    }

    public static SolicitudResponseDTO toDto(Solicitud entity) {
        SolicitudResponseDTO dto = new SolicitudResponseDTO();
        dto.setId(entity.getId());
        dto.setUsuarioId(entity.getUsuarioId());
        dto.setProductoId(entity.getProductoId());
        dto.setAlmacenId(entity.getAlmacenId());
        dto.setTipoSolicitud(entity.getTipoSolicitud());
        dto.setCantidad(entity.getCantidad());
        dto.setFechaSolicitud(entity.getFechaSolicitud());
        dto.setMotivo(entity.getMotivo());
        return dto;
    }
}