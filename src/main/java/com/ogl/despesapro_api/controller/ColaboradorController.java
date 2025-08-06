package com.ogl.despesapro_api.controller;

import com.ogl.despesapro_api.model.Colaborador;
import com.ogl.despesapro_api.model.Convite;
import com.ogl.despesapro_api.model.Usuario;
import com.ogl.despesapro_api.model.dto.ColaboradorResponseDTO;
import com.ogl.despesapro_api.model.dto.ConviteDTO;
import com.ogl.despesapro_api.model.dto.UsuarioSimplesDTO;
import com.ogl.despesapro_api.services.ColaboradorService;
import com.ogl.despesapro_api.services.ConviteService;
import com.ogl.despesapro_api.services.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/gestor/colaborador")
@RequiredArgsConstructor
public class ColaboradorController {

    private final UsuarioService usuarioService;
    private final ConviteService conviteService;
    private final ColaboradorService colaboradorService;

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

    @GetMapping("/listar-por-gestor")
    public ResponseEntity<List<ColaboradorResponseDTO>> listarColaboradoresPorGestor(@AuthenticationPrincipal Usuario usuario) {
        if (usuario == null) return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        List<Colaborador> colaboradoresDoGestor = colaboradorService.findAllByGestor(usuario);

        List<ColaboradorResponseDTO> respostaDTO = colaboradoresDoGestor.stream()
                .map(colaborador -> {
                    ColaboradorResponseDTO dto = new ColaboradorResponseDTO();
                    dto.setId(colaborador.getId());

                    UsuarioSimplesDTO usuarioDTO = new UsuarioSimplesDTO();
                    usuarioDTO.setId(colaborador.getUsuario().getId());
                    usuarioDTO.setNome(colaborador.getUsuario().getNome());
                    usuarioDTO.setEmail(colaborador.getUsuario().getEmail());
                    dto.setUsuario(usuarioDTO);

                    dto.setCriadoEm(colaborador.getCriadoEm());
                    dto.setAtivo(colaborador.isAtivo());

                    return dto;
                })
                .collect(Collectors.toList());

        return ResponseEntity.ok(respostaDTO);
    }
}
