package com.gestalmacen.demo.repository;

import com.gestalmacen.demo.entity.Inventario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface InventarioRepository extends JpaRepository<Inventario, Long> {

    // Reemplaza a obtenerInventarioEspecifico()
    Optional<Inventario> findByProductoIdAndAlmacenIdAndEmpresaId(Long productoId, Long almacenId, Long empresaId);

    // Útil para calcular el stock global (trae todos los registros de un producto en todos los almacenes)
    List<Inventario> findByProductoIdAndEmpresaId(Long productoId, Long empresaId);
}
