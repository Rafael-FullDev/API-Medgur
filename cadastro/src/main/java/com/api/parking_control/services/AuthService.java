package com.api.parking_control.services;

import com.api.parking_control.models.CadModel;
import com.api.parking_control.repositories.CadRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthService {

    private final CadRepository cadRepository;
    private final PasswordEncoder passwordEncoder;

    // criptografia de senha
    public AuthService(CadRepository cadRepository, PasswordEncoder passwordEncoder) {
        this.cadRepository = cadRepository;
        this.passwordEncoder = passwordEncoder;
    }

    // verificação pra ver se existe email e senha ou seja um usuario
     public CadModel authenticate(String email, String senha) {
        Optional<CadModel> usuarioOpt = cadRepository.findByEmailUsuario(email); // Já está correto

        if (usuarioOpt.isEmpty()) {
            return null;
        }

        CadModel usuario = usuarioOpt.get();
        if (!passwordEncoder.matches(senha, usuario.getSenha_usuario())) {
            return null;
        }

        return usuario;
    }
}