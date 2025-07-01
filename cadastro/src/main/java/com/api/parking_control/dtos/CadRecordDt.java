package com.api.parking_control.dtos;

import jakarta.validation.constraints.NotNull;

public record CadRecordDt(@NotNull String nome_usuario,
                          @NotNull String email_usuario,
                          @NotNull String senha_usuario,
                          Integer pontos_usuario,
                          Integer cupons_usuario) {
}