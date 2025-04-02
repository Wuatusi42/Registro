	package com.indexia.TecnicosRegistrar.Controllers;
	
	import com.indexia.TecnicosRegistrar.Service.CodigoService;
	import com.indexia.TecnicosRegistrar.Service.TecnicoService;
	import com.indexia.TecnicosRegistrar.model.Entity.Tecnico;
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
	    private static final Logger logger = LoggerFactory.getLogger(IndexController.class);
	    
	    @GetMapping("/index")
	    public String mostrarForm(Model model, HttpSession session) {
	        logger.info("Iniciando el método obtenerRegistros...");
	        try {
		        List<Tecnico> tecnicos = (List<Tecnico>) session.getAttribute("tecnicos");
		        if (tecnicos == null) { // Si la lista no está en sesión, cargarla desde la BD
		            tecnicos = tecnicoService.obtenerTodosLosTecnicos();
		            session.setAttribute("tecnicos", tecnicos);
		        }
		
		        model.addAttribute("tecnicoDTO", new TecnicoDTO());
		        model.addAttribute("infoDetail", new DetalleDeInfoDTO());
		        model.addAttribute("tecnicos", tecnicos);
		        
	        	logger.info("Se recuperaron {} registros técnicos", tecnicos.size());

		        return "index";
	        }catch (Exception e) {
				// TODO: handle exception	
	            logger.error("Error al obtener registros técnicos: {}", e.getMessage(), e);
	            model.addAttribute("infoDetail", new DetalleDeInfoDTO());
	            model.addAttribute("tecnicoDTO", new TecnicoDTO());
	        	return "index";
			}
	
	        
	    }
	
	    @PostMapping("/registrarForm")
	    public String registrarTecnico(@ModelAttribute("tecnicoDTO") TecnicoDTO tecnicoDTO, Model model, HttpSession session) {
	       
	    	RespuestaServicio respuestaServicio= new RespuestaServicio();
	        String username = (String) session.getAttribute("username");
	        logger.info("Nombre de usuario de la sesión: {}", username);
	        try {
	            if (tecnicoDTO.getIdTecnicoDTO() != null) {
	                // Actualización
	    	        logger.info("Iniciando el método de actualizacion...");

	                respuestaServicio = tecnicoService.formTecnico(tecnicoDTO,username);
	                model.addAttribute("mensajeExito", respuestaServicio.getMensajeRespuesta());
	                model.addAttribute("infoDetail", new DetalleDeInfoDTO());
	            } else {
	                // Registro
	    	        logger.info("Iniciando el método de registro...");

	                respuestaServicio = tecnicoService.formTecnico(tecnicoDTO,username);
	                model.addAttribute("mensajeExito", respuestaServicio.getMensajeRespuesta());
	            }
	
	            // Actualizar la lista en la sesión después de registrar un técnico
	            List<Tecnico> tecnicosActualizados = tecnicoService.obtenerTodosLosTecnicos();
	            session.setAttribute("tecnicos", tecnicosActualizados);
	            model.addAttribute("infoDetail", new DetalleDeInfoDTO());
	            model.addAttribute("tecnicos", tecnicosActualizados);
	
	        } catch (Exception e) {
	            logger.error("Error al registrar técnico: {}", e.getMessage(), e);
	            model.addAttribute("infoDetail", new DetalleDeInfoDTO());
	            model.addAttribute("mensajeError", "Error al registrar el técnico: " + e.getMessage());
	        }
	        logger.info("Respuesta el registrarTecnico :"+ respuestaServicio);
	        return "index";
	    }
	
	    @PostMapping("/register-format")
	    public String saveAll(@RequestParam("file") MultipartFile file, Model model, HttpSession session) throws IOException {
	        try {
    	        logger.info("Iniciando el método de register-format...");

	            RespuestaServicio respuestaServicio = tecnicoService.registerFormat(file.getInputStream());
	
	            // Actualizar la lista en la sesión después de importar técnicos
	            List<Tecnico> tecnicosActualizados = tecnicoService.obtenerTodosLosTecnicos();
	            session.setAttribute("tecnicos", tecnicosActualizados);
    	        logger.info("Respuesta  numTecnicosActualizados: "+tecnicosActualizados.size());
	            return "redirect:/index?mensaje=" + respuestaServicio.getMensajeRespuesta();
	        } catch (Exception e) {
	            e.printStackTrace();
	            logger.error(e.getLocalizedMessage());
	            return "redirect:/index?mensaje=Hubo un error al registrar el archivo.";
	        }
	    }
	
	    @GetMapping("/editar-tecnico")
	    public String editarTecnico(@RequestParam("id") Integer id, Model model, HttpSession session) {
	        TecnicoDTO tecnicoDTO = tecnicoService.obtenerTecnico(id);
	        DetalleDeInfoDTO detalleDeInfoDTO = codigoActivacionService.detalleDeInfo(id);
	        logger.info("Iniciando el método de editar-tecnico...");

	        if (tecnicoDTO == null) {
	            model.addAttribute("mensajeError", "Técnico no encontrado.");
	        } else {
	            model.addAttribute("tecnicoDTO", tecnicoDTO);
	            model.addAttribute("infoDetail", detalleDeInfoDTO); // Agregar DetailInfo al modelo
	        }

	        List<Tecnico> tecnicos = (List<Tecnico>) session.getAttribute("tecnicos");
	        if (tecnicos == null) {
	            tecnicos = tecnicoService.obtenerTodosLosTecnicos();
	            session.setAttribute("tecnicos", tecnicos);
	        }
	        logger.info("Respuesta de editarTecnico : "+tecnicos.size());


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
	        model.addAttribute("infoDetail", new DetalleDeInfoDTO());
	        model.addAttribute("tecnicos", tecnicos);
	
	        return infoDetail;
	    }
	    
	
	    @GetMapping("/limpiar-sesion")
	    public String limpiarSesion(HttpSession session) {
	        session.removeAttribute("tecnicos"); // Borra solo la lista de técnicos
	        return "redirect:/index";
	    }
	}