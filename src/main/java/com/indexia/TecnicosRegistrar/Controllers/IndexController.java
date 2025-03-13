package com.indexia.TecnicosRegistrar.Controllers;

import com.indexia.TecnicosRegistrar.Service.TecnicoService;
import com.indexia.TecnicosRegistrar.Service.impl.TecnicoServiceImpl;
import com.indexia.TecnicosRegistrar.model.Entity.Bancos;
import com.indexia.TecnicosRegistrar.model.Entity.Tecnico;
import com.indexia.TecnicosRegistrar.model.utils.RespuestaServicio;
import com.indexia.TecnicosRegistrar.model.utils.TecnicoDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Controller
public class IndexController {
	@Autowired
	private TecnicoService tecnicoService;
	@Autowired
	private TecnicoServiceImpl tecnicoServiveImpl;

	@GetMapping("/index")
	public String mostrarForm(Model model) {
		List<Tecnico> tecnicos = tecnicoService.obtenerTodosLosTecnicos();
		model.addAttribute("tecnicoDTO", new TecnicoDTO());
		model.addAttribute("tecnicos", tecnicos);
		return "index";
	}

	@PostMapping("/registrarForm")
	public String registrarTecnico(@ModelAttribute("tecnicoDTO") TecnicoDTO tecnicoDTO, Model model) {
		RespuestaServicio respuestaServicio = new RespuestaServicio();
		List<Tecnico> tecnicos = tecnicoService.obtenerTodosLosTecnicos();
		model.addAttribute("tecnicos", tecnicos);

		try {
			if (tecnicoDTO.getIdTecnicoDTO() != null) {
				// Actualización
				respuestaServicio = tecnicoService.formTecnico(tecnicoDTO);
				model.addAttribute("mensajeExito", "Técnico actualizado con éxito.");
			} else {
				// Registro
				respuestaServicio = tecnicoService.formTecnico(tecnicoDTO);
				model.addAttribute("mensajeExito", respuestaServicio.getMensajeRespuesta());
			}
		} catch (Exception e) {
			model.addAttribute("mensajeError", "Error al registrar el técnico: " + e.getMessage());
		}
		return "index";
	}

	@PostMapping("/register-format")
	public String saveAll(@RequestParam("file") MultipartFile file, Model model) throws IOException {
		try {
			RespuestaServicio respuestaServicio = tecnicoService.registerFormat(file.getInputStream());
			return "redirect:/index?mensaje=" + respuestaServicio.getMensajeRespuesta();
		} catch (Exception e) {
			e.printStackTrace(); // Agrega un log para ver el error
			return "redirect:/index?mensaje=Hubo un error al registrar el archivo.";
		}
	}

	@GetMapping("/editar-tecnico")
	public String editarTecnico(@RequestParam("id") Integer id, Model model) {
		TecnicoDTO tecnicoDTO = tecnicoService.obtenerTecnico(id);

		if (tecnicoDTO == null) {
			model.addAttribute("mensajeError", "Técnico no encontrado.");
		} else {
			System.out.println("Fecha de Nacimiento en DTO (Controller): " + tecnicoDTO.getDateBhirthday());
			model.addAttribute("tecnicoDTO", tecnicoDTO);
		}

		List<Tecnico> tecnicos = tecnicoService.obtenerTodosLosTecnicos();
		model.addAttribute("tecnicos", tecnicos);

		return "index";
	}

	@GetMapping("/filtrar-tecnico")
	public String filtrarTecnicos(@RequestParam(required = false) String nombre,
			@RequestParam(required = false) String email, Model model) {
		// Llamada al servicio para filtrar los técnicos
		List<Tecnico> tecnicos = tecnicoService.filtrarTecnicos(nombre, email);

		// Se pasa la lista de técnicos filtrados a la vista
		model.addAttribute("tecnicos", tecnicos);

		// Aquí podrías pasar un nuevo objeto tecnicoDTO si es necesario
		model.addAttribute("tecnicoDTO", new TecnicoDTO());

		return "index"; // Nombre de la vista donde se mostrarán los resultados
	}
}