package com.ogl.despesapro_api.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Data
public class Convite {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String token;

    private LocalDateTime criadoEm;

    private LocalDateTime expiraEm;

    private boolean usado;

    @ManyToOne
    private Usuario gestor;

    @ManyToOne
    private Usuario colaborador;

}
