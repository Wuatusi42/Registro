package com.indexia.TecnicosRegistrar.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.*;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.indexia.TecnicosRegistrar.Service.UsuariosService;
import com.indexia.TecnicosRegistrar.model.Entity.Usuarios;
import com.indexia.TecnicosRegistrar.model.utils.TecnicoDTO;

@Controller
public class LoginController {
	@Autowired
	private UsuariosService usuariosService;

	@GetMapping("/")
	public String mostrarLogin(Model model) {
		model.addAttribute("tecnicoDTO", new TecnicoDTO());
		return "login";
	}

	@PostMapping("/login")
	public String login(@RequestParam String nombreUsuario, @RequestParam String credencial, Model model) {
		model.addAttribute("tecnicoDTO", new TecnicoDTO());
		if (usuariosService.validarUsuario(nombreUsuario, credencial)) {
			return "redirect:/index";
		} else {
			model.addAttribute("error", "Credenciales inválidas");
			return "login";
		}
	}

}
