package com.gestalmacen.demo.mapper;

import com.gestalmacen.demo.dto.request.EmpresaRequestDTO;
import com.gestalmacen.demo.dto.response.EmpresaResponseDTO;
import com.gestalmacen.demo.entity.Empresa;

import java.time.LocalDate;

public class EmpresaMapper {

    // De DTO (Internet) a Entity (Base de Datos)
    public static Empresa toEntity(EmpresaRequestDTO dto) {
        Empresa entity = new Empresa();
        entity.setRuc(dto.getRuc());
        entity.setRazonSocial(dto.getRazonSocial());
        entity.setDireccionPrincipal(dto.getDireccionPrincipal());
        entity.setTelefonoContacto(dto.getTelefonoContacto());
        // Al crearla nueva, podemos asignarle la fecha de suscripción de hoy
        entity.setFechaSuscripcion(LocalDate.now()); 
        entity.setEstado("ACTIVO"); 
        entity.setCreatedAt(java.time.LocalDateTime.now()); // Hora de creación
        entity.setUpdatedAt(java.time.LocalDateTime.now()); // Hora de actualización 
        return entity;
    }

    // De Entity (Base de Datos) a DTO (Internet)
    public static EmpresaResponseDTO toDto(Empresa entity) {
        EmpresaResponseDTO dto = new EmpresaResponseDTO();
        dto.setId(entity.getId());
        dto.setRuc(entity.getRuc());
        dto.setRazonSocial(entity.getRazonSocial());
        dto.setDireccionPrincipal(entity.getDireccionPrincipal());
        dto.setTelefonoContacto(entity.getTelefonoContacto());
        dto.setEstado(entity.getEstado());
        return dto;
    }
}
