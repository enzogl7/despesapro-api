package com.ogl.despesapro_api.repositories;

import com.ogl.despesapro_api.model.Convite;
import com.ogl.despesapro_api.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface ConviteRepository extends JpaRepository<Convite, Long> {
    Optional<Convite> findByToken(String token);

    @Query(value = "select * from convite where colaborador_id = :colaboradorId and expira_em > current_timestamp and usado = false", nativeQuery = true)
    Optional<Convite> findValidoByColaborador(@Param("colaboradorId") String colaboradorId);

}
