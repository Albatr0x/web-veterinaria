package com.example.cita.model;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.LocalTime;

public class Cita {



    private Long idcita;


    private Long idveterinaria;


    private Long idcliente;


    private Long idmascota;


    private Long idservicio;


    private LocalDate fecha;


    private LocalTime horainicio;


    private LocalTime horafinal;

    @Enumerated(EnumType.STRING)
    private EstadoCita estadocita = EstadoCita.PENDIENTE;

}
