package com.gestalmacen.demo.service;

import com.gestalmacen.demo.entity.PapeleraProducto;
import com.gestalmacen.demo.exception.RecursoNoEncontradoException;
import com.gestalmacen.demo.repository.PapeleraProductoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class PapeleraProductoService {

    private final PapeleraProductoRepository papeleraRepository;

    public PapeleraProductoService(PapeleraProductoRepository papeleraRepository) {
        this.papeleraRepository = papeleraRepository;
    }

    /**
     * 1. Ver todos los productos eliminados de la empresa (Devuelve la Entidad directa)
     */
    public List<PapeleraProducto> listarPapelera(Long empresaId) {
        return papeleraRepository.findByEmpresaId(empresaId);
    }

    /**
     * 2. Ver el detalle de un producto específico en la papelera
     */
    public PapeleraProducto obtenerDetalle(Long id, Long empresaId) {
        return papeleraRepository.findByIdAndEmpresaId(id, empresaId)
                .orElseThrow(() -> new RecursoNoEncontradoException("Producto no encontrado en la papelera."));
    }

    /**
     * 3. Eliminar UN producto de forma definitiva (Desaparece de MySQL)
     */
    public void eliminarDefinitivamente(Long id, Long empresaId) {
        PapeleraProducto eliminado = papeleraRepository.findByIdAndEmpresaId(id, empresaId)
                .orElseThrow(() -> new RecursoNoEncontradoException("El producto ya fue eliminado o no existe."));
        
        papeleraRepository.delete(eliminado);
    }

    /**
     * 4. Vaciar TODA la papelera de la empresa con un solo clic
     */
    @Transactional
    public void vaciarPapelera(Long empresaId) {
        papeleraRepository.deleteByEmpresaId(empresaId);
    }
}