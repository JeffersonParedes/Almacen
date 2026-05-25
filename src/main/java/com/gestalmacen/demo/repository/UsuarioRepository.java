package com.gestalmacen.demo.repository;

import com.gestalmacen.demo.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    
    // Reemplaza al bucle for de tu listarUsuariosPorEmpresa
    List<Usuario> findByEmpresaId(Long empresaId);

    // Reemplaza al bucle for de tu autenticarUsuario (Login)
    // Spring leerá esto y hará: SELECT * FROM usuario WHERE usuario = ? AND contrasena = ? AND activo = true
    Optional<Usuario> findByUsuarioAndContrasenaAndActivoTrue(String usuario, String contrasena);
    
    // Para buscar un usuario específico validando que pertenezca a la empresa
    Optional<Usuario> findByIdAndEmpresaId(Long id, Long empresaId);
}