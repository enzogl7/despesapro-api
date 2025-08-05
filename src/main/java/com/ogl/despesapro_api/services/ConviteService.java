package com.ogl.despesapro_api.services;

import com.ogl.despesapro_api.model.Convite;
import com.ogl.despesapro_api.model.Usuario;
import com.ogl.despesapro_api.repositories.ConviteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ConviteService {
    @Autowired
    private ConviteRepository conviteRepository;

    public Optional<Convite> findByToken(String token) {
        return conviteRepository.findByToken(token);
    }

    public Optional<Convite> findValidoByColaborador(Usuario colaborador) {
        return conviteRepository.findValidoByColaborador(colaborador.getId());
    }
}
