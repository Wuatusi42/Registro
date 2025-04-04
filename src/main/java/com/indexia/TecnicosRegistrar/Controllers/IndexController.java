package com.indexia.TecnicosRegistrar.Controllers;

import com.indexia.TecnicosRegistrar.Service.CodigoService;
import com.indexia.TecnicosRegistrar.Service.TecnicoService;
import com.indexia.TecnicosRegistrar.model.Entity.Tecnico;
import com.indexia.TecnicosRegistrar.model.Entity.Usuarios;
import com.indexia.TecnicosRegistrar.model.Repository.UsuariosDAO;
import com.indexia.TecnicosRegistrar.model.utils.DetalleDeInfoDTO;
import com.indexia.TecnicosRegistrar.model.utils.RespuestaServicio;
import com.indexia.TecnicosRegistrar.model.utils.TecnicoDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    
    @Autowired
    private UsuariosDAO usuariosDAO;
    private static final Logger logger = LoggerFactory.getLogger(IndexController.class);

    @GetMapping("/index")
    public String mostrarForm(Model model, HttpSession session) {
        // *CAMBIO:* Asegurar que siempre se agregue un nuevo TecnicoDTO al modelo al cargar la página.
        model.addAttribute("tecnicoDTO", new TecnicoDTO());
        model.addAttribute("infoDetail", new DetalleDeInfoDTO());
        // *CAMBIO:* Cargar la lista de técnicos directamente del servicio en cada petición.
        List<Tecnico> tecnicos = tecnicoService.obtenerTodosLosTecnicos();
        model.addAttribute("tecnicos", tecnicos);
        return "index";
    }

    @PostMapping("/registrarForm")
    public String registrarTecnico(@ModelAttribute("tecnicoDTO") TecnicoDTO tecnicoDTO, Model model, HttpSession session) {
        RespuestaServicio respuestaServicio;
        Usuarios usernameC = (Usuarios) session.getAttribute("usernameC");
        String username = (String) session.getAttribute("username");
        logger.info("Nombre de usuario de la sesión: {}", username);
        try {
        	Usuarios usuario = usuariosDAO.findByNombreUsuario(username);
            respuestaServicio = tecnicoService.formTecnico(tecnicoDTO, usernameC);
            model.addAttribute("mensajeExito", respuestaServicio.getMensajeRespuesta());
            // *CAMBIO:* Limpiar el formulario creando un nuevo TecnicoDTO después del éxito.
            model.addAttribute("tecnicoDTO", new TecnicoDTO());
        } catch (Exception e) {
            model.addAttribute("mensajeError", "Error al registrar el técnico: " + e.getMessage());
            // *CAMBIO:* Mantener los datos en el formulario en caso de error.
            model.addAttribute("tecnicoDTO", tecnicoDTO);
        }
        model.addAttribute("infoDetail", new DetalleDeInfoDTO());
        // *CAMBIO:* Cargar la lista de técnicos directamente del servicio después de la operación.
        List<Tecnico> tecnicos = tecnicoService.obtenerTodosLosTecnicos();
        model.addAttribute("tecnicos", tecnicos);
        return "index";
    }

    @PostMapping("/register-format")
    public String saveAll(@RequestParam("file") MultipartFile file, Model model, HttpSession session) throws IOException {
        try {
            RespuestaServicio respuestaServicio = tecnicoService.registerFormat(file.getInputStream());
            model.addAttribute("mensajeExito", respuestaServicio.getMensajeRespuesta());
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("mensajeError", "Hubo un error al registrar el archivo: " + e.getMessage());
        }
        // *CAMBIO:* Asegurar que siempre se agregue un nuevo TecnicoDTO al modelo.
        model.addAttribute("tecnicoDTO", new TecnicoDTO());
        model.addAttribute("infoDetail", new DetalleDeInfoDTO());
        // *CAMBIO:* Cargar la lista de técnicos directamente del servicio después de la operación.
        List<Tecnico> tecnicos = tecnicoService.obtenerTodosLosTecnicos();
        session.setAttribute("tecnicos", tecnicos); // Si deseas mantener la lista en sesión, actualízala aquí
        return "redirect:/index";
    }

    @GetMapping("/editar-tecnico")
    public String editarTecnico(@RequestParam("id") Integer id, Model model, HttpSession session) {
        TecnicoDTO tecnicoDTO = tecnicoService.obtenerTecnico(id);
        DetalleDeInfoDTO detalleDeInfoDTO = codigoActivacionService.detalleDeInfo(id);

        if (tecnicoDTO == null) {
            model.addAttribute("mensajeError", "Técnico no encontrado.");
            // *CAMBIO:* Asegurar que siempre se agregue un nuevo TecnicoDTO al modelo.
            model.addAttribute("tecnicoDTO", new TecnicoDTO());
        } else {
            model.addAttribute("tecnicoDTO", tecnicoDTO);
            model.addAttribute("infoDetail", detalleDeInfoDTO); // Agregar DetailInfo al modelo
        }

        // *CAMBIO:* Cargar la lista de técnicos directamente del servicio.
        List<Tecnico> tecnicos = tecnicoService.obtenerTodosLosTecnicos();
        model.addAttribute("tecnicos", tecnicos);
        return "index";
    }

    @GetMapping("/filtrar-tecnico")
    public String filtrarTecnicos(@RequestParam(required = false) String nombre,
                                  @RequestParam(required = false) String email,
                                  Model model, HttpSession session) {
        List<Tecnico> tecnicos = tecnicoService.filtrarTecnicos(nombre, email);

        model.addAttribute("tecnicos", tecnicos);
        // *CAMBIO:* Asegurar que siempre se agregue un nuevo TecnicoDTO al modelo.
        model.addAttribute("tecnicoDTO", new TecnicoDTO());
        model.addAttribute("infoDetail", new DetalleDeInfoDTO());
        // *CAMBIO:* Cargar la lista de técnicos directamente del servicio (aunque ya esté filtrada).
        // Si la lógica de filtrado ya actualiza la lista 'tecnicos', no es estrictamente necesario recargarla.
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

        // *CAMBIO:* Asegurar que siempre se agregue un nuevo TecnicoDTO al modelo.
        model.addAttribute("tecnicoDTO", new TecnicoDTO());
        model.addAttribute("infoDetail", new DetalleDeInfoDTO());
        // *CAMBIO:* Cargar la lista de técnicos directamente del servicio.
        List<Tecnico> tecnicos = tecnicoService.obtenerTodosLosTecnicos();
        model.addAttribute("tecnicos", tecnicos);

        return infoDetail;
    }

    @GetMapping("/limpiar-sesion")
    public String limpiarSesion(HttpSession session, Model model) {
        session.removeAttribute("tecnicos"); // Borra solo la lista de técnicos
        // *CAMBIO:* Asegurar que siempre se agregue un nuevo TecnicoDTO al modelo al redirigir.
        model.addAttribute("tecnicoDTO", new TecnicoDTO());
        model.addAttribute("infoDetail", new DetalleDeInfoDTO());
        // *CAMBIO:* Cargar la lista de técnicos directamente del servicio.
        List<Tecnico> tecnicos = tecnicoService.obtenerTodosLosTecnicos();
        model.addAttribute("tecnicos", tecnicos);
        return "redirect:/index";
    }
}