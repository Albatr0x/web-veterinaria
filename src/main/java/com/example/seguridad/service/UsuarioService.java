package com.example.seguridad.service;
import com.example.seguridad.model.Usuario;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import dev.samstevens.totp.code.*;
import dev.samstevens.totp.exceptions.QrGenerationException;
import dev.samstevens.totp.qr.QrData;
import dev.samstevens.totp.qr.QrGenerator;
import dev.samstevens.totp.qr.ZxingPngQrGenerator;
import dev.samstevens.totp.secret.DefaultSecretGenerator;
import dev.samstevens.totp.time.SystemTimeProvider;
import dev.samstevens.totp.time.TimeProvider;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import com.example.seguridad.dto.UsuarioDto;
import com.example.seguridad.dto.LoginRequestDto;
import com.example.seguridad.dto.LoginResponseDto;
import com.example.seguridad.dto.GuardarSecretRequest;


import java.io.ByteArrayOutputStream;
import java.util.Base64;

@Service
public class UsuarioService {

    @Value("${api.usuario.url}")
    private String usuarioApiUrl;


    private final PasswordEncoder passwordEncoder;

    public UsuarioService( PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }


    public boolean registrarUsuario(String username, String password, String correo, String nombres, String apellido_paterno, String apellido_materno, String tipo_documento, String nro_documento, String nro_telefono, String direccion) {
        UsuarioDto usuarioDto = new UsuarioDto();
        usuarioDto.username = username;
        usuarioDto.password = password;
        usuarioDto.correo = correo;
        usuarioDto.nombres = nombres;
        usuarioDto.apellido_paterno = apellido_paterno;
        usuarioDto.apellido_materno = apellido_materno;
        usuarioDto.tipo_documento = tipo_documento;
        usuarioDto.nro_documento = nro_documento;
        usuarioDto.nro_telefono = nro_telefono;
        usuarioDto.direccion = direccion;

        RestTemplate restTemplate = new RestTemplate();
        org.springframework.http.ResponseEntity<Void> response = restTemplate.postForEntity(usuarioApiUrl, usuarioDto, Void.class);

        // Devuelve true si el status es 2xx (registro exitoso), false si no
        return response.getStatusCode().is2xxSuccessful();
    }

    public org.springframework.http.ResponseEntity<LoginResponseDto> login(String username, String password) {
        LoginRequestDto loginRequest = new LoginRequestDto();
        loginRequest.username = username;
        loginRequest.password = password;

        RestTemplate restTemplate = new RestTemplate();
        String loginUrl = usuarioApiUrl + "/login";
        return restTemplate.postForEntity(loginUrl, loginRequest, LoginResponseDto.class);
    }


    public void guardarSecretKey(Long usuarioId, String secret) {
        RestTemplate restTemplate = new RestTemplate();
        String url = usuarioApiUrl + "/guardar-secret";
        GuardarSecretRequest request = new GuardarSecretRequest(usuarioId, secret);

        restTemplate.postForEntity(url, request, Void.class);
    }


    public Usuario buscarPorId(Long usuarioId) {
        RestTemplate restTemplate = new RestTemplate();
        String url = usuarioApiUrl + "/" + usuarioId;
        Usuario usuario = restTemplate.getForObject(url, Usuario.class);
        if (usuario == null) {
            throw new RuntimeException("Usuario no encontrado");
        }
        return usuario;
    }

    public String generarQRCode(String secret, String username) throws QrGenerationException {
        QrData data = new QrData.Builder()
                .label(username)
                .secret(secret)
                .issuer("Mi Aplicación")
                .algorithm(HashingAlgorithm.SHA1)
                .digits(6)
                .period(30)
                .build();

        QrGenerator generator = new ZxingPngQrGenerator();
        byte[] imageData = generator.generate(data);

        return Base64.getEncoder().encodeToString(imageData);
    }

    public boolean verificarCodigo2FA(String secret, String code) {
        TimeProvider timeProvider = new SystemTimeProvider();
        CodeGenerator codeGenerator = new DefaultCodeGenerator();
        CodeVerifier verifier = new DefaultCodeVerifier(codeGenerator, timeProvider);
        return verifier.isValidCode(secret, code);
    }


}