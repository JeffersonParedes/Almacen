package com.gestalmacen.demo.security;

import com.gestalmacen.demo.entity.Usuario;
import com.gestalmacen.demo.repository.UsuarioRepository;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UsuarioRepository usuarioRepository;

    public UserDetailsServiceImpl(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // En tu DB, el campo 'usuario' es el nombre de usuario
        Usuario logerUser = usuarioRepository.findByUsuario(username)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado: " + username));

        if (!logerUser.getActivo()) {
            throw new UsernameNotFoundException("El usuario está inactivo.");
        }

        return User.builder()
                .username(logerUser.getUsuario())
                .password(logerUser.getContrasena())
                .roles(logerUser.getRol()) // El rol ya viene como ADMINISTRADOR, ALMACENERO o VENDEDOR
                .build();
    }
}
