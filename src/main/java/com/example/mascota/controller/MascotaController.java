package com.example.mascota.controller;

import com.example.cita.service.CitaService;
import com.example.mascota.service.MascotaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.ui.Model;

import java.util.List;
import java.util.Map;

@Controller
public class MascotaController {

    @Autowired
    private MascotaService mascotaService;

    @GetMapping("/menu/mascota/registrarmascota")
    public String mostrarFormularioCita(Model model) {
        // Obtener usuario autenticado
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Long idUsuario = null;
        if (authentication != null && authentication.isAuthenticated()
                && authentication.getPrincipal() instanceof com.example.Usuario.model.Usuario) {
            com.example.Usuario.model.Usuario usuario =
                    (com.example.Usuario.model.Usuario) authentication.getPrincipal();
            idUsuario = usuario.getId();
            model.addAttribute("idUsuario", idUsuario);
        }


        model.addAttribute("username", authentication.getName());


        return "menu/mascota/registrarmascota";
    }


    @PostMapping("/menu/mascota/registrar")
    public String registrarMascota(
            @RequestParam String nombremascota,
            @RequestParam String especiemascota,
            @RequestParam String razamascota,
            @RequestParam Integer edadmascota,
            @RequestParam Double pesomascota,
            Model model
    ) {
        // Aquí puedes obtener el usuario autenticado si lo necesitas

        // Obtener usuario autenticado
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Long idUsuario = null;
        if (authentication != null && authentication.isAuthenticated()
                && authentication.getPrincipal() instanceof com.example.Usuario.model.Usuario) {
            com.example.Usuario.model.Usuario usuario =
                    (com.example.Usuario.model.Usuario) authentication.getPrincipal();
            idUsuario = usuario.getId();
            model.addAttribute("idUsuario", idUsuario);
        }

        model.addAttribute("username", authentication.getName());

        // Lógica para registrar la mascota usando MascotaService
        boolean exito = mascotaService.registrarMascota(idUsuario,
                nombremascota, especiemascota, razamascota, edadmascota, pesomascota
        );

        if (exito) {
            model.addAttribute("mensajeExito", "Mascota registrada correctamente.");
        } else {
            model.addAttribute("mensajeError", "Ocurrió un error al registrar la mascota.");
        }
        return "menu/mascota/registrarmascota";
    }

}
