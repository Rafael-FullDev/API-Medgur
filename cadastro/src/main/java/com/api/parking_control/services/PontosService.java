package com.api.parking_control.services;

import com.api.parking_control.models.CadModel;
import com.api.parking_control.repositories.CadRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PontosService {

    private final CadRepository cadRepository;

    public PontosService(CadRepository cadRepository) {
        this.cadRepository = cadRepository;
    }

    // funcão de adicionar ponto por id
    public ResponseEntity<?> adicionarPontos(String email, Integer pontos) {
        Optional<CadModel> usuarioOpt = cadRepository.findByEmailUsuario(email);
        if (usuarioOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuário não encontrado");
        }

        CadModel usuario = usuarioOpt.get();
        usuario.setPontos_usuario(usuario.getPontos_usuario() + pontos);
        cadRepository.save(usuario);

        return ResponseEntity.ok(usuario);
    }

    // funcão de remover ponto por id
    public ResponseEntity<?> removerPontos(String email, Integer pontos) {
        Optional<CadModel> usuarioOpt = cadRepository.findByEmailUsuario(email);
        if (usuarioOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuário não encontrado");
        }

        CadModel usuario = usuarioOpt.get();
        if (usuario.getPontos_usuario() < pontos) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Pontos insuficientes");
        }

        usuario.setPontos_usuario(usuario.getPontos_usuario() - pontos);
        cadRepository.save(usuario);

        return ResponseEntity.ok(usuario);
    }

    // trocar pontos por cupom
    public ResponseEntity<?> trocarPontosPorCupom(String email) {
        Optional<CadModel> usuarioOpt = cadRepository.findByEmailUsuario(email);
        if (usuarioOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuário não encontrado");
        }

        CadModel usuario = usuarioOpt.get();
        if (usuario.getPontos_usuario() < 100) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Pontos insuficientes para a troca por cupons");
        }

        usuario.setPontos_usuario(usuario.getPontos_usuario() - 100);
        usuario.setCupons_usuario(usuario.getCupons_usuario() + 1);
        cadRepository.save(usuario);

        return ResponseEntity.ok(usuario);
    }
}