package com.example.seguridad.controller;
import com.example.seguridad.dto.UsuarioDto;
import com.example.seguridad.dto.LoginRequestDto;
import com.example.seguridad.dto.LoginResponseDto;
import com.example.seguridad.service.UsuarioService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.beans.factory.annotation.Value;

@Controller
public class AuthController {

    private final UsuarioService usuarioService;




    public AuthController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @GetMapping("/about")
    public String about() {
        return "about";
    }

    @GetMapping("/contact")
    public String contact() {
        return "contact";
    }


    @GetMapping("/blog")
    public String blog() {
        return "blog";
    }


    @GetMapping("/services")
    public String services() {
        return "services";
    }



    @GetMapping("/login")
    public String mostrarLogin(@RequestParam(value = "success", required = false) String success,
                               @RequestParam(value = "error", required = false) String error,
                               Model model) {
        if (success != null) {
            model.addAttribute("mensajeExito", "¡Inicio de sesión exitoso!");
        }
        if (error != null) {
            model.addAttribute("mensajeError", "Usuario o clave incorrectas.");
        }
        return "login";
    }


    @PostMapping("/login")
    public String login(@RequestParam String username, @RequestParam String password, Model model) {
        try {
            org.springframework.http.ResponseEntity<LoginResponseDto> response = usuarioService.login(username, password);
            if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
                model.addAttribute("token", response.getBody().token);
                model.addAttribute("username", response.getBody().username);
                model.addAttribute("mensajeExito", "¡Inicio de sesión exitoso!");
                return "redirect:/dashboard";
            } else {
                model.addAttribute("mensajeError", "Credenciales inválidas o error en el servicio.");
                return "login"; // <-- Esto está correcto
            }
        } catch (Exception e) {
            model.addAttribute("mensajeError", "Credenciales inválidas o error en el servicio.");
            return "login"; // <-- Esto también
        }
    }

    @GetMapping("/registro")
    public String mostrarRegistro(@RequestParam(value = "success", required = false) String success,
                                  @RequestParam(value = "error", required = false) String error,
                                  Model model) {
        if (success != null) {
            model.addAttribute("mensajeExito", "¡Registro exitoso!");
        }
        if (error != null) {
            model.addAttribute("mensajeError", "Error al registrar usuario.");
        }
        return "registro";
    }

    @PostMapping("/registro")
    public String registrarUsuario(@RequestParam String username, @RequestParam String password, @RequestParam String correo, @RequestParam String nombres, @RequestParam String apellido_paterno, @RequestParam String apellido_materno, @RequestParam String tipo_documento, @RequestParam String nro_documento, @RequestParam String nro_telefono, @RequestParam String direccion, Model model) {
        boolean exito = usuarioService.registrarUsuario(username, password, correo, nombres, apellido_paterno, apellido_materno, tipo_documento, nro_documento, nro_telefono, direccion);
        if (exito) {
            model.addAttribute("mensajeExito", "¡Registro exitoso!");
        } else {
            model.addAttribute("mensajeError", "Error al registrar usuario.");
        }
        return "registro";
    }



    @GetMapping("/dashboard")
    public String mostrarDashboard(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            model.addAttribute("username", authentication.getName());
        }
        return "dashboard";
    }

}
