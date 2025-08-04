package com.ogl.despesapro_api.model.dto;

public record LoginResponseDTO(String token, com.ogl.despesapro_api.model.UserRole role) {
}
