package com.api.parking_control.dtos;

import jakarta.validation.constraints.NotBlank;

public record LoginDto(
        @NotBlank(message = "Email obrigatório")
        String email_usuario,

        @NotBlank(message = "Senha obrigatória")
        String senha_usuario
) {}