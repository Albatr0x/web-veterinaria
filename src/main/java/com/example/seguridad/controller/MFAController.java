package com.example.seguridad.controller;
import  com.example.seguridad.service.TwoFactorAuthenticationService;
import com.example.seguridad.service.UsuarioService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.example.seguridad.model.Usuario;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import com.example.seguridad.service.UsuarioService;

@Controller
public class MFAController {


    private final TwoFactorAuthenticationService twoFactorAuthenticationService;
    private final UsuarioService usuarioService;
    private String secretKey;

    public MFAController(TwoFactorAuthenticationService twoFactorAuthenticationService,UsuarioService usuarioService) {
        this.twoFactorAuthenticationService = twoFactorAuthenticationService;
        this.usuarioService = usuarioService;
    }

    @GetMapping("/mfa")
    public String mostrarMfa(Model model) throws Exception {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Usuario usuarioAuth = (Usuario) authentication.getPrincipal();

        Usuario usuario = usuarioService.buscarPorId(usuarioAuth.getId());

        boolean mostrarQr = false;
        String qrCode = null;

        if (usuario.getSecretKey() == null) {
            String secretKey = twoFactorAuthenticationService.generateSecretKey();
            usuarioService.guardarSecretKey(usuario.getId(), secretKey);
            usuario = usuarioService.buscarPorId(usuario.getId()); // Recarga usuario actualizado
            qrCode = twoFactorAuthenticationService.generateQrCode(
                    usuario.getSecretKey(), usuario.getUsername(), "MiAplicacion");
            mostrarQr = true;
        }

        model.addAttribute("mostrarQr", mostrarQr);
        model.addAttribute("qrCode", qrCode);
        return "mfa";
    }

    @PostMapping("/mfa")
    public String verificarMfa(@RequestParam String code) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Usuario usuarioAuth = (Usuario) authentication.getPrincipal();
        Usuario usuario = usuarioService.buscarPorId(usuarioAuth.getId());

        if (usuario.getSecretKey() == null) {
            return "redirect:/mfa?setup";
        }

        if (twoFactorAuthenticationService.verifyCode(usuario.getSecretKey(), code)) {
            return "redirect:/dashboard";
        }
        return "redirect:/mfa?error";
    }
}