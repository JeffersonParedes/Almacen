package com.gestalmacen.demo.service;

import com.gestalmacen.demo.dto.request.AlmacenRequestDTO;
import com.gestalmacen.demo.dto.response.AlmacenResponseDTO;
import com.gestalmacen.demo.entity.Almacen;
import com.gestalmacen.demo.exception.RecursoNoEncontradoException;
import com.gestalmacen.demo.mapper.AlmacenMapper;
import com.gestalmacen.demo.repository.AlmacenRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AlmacenService {
   private final AlmacenRepository almacenRepository;

    public AlmacenService(AlmacenRepository almacenRepository) {
        this.almacenRepository = almacenRepository;
    }

    /**
     * 1. Crear un nuevo almacén asegurando que pertenezca a la empresa actual.
     */
    public AlmacenResponseDTO crearAlmacen(AlmacenRequestDTO dto, Long empresaId) {
        Almacen nuevoAlmacen = AlmacenMapper.toEntity(dto, empresaId);
        Almacen almacenGuardado = almacenRepository.save(nuevoAlmacen);
        return AlmacenMapper.toDto(almacenGuardado);
    }

  /**
     * 2. Listar solo los almacenes del inquilino (Empresa) actual.
     * EXCLUYE los que tengan estado "INACTIVO" para simular que fueron borrados.
     */
    public List<AlmacenResponseDTO> listarPorEmpresa(Long empresaId) {
        // Usamos tu método personalizado en lugar del básico
        List<Almacen> almacenesBD = almacenRepository.findByEmpresaIdAndEstadoNot(empresaId, "INACTIVO");
        
        return almacenesBD.stream()
                .map(AlmacenMapper::toDto)
                .collect(Collectors.toList());
    } 

    /**
     * 3. Obtener un almacén específico, validando doble seguridad (ID + Empresa).
     */
    public AlmacenResponseDTO obtenerPorId(Long id, Long empresaId) {
        Almacen almacen = almacenRepository.findByIdAndEmpresaId(id, empresaId)
                .orElseThrow(() -> new RecursoNoEncontradoException("Almacén no encontrado en su organización."));
        return AlmacenMapper.toDto(almacen);
    }

    /**
     * 4. Actualizar datos físicos del almacén.
     */
    public AlmacenResponseDTO actualizarAlmacen(Long id, Long empresaId, AlmacenRequestDTO dto) {
        Almacen almacen = almacenRepository.findByIdAndEmpresaId(id, empresaId)
                .orElseThrow(() -> new RecursoNoEncontradoException("No se puede actualizar. Almacén no encontrado."));
        
        almacen.setNombre(dto.getNombre());
        almacen.setDireccion(dto.getDireccion());
        
        Almacen actualizado = almacenRepository.save(almacen);
        return AlmacenMapper.toDto(actualizado);
    }

    /**
     * 5. Activar, Inactivar o poner en Mantenimiento.
     */
    public void cambiarEstado(Long id, Long empresaId, String nuevoEstado) {
        Almacen almacen = almacenRepository.findByIdAndEmpresaId(id, empresaId)
                .orElseThrow(() -> new RecursoNoEncontradoException("No se puede cambiar el estado. Almacén no encontrado."));
        
        almacen.setEstado(nuevoEstado);
        almacenRepository.save(almacen);
    } 
}
