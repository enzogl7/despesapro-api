package com.ogl.despesapro_api.controller;

import com.ogl.despesapro_api.model.Convite;
import com.ogl.despesapro_api.repositories.ConviteRepository;
import com.ogl.despesapro_api.services.ConviteService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/convite")
@RequiredArgsConstructor
public class ConviteController {

    private final ConviteService conviteService;

    @GetMapping("/validar/{token}")
    public ResponseEntity<?> verificarConvite(@PathVariable String token) {
        Optional<Convite> conviteOpt = conviteService.findByToken(token);
        if (conviteOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Convite não encontrado");
        }

        Convite convite = conviteOpt.get();

        if (convite.isUsado() || convite.getExpiraEm().isBefore(LocalDateTime.now())) {
            return ResponseEntity.status(HttpStatus.GONE).body("Convite expirado ou já utilizado");
        }

        Map<String, Object> resposta = Map.of(
                "nomeGestor", convite.getGestor().getNome(),
                "emailGestor", convite.getGestor().getEmail(),
                "nomeColaborador", convite.getColaborador().getNome(),
                "emailColaborador", convite.getColaborador().getEmail(),
                "expiraEm", convite.getExpiraEm()
        );

        return ResponseEntity.ok(resposta);
    }

}
