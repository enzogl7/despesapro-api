package com.ogl.despesapro_api.repositories;

import com.ogl.despesapro_api.model.Colaborador;
import com.ogl.despesapro_api.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ColaboradorRepository extends JpaRepository<Colaborador, String> {
    Optional<Colaborador> findByUsuario(Usuario usuario);

    @Query(nativeQuery = true, value = "select * from colaborador where gestor_id = :idUsuario")
    List<Colaborador> findAllByGestor(@Param("idUsuario") String idUsuario);
}
