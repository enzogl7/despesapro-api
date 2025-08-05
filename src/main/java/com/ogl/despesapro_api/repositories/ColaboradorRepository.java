package com.ogl.despesapro_api.repositories;

import com.ogl.despesapro_api.model.Colaborador;
import com.ogl.despesapro_api.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ColaboradorRepository extends JpaRepository<Colaborador, String> {
    Optional<Colaborador> findByUsuario(Usuario usuario);
}
