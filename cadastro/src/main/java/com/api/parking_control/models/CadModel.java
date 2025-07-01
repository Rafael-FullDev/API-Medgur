package com.api.parking_control.models;

import jakarta.persistence.*;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.io.Serializable;

@Entity
@Table(name = "cad_usuario")
public class CadModel implements Serializable {

    private static final long SerialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id_usuario;
    private String nome_usuario;


    @Column(name = "email_usuario")
    private String emailUsuario;

    @Column(name = "senha_usuario")
    private String senha_usuario;

    @Column(name = "pontos")
    private Integer pontos_usuario = 0;

    @Column(name = "cupons")
    private Integer cupons_usuario = 0;


    public int getId_usuario() {return id_usuario;
    }

    public void setId_usuario(int id_usuario) {
        this.id_usuario = id_usuario;
    }

    public String getNome_usuario() {
        return nome_usuario;
    }

    public void setNome_usuario(String nome_usuario) {
        this.nome_usuario = nome_usuario;
    }


    public String getEmail_usuario() {
        return emailUsuario;
    }

    public void setEmail_usuario(String email_usuario) {
        this.emailUsuario = email_usuario;
    }

    public String getSenha_usuario() {
        return senha_usuario;
    }

    public void setSenha_usuario(String senha_usuario) {
        this.senha_usuario = senha_usuario;
    }

    public Integer getPontos_usuario() {
        return pontos_usuario;
    }

    public void setPontos_usuario(Integer pontos_usuario) {
        this.pontos_usuario = pontos_usuario;
    }

    public Integer getCupons_usuario() {
        return cupons_usuario;
    }

    public void setCupons_usuario(Integer cupons_usuario) {
        this.cupons_usuario = cupons_usuario;
    }

    @PrePersist
    @PreUpdate
    private void encryptPassword() {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        this.senha_usuario = encoder.encode(this.senha_usuario);
    }

}
