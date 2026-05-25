package com.gestalmacen.demo.repository;

import com.gestalmacen.demo.entity.Producto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductoRepository extends JpaRepository<Producto, Long> {

    // El catálogo web: Solo productos activos de la empresa
    List<Producto> findByEmpresaIdAndEstado(Long empresaId, String estado);

    Optional<Producto> findByIdAndEmpresaId(Long id, Long empresaId);
}