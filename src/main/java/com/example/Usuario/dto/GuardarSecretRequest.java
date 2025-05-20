package com.example.Usuario.dto;

public  class GuardarSecretRequest {
    public Long usuarioId;
    public String secret;
    public GuardarSecretRequest(Long usuarioId, String secret) {
        this.usuarioId = usuarioId;
        this.secret = secret;
    }
}
