package com.ogl.despesapro_api.controller;

import com.ogl.despesapro_api.model.Colaborador;
import com.ogl.despesapro_api.model.Convite;
import com.ogl.despesapro_api.model.Usuario;
import com.ogl.despesapro_api.services.ColaboradorService;
import com.ogl.despesapro_api.services.ConviteService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/convite")
@RequiredArgsConstructor
public class ConviteController {

    private final ConviteService conviteService;
    private final ColaboradorService colaboradorService;

    @GetMapping("/validar/{token}")
    public ResponseEntity<?> verificarConvite(@PathVariable String token, @AuthenticationPrincipal Usuario usuario) {
        Optional<Convite> conviteOpt = conviteService.findByToken(token);
        if (conviteOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Convite não encontrado");
        }

        Convite convite = conviteOpt.get();

        if (convite.isUsado() || convite.getExpiraEm().isBefore(LocalDateTime.now())) {
            return ResponseEntity.status(HttpStatus.GONE).body("Convite expirado ou já utilizado");
        }

        if (!usuario.getEmail().equals(convite.getColaborador().getEmail())) return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Este convite pertence a outro colaborador.");

        Colaborador colaborador = colaboradorService.findByUsuarioDef(convite.getColaborador());
        String gestorAtualColaborador = "";
        if (colaborador != null) gestorAtualColaborador = colaborador.getGestor().getNome();

        Map<String, Object> resposta = Map.of(
                "nomeGestor", convite.getGestor().getNome(),
                "emailGestor", convite.getGestor().getEmail(),
                "nomeColaborador", convite.getColaborador().getNome(),
                "emailColaborador", convite.getColaborador().getEmail(),
                "gestorAtualColaborador", gestorAtualColaborador,
                "expiraEm", convite.getExpiraEm()
        );

        return ResponseEntity.ok(resposta);
    }

    @PutMapping("/aceitar/{token}")
    public ResponseEntity<?> aceitarConvite(@PathVariable String token) {
        try {
            Convite convite = conviteService.findByTokenDef(token);
            Optional<Colaborador> colaborador = colaboradorService.findByUsuario(convite.getColaborador());
            if (colaborador.isEmpty()) {
                Colaborador colaboradorNovo = new Colaborador();
                colaboradorNovo.setUsuario(convite.getColaborador());
                colaboradorNovo.setGestor(convite.getGestor());
                colaboradorNovo.setConviteAceito(convite);
                colaboradorNovo.setCriadoEm(LocalDateTime.now());
                colaboradorNovo.setAtivo(true);
                colaboradorService.salvar(colaboradorNovo);
            } else {
                colaborador.get().setConviteAceito(convite);
                colaborador.get().setGestor(convite.getGestor());
                colaboradorService.salvar(colaborador.get());
            }

            convite.setUsado(true);
            conviteService.salvar(convite);

            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/recusar/{token}")
    public ResponseEntity<?> recusarConvite(@PathVariable String token) {
        try {
            Convite convite = conviteService.findByTokenDef(token);
            convite.setUsado(true);
            conviteService.salvar(convite);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

}
