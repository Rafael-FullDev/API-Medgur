package com.api.parking_control.controllers;

import com.api.parking_control.dtos.*;
import com.api.parking_control.models.CadModel;
import com.api.parking_control.repositories.CadRepository;
import com.api.parking_control.services.AuthService;
import com.api.parking_control.services.PontosService;
import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("medgur")
@CrossOrigin(origins = "http://localhost:5173")
public class CadController {

    private final CadRepository cadRepository;
    private final AuthService authService;
    private final PontosService pontosService;

    // serviços
    public CadController(CadRepository cadRepository, AuthService authService, PontosService pontosService) {
        this.cadRepository = cadRepository;
        this.authService = authService;
        this.pontosService = pontosService;
    }

    // endpoint do cadastro
    @PostMapping("/cadastro")
    public ResponseEntity<CadModel> saveCad(@RequestBody @Valid CadRecordDt cadRecordDto) {
        var cadModel = new CadModel();
        BeanUtils.copyProperties(cadRecordDto, cadModel);

        // valor inicial dos pontos e cupons
        if (cadModel.getPontos_usuario() == null) {
            cadModel.setPontos_usuario(0);
        }
        if (cadModel.getCupons_usuario() == null) {
            cadModel.setCupons_usuario(0);
        }

        return ResponseEntity.status(HttpStatus.CREATED).body(cadRepository.save(cadModel));
    }

    // endpoin do login
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody @Valid LoginDto loginDto) {
        CadModel usuario = authService.authenticate(loginDto.email_usuario(), loginDto.senha_usuario());

        if (usuario == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body("{\"message\": \"Email ou senha inválidos\"}");
        }

        return ResponseEntity.ok(usuario);
    }

    // pucar dados dos usuarioa cadastrados
    @GetMapping("/usuarios")
    public List<CadModel> listaCadUsuario() {
        return cadRepository.findAll();
    }

    @PostMapping("/somar_pontos")
    public ResponseEntity<?> adicionarPontos(@RequestBody @Valid PontosDto pontosDto) {
        return pontosService.adicionarPontos(pontosDto.email_usuario(), pontosDto.pontos());
    }

    @PostMapping("/tirar_pontos")
    public ResponseEntity<?> removerPontos(@RequestBody @Valid PontosDto pontosDto) {
        return pontosService.removerPontos(pontosDto.email_usuario(), pontosDto.pontos());
    }

    @PostMapping("/cupom")
    public ResponseEntity<?> trocarPorCupom(@RequestBody @Valid CuponsDto cuponsDto) {
        return pontosService.trocarPontosPorCupom(cuponsDto.email_usuario());
    }

    @GetMapping("/pontos/{email}")
    public ResponseEntity<?> getPontos(@PathVariable String email) {
        Optional<CadModel> usuarioOpt = cadRepository.findByEmailUsuario(email);
        if (usuarioOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuário não encontrado");
        }
        return ResponseEntity.ok(usuarioOpt.get().getPontos_usuario());
    }

    @GetMapping("/cupons/{email}")
    public ResponseEntity<?> getCupons(@PathVariable String email) {
        Optional<CadModel> usuarioOpt = cadRepository.findByEmailUsuario(email);
        if (usuarioOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuário não encontrado");
        }
        return ResponseEntity.ok(usuarioOpt.get().getCupons_usuario());
    }
}