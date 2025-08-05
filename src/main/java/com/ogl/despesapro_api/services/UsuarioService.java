package com.ogl.despesapro_api.services;

import com.ogl.despesapro_api.model.Usuario;
import com.ogl.despesapro_api.repositories.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UsuarioService {
    @Autowired
    private UsuarioRepository usuarioRepository;

    public Usuario findByEmail(String email) {
        return (Usuario) usuarioRepository.findByEmail(email);
    }
}
