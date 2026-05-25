package com.gestalmacen.demo.service;

import com.gestalmacen.demo.dto.request.EmpresaRequestDTO;
import com.gestalmacen.demo.dto.response.EmpresaResponseDTO;
import com.gestalmacen.demo.entity.Empresa;
import com.gestalmacen.demo.exception.RecursoNoEncontradoException;
import com.gestalmacen.demo.exception.ReglaNegocioException;
import com.gestalmacen.demo.mapper.EmpresaMapper;
import com.gestalmacen.demo.repository.EmpresaRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class EmpresaService {

    private final EmpresaRepository empresaRepository;

    // El único constructor sirve para inyectar el repositorio. 
    // La base de datos inicia completamente vacía.
    public EmpresaService(EmpresaRepository empresaRepository) {
        this.empresaRepository = empresaRepository;
    }

    /**
     * 1. Registrar Nueva Empresa (Onboarding)
     */
    public EmpresaResponseDTO registrarNuevaEmpresa(EmpresaRequestDTO dto) {
        if (dto.getRuc() == null || dto.getRuc().length() != 11) {
            throw new ReglaNegocioException("El RUC debe tener exactamente 11 dígitos.");
        }

        Empresa nuevaEmpresa = EmpresaMapper.toEntity(dto);
        Empresa empresaGuardada = empresaRepository.save(nuevaEmpresa);
        return EmpresaMapper.toDto(empresaGuardada);
    }

    /**
     * 2. Obtener Datos de una Empresa
     */
    public EmpresaResponseDTO obtenerDatosEmpresa(Long id) {
        Empresa empresa = empresaRepository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("Empresa no encontrada con el ID: " + id));
        
        return EmpresaMapper.toDto(empresa);
    }

    /**
     * 3. Listar todas las empresas
     */
    public List<EmpresaResponseDTO> listarTodasLasEmpresas() {
        List<Empresa> empresasBD = empresaRepository.findAll();
        return empresasBD.stream()
                .map(EmpresaMapper::toDto)
                .collect(Collectors.toList());
    }

    /**
     * 4. Actualizar Datos Generales
     */
    public EmpresaResponseDTO actualizarDatosGenerales(Long id, String nuevaDireccion, String nuevoTelefono) {
        Empresa empresa = empresaRepository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("No se puede actualizar. Empresa no encontrada."));
        
        empresa.setDireccionPrincipal(nuevaDireccion);
        empresa.setTelefonoContacto(nuevoTelefono);
        Empresa empresaActualizada = empresaRepository.save(empresa);
        
        return EmpresaMapper.toDto(empresaActualizada);
    }

    /**
     * 5. Cambiar Estado
     */
    public EmpresaResponseDTO cambiarEstado(Long id, String nuevoEstado) {
        Empresa empresa = empresaRepository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("No se puede cambiar el estado. Empresa no encontrada."));
        
        empresa.setEstado(nuevoEstado);
        Empresa empresaActualizada = empresaRepository.save(empresa);
        
        return EmpresaMapper.toDto(empresaActualizada);
    }
} 