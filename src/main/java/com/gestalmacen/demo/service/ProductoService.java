package com.gestalmacen.demo.service;

import com.gestalmacen.demo.dto.request.ProductoRequestDTO;
import com.gestalmacen.demo.dto.response.ProductoResponseDTO;
import com.gestalmacen.demo.entity.Producto;
import com.gestalmacen.demo.exception.RecursoNoEncontradoException;
import com.gestalmacen.demo.mapper.ProductoMapper;
import com.gestalmacen.demo.repository.ProductoRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductoService {
 private final ProductoRepository productoRepository;

    public ProductoService(ProductoRepository productoRepository) {
        this.productoRepository = productoRepository;
    }

    public ProductoResponseDTO crearProducto(ProductoRequestDTO dto, Long empresaId) {
        // Convertimos el DTO a Entidad. El Mapper se encargará de setear la Empresa y Categoría.
        Producto nuevoProducto = ProductoMapper.toEntity(dto, empresaId);
        Producto productoGuardado = productoRepository.save(nuevoProducto);
        return ProductoMapper.toDto(productoGuardado);
    }
public List<ProductoResponseDTO> listarCatologoPorEmpresa(Long empresaId) {
        // Usamos tu método exacto del Repository: buscamos explícitamente los productos "ACTIVOS"
        List<Producto> productosBD = productoRepository.findByEmpresaIdAndEstado(empresaId, "ACTIVO");
        
        return productosBD.stream()
                .map(ProductoMapper::toDto)
                .collect(Collectors.toList());
    }  

    public ProductoResponseDTO obtenerPorId(Long id, Long empresaId) {
        Producto producto = productoRepository.findByIdAndEmpresaId(id, empresaId)
                .orElseThrow(() -> new RecursoNoEncontradoException("Producto no encontrado en el catálogo de su empresa."));
        return ProductoMapper.toDto(producto);
    }

    public ProductoResponseDTO actualizarProducto(Long id, Long empresaId, ProductoRequestDTO dto) {
        Producto producto = productoRepository.findByIdAndEmpresaId(id, empresaId)
                .orElseThrow(() -> new RecursoNoEncontradoException("No se puede actualizar. Producto no encontrado."));
        
        // Actualizamos los datos físicos y comerciales
        producto.setNombre(dto.getNombre());
        producto.setCodigoBarras(dto.getCodigoBarras());
        producto.setDescripcion(dto.getDescripcion());
        producto.setPrecio(dto.getPrecio());
        producto.setImagenUrl(dto.getImagenUrl());
        // Nota: Para cambiar de categoría se requeriría setear la nueva categoría aquí si lo permites
        
        Producto actualizado = productoRepository.save(producto);
        return ProductoMapper.toDto(actualizado);
    }

    public void cambiarEstado(Long id, Long empresaId, String nuevoEstado) {
        Producto producto = productoRepository.findByIdAndEmpresaId(id, empresaId)
                .orElseThrow(() -> new RecursoNoEncontradoException("No se puede cambiar el estado. Producto no encontrado."));
        
        producto.setEstado(nuevoEstado);
        productoRepository.save(producto);
    }
}  
