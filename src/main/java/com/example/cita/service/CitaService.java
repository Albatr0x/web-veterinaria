package com.example.cita.service;

import com.example.cita.dto.CitaDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;

@Service
public class CitaService {

    @Value("${api.cita.url}")
    private String citaApiUrl;


    private final RestTemplate restTemplate = new RestTemplate();

    public boolean registrarCita(String fecha, String idveterinaria, String horainicio, String horafinal, String idmascota, String idservicio, Long idUsuario) {
        CitaDto citaDto = new CitaDto();
        citaDto.idveterinaria = Long.valueOf(idveterinaria);
        citaDto.idcliente = idUsuario;
        citaDto.idmascota = Long.valueOf(idmascota);
        citaDto.idservicio = Long.valueOf(idservicio);
        citaDto.fecha = LocalDate.parse(fecha);
        citaDto.horainicio = LocalTime.parse(horainicio);
        citaDto.horafinal = LocalTime.parse(horafinal);
        citaDto.estadocita = "PENDIENTE";

        String apiUrl = citaApiUrl + "/citas";
        RestTemplate restTemplate = new RestTemplate();
        org.springframework.http.ResponseEntity<Void> response = restTemplate.postForEntity(apiUrl, citaDto, Void.class);

        return response.getStatusCode().is2xxSuccessful();
    }

    // 2. ListarCita: GET /api/citas/
    public List<Map<String, Object>> listarCita() {
        String ApiUrl;
        ApiUrl = citaApiUrl + "/citas";

        return restTemplate.getForObject(ApiUrl, List.class);
    }

    // 3. ListarVeterinarias: GET /api/veterinarias
    public List<Map<String, Object>> listarVeterinarias() {
        String ApiUrl;
        ApiUrl = citaApiUrl + "/veterinarias";


        return restTemplate.getForObject(ApiUrl, List.class);
    }

    // 4. ListarServicios: GET /api/servicios
    public List<Map<String, Object>> listarServicios() {
        String ApiUrl;
        ApiUrl = citaApiUrl + "/servicios" ;
        return restTemplate.getForObject(ApiUrl, List.class);
    }

    public List<Map<String, Object>> listarMascotas(Long idUsuario) {
        String ApiUrl = citaApiUrl + "/mascotas/cliente/" + idUsuario;
        return restTemplate.getForObject(ApiUrl, List.class);
    }

}
