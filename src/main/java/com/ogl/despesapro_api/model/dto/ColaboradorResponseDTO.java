package com.ogl.despesapro_api.model.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ColaboradorResponseDTO {

    private String id;
    private UsuarioSimplesDTO usuario;
    private LocalDateTime criadoEm;
    private String cargo = "";
    private String telefone = "";
    private boolean ativo;
}
