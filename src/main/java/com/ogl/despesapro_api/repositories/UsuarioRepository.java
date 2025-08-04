package com.ogl.despesapro_api.repositories;

import com.ogl.despesapro_api.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;

public interface UsuarioRepository extends JpaRepository<Usuario, String> {

    UserDetails findByEmail(String email);
}
