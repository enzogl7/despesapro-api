package com.ogl.despesapro_api.controller;

import com.ogl.despesapro_api.model.Convite;
import com.ogl.despesapro_api.model.Usuario;
import com.ogl.despesapro_api.model.dto.ConviteDTO;
import com.ogl.despesapro_api.repositories.ConviteRepository;
import com.ogl.despesapro_api.services.ConviteService;
import com.ogl.despesapro_api.services.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/gestor/colaborador")
@RequiredArgsConstructor
public class ColaboradorController {

    private final UsuarioService usuarioService;
    private final ConviteService conviteService;

    @PostMapping("/gerar-convite")
    public ResponseEntity<Map<String, String>> gerarConviteColaborador(@RequestBody ConviteDTO dto) {
        Usuario usuarioGestor = usuarioService.findByEmail(dto.emailGestor());
        Usuario usuarioColaborador = usuarioService.findByEmail(dto.emailColaborador());

        if (usuarioColaborador == null) return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        if (usuarioGestor == null) return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();

        Optional<Convite> colaboradorJaConvidado = conviteService.findValidoByColaborador(usuarioColaborador);
        if (colaboradorJaConvidado.isPresent()) return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).build();

        Convite convite = new Convite();

        convite.setToken(UUID.randomUUID().toString());
        convite.setCriadoEm(LocalDateTime.now());
        convite.setExpiraEm(LocalDateTime.now().plusDays(1));
        convite.setUsado(false);
        convite.setGestor(usuarioGestor);
        convite.setColaborador(usuarioColaborador);
        conviteService.salvar(convite);

        return ResponseEntity.ok().body(Map.of("token", convite.getToken()));
    }
}
