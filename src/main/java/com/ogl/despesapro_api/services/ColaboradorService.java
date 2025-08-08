package com.ogl.despesapro_api.services;

import com.ogl.despesapro_api.model.Colaborador;
import com.ogl.despesapro_api.model.Usuario;
import com.ogl.despesapro_api.repositories.ColaboradorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ColaboradorService {
    @Autowired
    private ColaboradorRepository colaboradorRepository;

    public void salvar(Colaborador colaborador) {
        colaboradorRepository.save(colaborador);
    }

    public Optional<Colaborador> findByUsuario(Usuario usuario) {
        return colaboradorRepository.findByUsuario(usuario);
    }

    public Colaborador findByUsuarioDef(Usuario usuario) {
        return colaboradorRepository.findByUsuario(usuario).orElse(null);
    }

    public List<Colaborador> findAllByGestor(Usuario usuario) {
        return colaboradorRepository.findAllByGestor(usuario.getId());
    }

    public Colaborador findById(String id) {
        return colaboradorRepository.findById(id).orElse(null);
    }
}
