package com.example.cita.controller;

import com.example.cita.service.CitaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;

@Controller
public class CitaController {

    @Autowired
    private CitaService citaService;

    @GetMapping("/menu/cita/nuevacita")
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

        // Llamadas a CitaService
        List<Map<String, Object>> veterinarias = citaService.listarVeterinarias();
        List<Map<String, Object>> servicios = citaService.listarServicios();
        List<Map<String, Object>> mascotas = citaService.listarMascotas(idUsuario);

        model.addAttribute("username", authentication.getName());
        model.addAttribute("veterinarias", veterinarias);
        model.addAttribute("servicios", servicios);
        model.addAttribute("mascotas", mascotas);

        return "menu/cita/nuevacita";
    }

    @PostMapping("/menu/cita/nuevacita")
    public String registrarCita(
            @RequestParam String fecha,
            @RequestParam String idveterinaria,
            @RequestParam String horainicio,
            @RequestParam String horafinal,
            @RequestParam String idmascota,
            @RequestParam String idservicio,
            Model model
    ) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Long idUsuario = null;
        if (authentication != null && authentication.isAuthenticated()
                && authentication.getPrincipal() instanceof com.example.Usuario.model.Usuario) {
            com.example.Usuario.model.Usuario usuario =
                    (com.example.Usuario.model.Usuario) authentication.getPrincipal();
            idUsuario = usuario.getId();
        }

        // Llama a tu servicio con los parámetros
        boolean exito = citaService.registrarCita(fecha, idveterinaria, horainicio, horafinal, idmascota, idservicio, idUsuario);

        if (exito) {
            model.addAttribute("mensajeExito", "Cita registrada correctamente.");
        } else {
            model.addAttribute("mensajeError", "Ocurrió un error al registrar la cita.");
        }
        // Recarga los datos necesarios para la vista
        // Llamadas a CitaService
        List<Map<String, Object>> veterinarias = citaService.listarVeterinarias();
        List<Map<String, Object>> servicios = citaService.listarServicios();
        List<Map<String, Object>> mascotas = citaService.listarMascotas(idUsuario);

        model.addAttribute("username", authentication.getName());
        model.addAttribute("veterinarias", veterinarias);
        model.addAttribute("servicios", servicios);
        model.addAttribute("mascotas", mascotas);
        return "menu/cita/nuevacita";
    }
}
