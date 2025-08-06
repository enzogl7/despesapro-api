package com.ogl.despesapro_api.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Colaborador {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @OneToOne
    private Usuario usuario;

    @ManyToOne
    private Usuario gestor;

    @OneToOne
    private Convite conviteAceito;

    private LocalDateTime criadoEm;

    @Column(name = "ativo")
    private boolean ativo;
}
