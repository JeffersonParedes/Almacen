package com.gestalmacen.demo.service;

import com.gestalmacen.demo.dto.request.InventarioRequestDTO;
import com.gestalmacen.demo.dto.response.InventarioResponseDTO;
import com.gestalmacen.demo.entity.Inventario;
import com.gestalmacen.demo.exception.RecursoNoEncontradoException;
import com.gestalmacen.demo.mapper.InventarioMapper;
import com.gestalmacen.demo.repository.InventarioRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class InventarioService {
 private final InventarioRepository inventarioRepository;

    public InventarioService(InventarioRepository inventarioRepository) {
        this.inventarioRepository = inventarioRepository;
    }

    /**
     * 1. Registrar o Actualizar Stock (Upsert)
     * Si el producto ya tiene stock en ese almacén, lo actualiza. Si no, crea la fila.
     */
    public InventarioResponseDTO registrarOActualizarInventario(InventarioRequestDTO dto, Long empresaId) {
        
        // Buscamos si ya existe la relación Producto-Almacén usando tu método del Repository
        Inventario inventario = inventarioRepository
                .findByProductoIdAndAlmacenIdAndEmpresaId(dto.getProductoId(), dto.getAlmacenId(), empresaId)
                .orElse(null);

        if (inventario == null) {
            // Es la primera vez que este producto entra a este almacén: Lo creamos
            inventario = InventarioMapper.toEntity(dto, empresaId);
            inventario.setUltimaActualizacion(LocalDateTime.now());
        } else {
            // Ya existía: Solo actualizamos las cantidades
            inventario.setStockActual(dto.getStockActual());
            inventario.setStockMinimo(dto.getStockMinimo());
            inventario.setUltimaActualizacion(LocalDateTime.now());
        }

        Inventario guardado = inventarioRepository.save(inventario);
        return InventarioMapper.toDto(guardado);
    }

    /**
     * 2. Consultar el stock exacto de un producto en un solo almacén
     */
    public InventarioResponseDTO consultarStockEnAlmacen(Long productoId, Long almacenId, Long empresaId) {
        Inventario inventario = inventarioRepository
                .findByProductoIdAndAlmacenIdAndEmpresaId(productoId, almacenId, empresaId)
                .orElseThrow(() -> new RecursoNoEncontradoException("No hay registro de inventario para este producto en el almacén indicado."));
        
        return InventarioMapper.toDto(inventario);
    }

    /**
     * 3. Consultar stock global (Trae el desglose del producto en TODOS los almacenes de la empresa)
     */
    public List<InventarioResponseDTO> consultarStockGlobal(Long productoId, Long empresaId) {
        // Usamos tu segundo método del Repository
        List<Inventario> inventarios = inventarioRepository.findByProductoIdAndEmpresaId(productoId, empresaId);
        
        return inventarios.stream()
                .map(InventarioMapper::toDto)
                .collect(Collectors.toList());
    }
}
