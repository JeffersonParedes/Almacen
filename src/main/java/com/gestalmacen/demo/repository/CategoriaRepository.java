package com.gestalmacen.demo.repository;

import com.gestalmacen.demo.entity.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CategoriaRepository extends JpaRepository<Categoria, Long> {

    // Lista categorías activas de una empresa específica
    List<Categoria> findByEmpresaIdAndEstado(Long empresaId, String estado);

    Optional<Categoria> findByIdAndEmpresaId(Long id, Long empresaId);
}
