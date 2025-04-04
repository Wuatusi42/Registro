package com.indexia.TecnicosRegistrar.Controllers;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.*;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.indexia.TecnicosRegistrar.Service.UsuariosService;
import com.indexia.TecnicosRegistrar.model.Entity.Usuarios;
import com.indexia.TecnicosRegistrar.model.Repository.UsuariosDAO;
import com.indexia.TecnicosRegistrar.model.utils.TecnicoDTO;

@Controller
public class LoginController {
	@Autowired
	private UsuariosService usuariosService;
	 private static final Logger logger = LoggerFactory.getLogger(LoginController.class);
	 @Autowired
	 private UsuariosDAO usuariosDAO; // Inyecta tu UsuariosDAO
	@GetMapping("/")
	public String mostrarLogin(Model model) {
		model.addAttribute("tecnicoDTO", new TecnicoDTO());
		return "login";
	}

	@PostMapping("/login")
    public String login(@RequestParam String nombreUsuario, @RequestParam String credencial, Model model, HttpSession session) {
        model.addAttribute("tecnicoDTO", new TecnicoDTO());
        if (usuariosService.validarUsuario(nombreUsuario, credencial)) {
            Usuarios usuarioEncontrado = usuariosDAO.findByNombreUsuario(nombreUsuario); // Obtén el objeto Usuarios

            if (usuarioEncontrado != null) {
                session.setAttribute("usernameC", usuarioEncontrado); // Guarda el objeto Usuarios con la clave "usernameC"
                session.setAttribute("username", nombreUsuario); // Guarda el nombre de usuario (opcional)
                logger.info("Usuario '{}' ha iniciado sesión.", nombreUsuario);
                return "redirect:/index";
            } else {
                model.addAttribute("error", "Error al obtener la información del usuario.");
                return "login";
            }
        } else {
            model.addAttribute("error", "Credenciales inválidas");
            return "login";
        }
    }

}
