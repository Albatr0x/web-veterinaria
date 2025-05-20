package com.example.cita.dto;

import java.time.LocalDate;
import java.time.LocalTime;

public class CitaDto {
    public Long idveterinaria;
    public Long idcliente;
    public Long idmascota;
    public Long idservicio;
    public LocalDate fecha;
    public LocalTime horainicio;
    public LocalTime horafinal;
    public String estadocita;
}