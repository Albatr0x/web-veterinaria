package com.example.mascota.service;

import com.example.mascota.model.Mascota;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import java.util.Arrays;
import java.util.List;
import com.example.mascota.dto.MascotaDto;

@Service
public class MascotaService {
    @Value("${api.cita.url}")
    private String citaApiUrl;



    private final RestTemplate restTemplate = new RestTemplate();


    // 1. Listar mascotas por idusuario
    public List<Mascota> listarMascotasPorUsuario(Long idUsuario) {
        String url = citaApiUrl + "/mascotas/cliente/" + idUsuario;
        ResponseEntity<Mascota[]> response = restTemplate.getForEntity(url, Mascota[].class);
        return Arrays.asList(response.getBody());
    }

    // 2. Registrar mascota
    public boolean registrarMascota(Long idcliente, String nombremascota, String especiemascota,
                                    String razamascota, Integer edadmascota, Double pesomascota) {
        MascotaDto mascotaDto = new MascotaDto();
        mascotaDto.idcliente = idcliente;
        mascotaDto.nombremascota = nombremascota;
        mascotaDto.especiemascota = especiemascota;
        mascotaDto.razamascota = razamascota;
        mascotaDto.edadmascota = edadmascota;
        mascotaDto.pesomascota = pesomascota;
        mascotaDto.activomascota = true;

        String url = citaApiUrl + "/mascotas";

        RestTemplate restTemplate = new RestTemplate();
        org.springframework.http.ResponseEntity<Void> response = restTemplate.postForEntity(url, mascotaDto, Void.class);



        return response.getStatusCode().is2xxSuccessful();
    }
}