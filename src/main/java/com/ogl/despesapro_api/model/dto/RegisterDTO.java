package com.ogl.despesapro_api.model.dto;

import com.ogl.despesapro_api.model.UserRole;

public record RegisterDTO(String nome, String email, String senha, UserRole role) {
}
