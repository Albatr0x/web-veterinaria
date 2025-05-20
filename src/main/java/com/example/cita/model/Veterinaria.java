package com.example.cita.model;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

public class Veterinaria {


    private Long idveterinaria;


    private String nombreveterinaria;


    private String direccionveterinaria;


    private String telefonoveterinaria;


    private String emailveterinaria;


    private Boolean activoveterinaria = true;
}