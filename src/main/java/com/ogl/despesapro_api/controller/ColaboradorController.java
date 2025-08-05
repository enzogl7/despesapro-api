package com.ogl.despesapro_api.controller;

import com.ogl.despesapro_api.model.Convite;
import com.ogl.despesapro_api.model.Usuario;
import com.ogl.despesapro_api.model.dto.ConviteDTO;
import com.ogl.despesapro_api.repositories.ConviteRepository;
import com.ogl.despesapro_api.repositories.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.UUID;

@RestController
@RequestMapping("/api/gestor/colaborador")
@RequiredArgsConstructor
public class ColaboradorController {

    private final UsuarioRepository usuarioRepository;
    private final ConviteRepository conviteRepository;

    @PostMapping("/gerar-convite")
    public ResponseEntity<String> gerarConviteColaborador(@RequestBody ConviteDTO dto) {
        Usuario usuarioGestor = (Usuario) usuarioRepository.findByEmail(dto.emailGestor());
        if (usuarioGestor != null) {
            Convite convite = new Convite();

            convite.setToken(UUID.randomUUID().toString());
            convite.setCriadoEm(LocalDateTime.now());
            convite.setExpiraEm(LocalDateTime.now().plusDays(1));
            convite.setUsado(false);
            convite.setGestor(usuarioGestor);
            conviteRepository.save(convite);
            return ResponseEntity.ok().body(convite.getToken());
        }
        return ResponseEntity.noContent().build();
    }
}
