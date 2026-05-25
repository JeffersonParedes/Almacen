package com.gestalmacen.demo.service;

import com.gestalmacen.demo.dto.request.SolicitudRequestDTO;
import com.gestalmacen.demo.dto.response.SolicitudResponseDTO;
import com.gestalmacen.demo.entity.Solicitud;
import com.gestalmacen.demo.exception.RecursoNoEncontradoException;
import com.gestalmacen.demo.mapper.SolicitudMapper;
import com.gestalmacen.demo.repository.SolicitudRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SolicitudService {

    private final SolicitudRepository solicitudRepository;

    public SolicitudService(SolicitudRepository solicitudRepository) {
        this.solicitudRepository = solicitudRepository;
    }

    /**
     * 1. Registrar un nuevo movimiento (ENTRADA, SALIDA, AJUSTE, TRASLADO)
     */
    public SolicitudResponseDTO registrarMovimiento(SolicitudRequestDTO dto, Long empresaId) {
        
        // Convertimos a entidad y asignamos el inquilino
        Solicitud nuevaSolicitud = SolicitudMapper.toEntity(dto, empresaId);
        
        // Estampamos la fecha y hora EXACTA del servidor (Seguridad de auditoría)
        nuevaSolicitud.setFechaSolicitud(LocalDateTime.now());
        
        Solicitud guardada = solicitudRepository.save(nuevaSolicitud);
        return SolicitudMapper.toDto(guardada);
    }

    /**
     * 2. Obtener el historial completo de la empresa
     */
    public List<SolicitudResponseDTO> listarHistorialEmpresa(Long empresaId) {
        List<Solicitud> historial = solicitudRepository.findByEmpresaId(empresaId);
        return historial.stream()
                .map(SolicitudMapper::toDto)
                .collect(Collectors.toList());
    }

    /**
     * 3. Obtener el historial de un almacén específico
     */
    public List<SolicitudResponseDTO> listarHistorialPorAlmacen(Long empresaId, Long almacenId) {
        List<Solicitud> historial = solicitudRepository.findByEmpresaIdAndAlmacenId(empresaId, almacenId);
        return historial.stream()
                .map(SolicitudMapper::toDto)
                .collect(Collectors.toList());
    }

    /**
     * 4. Ver el detalle de un movimiento exacto
     */
    public SolicitudResponseDTO obtenerDetalleMovimiento(Long id, Long empresaId) {
        Solicitud solicitud = solicitudRepository.findByIdAndEmpresaId(id, empresaId)
                .orElseThrow(() -> new RecursoNoEncontradoException("Movimiento no encontrado en sus registros."));
        return SolicitudMapper.toDto(solicitud);
    }
} 
 