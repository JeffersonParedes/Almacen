package com.gestalmacen.demo.service;
import com.gestalmacen.demo.dto.request.CategoriaRequestDTO;
import com.gestalmacen.demo.dto.response.CategoriaResponseDTO;
import com.gestalmacen.demo.entity.Categoria;
import com.gestalmacen.demo.exception.RecursoNoEncontradoException;
import com.gestalmacen.demo.mapper.CategoriaMapper;
import com.gestalmacen.demo.repository.CategoriaRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoriaService {
 private final CategoriaRepository categoriaRepository;

    public CategoriaService(CategoriaRepository categoriaRepository) {
        this.categoriaRepository = categoriaRepository;
    }

    public CategoriaResponseDTO crearCategoria(CategoriaRequestDTO dto, Long empresaId) {
        Categoria nuevaCategoria = CategoriaMapper.toEntity(dto, empresaId);
        Categoria categoriaGuardada = categoriaRepository.save(nuevaCategoria);
        return CategoriaMapper.toDto(categoriaGuardada);
    }

   public List<CategoriaResponseDTO> listarPorEmpresa(Long empresaId) {
        // Usamos tu método exacto: buscamos las que están expresamente en estado "ACTIVO"
        List<Categoria> categoriasBD = categoriaRepository.findByEmpresaIdAndEstado(empresaId, "ACTIVO");
        
        return categoriasBD.stream()
                .map(CategoriaMapper::toDto)
                .collect(Collectors.toList());
    } 

    public CategoriaResponseDTO obtenerPorId(Long id, Long empresaId) {
        Categoria categoria = categoriaRepository.findByIdAndEmpresaId(id, empresaId)
                .orElseThrow(() -> new RecursoNoEncontradoException("Categoría no encontrada en su organización."));
        return CategoriaMapper.toDto(categoria);
    }

    public CategoriaResponseDTO actualizarCategoria(Long id, Long empresaId, CategoriaRequestDTO dto) {
        Categoria categoria = categoriaRepository.findByIdAndEmpresaId(id, empresaId)
                .orElseThrow(() -> new RecursoNoEncontradoException("No se puede actualizar. Categoría no encontrada."));
        
        categoria.setNombre(dto.getNombre());
        categoria.setDescripcion(dto.getDescripcion());
        
        Categoria actualizada = categoriaRepository.save(categoria);
        return CategoriaMapper.toDto(actualizada);
    }

    public void cambiarEstado(Long id, Long empresaId, String nuevoEstado) {
        Categoria categoria = categoriaRepository.findByIdAndEmpresaId(id, empresaId)
                .orElseThrow(() -> new RecursoNoEncontradoException("No se puede cambiar el estado. Categoría no encontrada."));
        
        categoria.setEstado(nuevoEstado);
        categoriaRepository.save(categoria);
    }
}  
