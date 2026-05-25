package com.gestalmacen.demo.repository;

import com.gestalmacen.demo.entity.PapeleraProducto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;

@Repository
public interface PapeleraProductoRepository extends JpaRepository<PapeleraProducto, Long> {

    List<PapeleraProducto> findByEmpresaId(Long empresaId);

    Optional<PapeleraProducto> findByIdAndEmpresaId(Long id, Long empresaId);
    
    // Método especial para vaciar la papelera de una empresa
    @Modifying
    @Transactional
    void deleteByEmpresaId(Long empresaId);


}
