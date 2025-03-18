package com.indexia.TecnicosRegistrar.Controllers;

import com.indexia.TecnicosRegistrar.Service.CodigoService;
import com.indexia.TecnicosRegistrar.Service.TecnicoService;
import com.indexia.TecnicosRegistrar.model.Entity.Tecnico;
import com.indexia.TecnicosRegistrar.model.utils.DetalleDeInfoDTO;
import com.indexia.TecnicosRegistrar.model.utils.RespuestaServicio;
import com.indexia.TecnicosRegistrar.model.utils.TecnicoDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpSession;

@Controller
public class IndexController {

    @Autowired
    private TecnicoService tecnicoService;

    @Autowired
    private CodigoService codigoActivacionService;

    @GetMapping("/index")
    public String mostrarForm(Model model, HttpSession session) {
        List<Tecnico> tecnicos = (List<Tecnico>) session.getAttribute("tecnicos");

        if (tecnicos == null) { // Si la lista no está en sesión, cargarla desde la BD
            tecnicos = tecnicoService.obtenerTodosLosTecnicos();
            session.setAttribute("tecnicos", tecnicos);
        }

        model.addAttribute("tecnicoDTO", new TecnicoDTO());
        model.addAttribute("infoDetail", new DetalleDeInfoDTO());
        model.addAttribute("tecnicos", tecnicos);

        return "index";
    }

    @PostMapping("/registrarForm")
    public String registrarTecnico(@ModelAttribute("tecnicoDTO") TecnicoDTO tecnicoDTO, Model model, HttpSession session) {
        RespuestaServicio respuestaServicio;

        try {
            if (tecnicoDTO.getIdTecnicoDTO() != null) {
                // Actualización
                respuestaServicio = tecnicoService.formTecnico(tecnicoDTO);
                model.addAttribute("mensajeExito", respuestaServicio.getMensajeRespuesta());
            } else {
                // Registro
                respuestaServicio = tecnicoService.formTecnico(tecnicoDTO);
                model.addAttribute("mensajeExito", respuestaServicio.getMensajeRespuesta());
            }

            // Actualizar la lista en la sesión después de registrar un técnico
            List<Tecnico> tecnicosActualizados = tecnicoService.obtenerTodosLosTecnicos();
            session.setAttribute("tecnicos", tecnicosActualizados);
            model.addAttribute("tecnicos", tecnicosActualizados);

        } catch (Exception e) {
            model.addAttribute("mensajeError", "Error al registrar el técnico: " + e.getMessage());
        }

        return "index";
    }

    @PostMapping("/register-format")
    public String saveAll(@RequestParam("file") MultipartFile file, Model model, HttpSession session) throws IOException {
        try {
            RespuestaServicio respuestaServicio = tecnicoService.registerFormat(file.getInputStream());

            // Actualizar la lista en la sesión después de importar técnicos
            List<Tecnico> tecnicosActualizados = tecnicoService.obtenerTodosLosTecnicos();
            session.setAttribute("tecnicos", tecnicosActualizados);

            return "redirect:/index?mensaje=" + respuestaServicio.getMensajeRespuesta();
        } catch (Exception e) {
            e.printStackTrace();
            return "redirect:/index?mensaje=Hubo un error al registrar el archivo.";
        }
    }

    @GetMapping("/editar-tecnico")
    public String editarTecnico(@RequestParam("id") Integer id, Model model, HttpSession session) {
        TecnicoDTO tecnicoDTO = tecnicoService.obtenerTecnico(id);

        if (tecnicoDTO == null) {
            model.addAttribute("mensajeError", "Técnico no encontrado.");
        } else {
            System.out.println("Fecha de Nacimiento en DTO (Controller): " + tecnicoDTO.getDateBhirthday());
            model.addAttribute("tecnicoDTO", tecnicoDTO);
        }

        List<Tecnico> tecnicos = (List<Tecnico>) session.getAttribute("tecnicos");
        if (tecnicos == null) {
            tecnicos = tecnicoService.obtenerTodosLosTecnicos();
            session.setAttribute("tecnicos", tecnicos);
        }

        model.addAttribute("tecnicos", tecnicos);
        return "index";
    }

    @GetMapping("/filtrar-tecnico")
    public String filtrarTecnicos(@RequestParam(required = false) String nombre,
                                  @RequestParam(required = false) String email,
                                  Model model, HttpSession session) {
        List<Tecnico> tecnicos = tecnicoService.filtrarTecnicos(nombre, email);

        model.addAttribute("tecnicos", tecnicos);
        model.addAttribute("tecnicoDTO", new TecnicoDTO());

        // Opcional: actualizar la lista en la sesión con los técnicos filtrados
        session.setAttribute("tecnicos", tecnicos);

        return "index";
    }

    @GetMapping("/getDetailInfo")
    @ResponseBody
    public DetalleDeInfoDTO getDetailInfo(@RequestParam Integer idtecnico, Model model, HttpSession session) {
        System.out.println("idTecnico: " + idtecnico);

        DetalleDeInfoDTO infoDetail = codigoActivacionService.detalleDeInfo(idtecnico);
        if (infoDetail == null) {
            model.addAttribute("mensaje", "No se encontraron detalles para este técnico.");
            System.out.println("infoDetail: " + "No se encontraron detalles para este técnico.");

        } else {
            model.addAttribute("infoDetail", infoDetail);
            System.out.println("infoDetail: " + infoDetail.toString());
        }

        // Mantener la lista de técnicos en la sesión
        List<Tecnico> tecnicos = (List<Tecnico>) session.getAttribute("tecnicos");
        if (tecnicos == null) {
            tecnicos = tecnicoService.obtenerTodosLosTecnicos();
            session.setAttribute("tecnicos", tecnicos);
            System.out.println("recargando tecnicos via service...: ");
        }
        model.addAttribute("tecnicoDTO", new TecnicoDTO());
        model.addAttribute("tecnicos", tecnicos);

        return infoDetail;
    }
    

    @GetMapping("/limpiar-sesion")
    public String limpiarSesion(HttpSession session) {
        session.removeAttribute("tecnicos"); // Borra solo la lista de técnicos
        return "redirect:/index";
    }
}