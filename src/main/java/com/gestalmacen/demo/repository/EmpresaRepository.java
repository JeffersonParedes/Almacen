package com.gestalmacen.demo.repository;

import com.gestalmacen.demo.entity.Empresa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmpresaRepository extends JpaRepository<Empresa, Long> {
    // JpaRepository ya incluye por defecto: save(), findById(), findAll(), deleteById()
    // Como Empresa no tiene filtros complejos por ahora, la dejamos así.
}
