package com.gestalmacen.demo.repository;

import com.gestalmacen.demo.entity.Solicitud;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional; // <-- No olvides agregar este import

@Repository
public interface SolicitudRepository extends JpaRepository<Solicitud, Long> {

    // 1. Trae el historial completo de movimientos de una empresa
    List<Solicitud> findByEmpresaId(Long empresaId);

    // 2. Filtra los movimientos de un local específico (Ej. Solo taller o solo tienda)
    List<Solicitud> findByEmpresaIdAndAlmacenId(Long empresaId, Long almacenId);

    // 3. Busca un movimiento específico (Auditoría segura)
    Optional<Solicitud> findByIdAndEmpresaId(Long id, Long empresaId);
}