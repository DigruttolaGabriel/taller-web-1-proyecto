package ar.edu.unlam.tallerweb1.controladores;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import ar.edu.unlam.tallerweb1.modelo.RestauranteModel;
import ar.edu.unlam.tallerweb1.modelo.enums.Rol;
import ar.edu.unlam.tallerweb1.modelo.HorarioModel;
import ar.edu.unlam.tallerweb1.modelo.form.FormularioCalificacionRestaurante;
import ar.edu.unlam.tallerweb1.modelo.form.FormularioRestauranteHorario;
import ar.edu.unlam.tallerweb1.servicios.CalificacionService;
import ar.edu.unlam.tallerweb1.servicios.HorarioService;
import ar.edu.unlam.tallerweb1.servicios.RestauranteHorarioService;
import ar.edu.unlam.tallerweb1.servicios.RestauranteService;

@Controller
public class RestauranteController {

	@Autowired
	private RestauranteService servRestaurante;

	@Autowired
	private RestauranteHorarioService restauranteHorarioService;

	@Autowired
	private HorarioService horarioService;

	@Autowired
	private CalificacionService servCalificacion;

	@RequestMapping("/restaurantes")
	public ModelAndView restaurantes(HttpServletRequest request) {

		ModelMap modelo = new ModelMap();

		modelo.put("titulo", "Lista de Restaurantes");
		modelo.put("RESTAURANTES", servRestaurante.buscarRestaurantes());
		modelo.put("nombreUsuario", request.getSession().getAttribute("NOMBRE"));
		modelo.put("rol", request.getSession().getAttribute("ROL"));

		return new ModelAndView("restaurantes", modelo);
	}

	@RequestMapping(path = "/agregarRestaurante", method = RequestMethod.POST)
	public ModelAndView agregarRestaurante(HttpServletRequest request) {
		Long rol = (Long)request.getSession().getAttribute("ROL");
		if (rol != Rol.ADMIN.getId()) 
			return new ModelAndView ("redirect:/logout");
		
		ModelMap modelo = new ModelMap();

		RestauranteModel restaurante = new RestauranteModel();

		modelo.put("titulo", "Agregar Restaurante");
		modelo.put("restaurante", restaurante);
		modelo.put("nombreUsuario", request.getSession().getAttribute("NOMBRE"));
		modelo.put("rol", request.getSession().getAttribute("ROL"));

		return new ModelAndView("agregarRestaurante", modelo);
	}

	@RequestMapping(path = "/validar-nuevoRestaurante", method = RequestMethod.POST)
	public ModelAndView validarNuevoRestaurante(@ModelAttribute("restaurante") RestauranteModel restaurante,
			@RequestParam("file") MultipartFile file, HttpServletRequest request) {
		Long rol = (Long)request.getSession().getAttribute("ROL");
		if (rol != Rol.ADMIN.getId()) 
			return new ModelAndView ("redirect:/logout");
		
		ModelMap modelo = new ModelMap();

		modelo.put("titulo", "Lista de Restaurantes");
		modelo.put("nombreUsuario", request.getSession().getAttribute("NOMBRE"));
		modelo.put("rol", request.getSession().getAttribute("ROL"));

		return servRestaurante.procesarNuevoRestaurante(restaurante, file, modelo);
	}

	@RequestMapping("/editarRestaurante")
	public ModelAndView editarRestaurante(@RequestParam("id") Long id, HttpServletRequest request) {
		Long rol = (Long)request.getSession().getAttribute("ROL");
		if (rol != Rol.ADMIN.getId()) 
			return new ModelAndView ("redirect:/logout");
		
		RestauranteModel restaurante = servRestaurante.buscarRestaurantePorId(id);

		ModelMap modelo = new ModelMap();

		modelo.put("titulo", "Editar " + restaurante.getNombre());
		modelo.put("restaurante", restaurante);
		modelo.put("nombreUsuario", request.getSession().getAttribute("NOMBRE"));
		modelo.put("rol", request.getSession().getAttribute("ROL"));

		return new ModelAndView("editarRestaurante", modelo);
	}

	@RequestMapping(path = "/validar-editarRestaurante", method = RequestMethod.POST)
	public ModelAndView validarEdicionRestaurante(@ModelAttribute("restaurante") RestauranteModel restaurante,
			@RequestParam("file") MultipartFile file, HttpServletRequest request) {
		Long rol = (Long)request.getSession().getAttribute("ROL");
		if (rol != Rol.ADMIN.getId()) 
			return new ModelAndView ("redirect:/logout");
		
		ModelMap modelo = new ModelMap();
		modelo.put("nombreUsuario", request.getSession().getAttribute("NOMBRE"));
		modelo.put("rol", request.getSession().getAttribute("ROL"));

		servRestaurante.procesarEdicionRestaurante(restaurante, file);

		return new ModelAndView("redirect:/restaurantes");
	}

	@RequestMapping("/eliminarRestaurante")
	public ModelAndView eliminarRestaurante(@RequestParam("id") Long id, HttpServletRequest request) throws Exception {
		Long rol = (Long)request.getSession().getAttribute("ROL");
		if (rol != Rol.ADMIN.getId()) 
			return new ModelAndView ("redirect:/logout");
		
		ModelMap modelo = new ModelMap();
		modelo.put("nombreUsuario", request.getSession().getAttribute("NOMBRE"));
		modelo.put("rol", request.getSession().getAttribute("ROL"));

		RestauranteModel restaurante = servRestaurante.buscarRestaurantePorId(id);

		servRestaurante.procesarEliminacionRestaurante(restaurante);

		return new ModelAndView("redirect:/restaurantes");
	}

	@RequestMapping(path = "/nuevo-horario", method = RequestMethod.POST)
	public ModelAndView generarNuevoHorario(@RequestParam("idRestaurante") Long idRestaurante,
			HttpServletRequest request) {
		Long rol = (Long)request.getSession().getAttribute("ROL");
		if (rol != Rol.ADMIN.getId()) 
			return new ModelAndView ("redirect:/logout");
		
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.addObject("titulo", "Generar nuevo horario");

		modelAndView.addObject("restaurante", servRestaurante.buscarRestaurantePorId(idRestaurante));
		modelAndView.addObject("horarios", horarioService.getHorariosNoAsignadosARestaurante(idRestaurante));
		modelAndView.addObject("formularioNuevoHorario", new FormularioRestauranteHorario());
		modelAndView.addObject("nombreUsuario", request.getSession().getAttribute("NOMBRE"));
		modelAndView.addObject("rol", request.getSession().getAttribute("ROL"));

		modelAndView.setViewName("generacionNuevoHorario");

		return modelAndView;
	}

	@RequestMapping(path = "/guardar-nuevo-horario", method = RequestMethod.POST)
	public ModelAndView generarNuevoHorarioPost(
			@ModelAttribute("formularioNuevoHorario") FormularioRestauranteHorario formularioRestauranteHorario,
			HttpServletRequest request) {
		Long rol = (Long)request.getSession().getAttribute("ROL");
		if (rol != Rol.ADMIN.getId()) 
			return new ModelAndView ("redirect:/logout");
		
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.addObject("titulo", "Horario guardado");

		HorarioModel horario = restauranteHorarioService.procesarNuevoHorarioRestaurante(formularioRestauranteHorario);
		modelAndView.addObject("horario", horario);
		modelAndView.addObject("nombreUsuario", request.getSession().getAttribute("NOMBRE"));
		modelAndView.addObject("rol", request.getSession().getAttribute("ROL"));
		modelAndView.setViewName("nuevoHorarioExitoso");

		return modelAndView;
	}

	@RequestMapping(path = "/nueva-calificacion", method = RequestMethod.POST)
	public ModelAndView verACalificacion(@RequestParam("idRestaurante") Long idRestaurante,
			HttpServletRequest request) {
		Long rol = (Long)request.getSession().getAttribute("ROL");
		if (rol != Rol.CLIENTE.getId()) 
			return new ModelAndView ("redirect:/logout");
		
		ModelMap model = new ModelMap();
		RestauranteModel restaurante = new RestauranteModel();
		restaurante.setIdRestaurante(idRestaurante);
		FormularioCalificacionRestaurante formulario = new FormularioCalificacionRestaurante();
		formulario.getCalificacionRestaurante().setRestauranteModel(restaurante);
		model.put("titulo", "Nueva calificacion");
		model.put("formularioCalificacion", formulario);
		model.put("nombreUsuario", request.getSession().getAttribute("NOMBRE"));
		model.put("rol", request.getSession().getAttribute("ROL"));

		return new ModelAndView("calificarRestaurante", model);
	}

	
	@RequestMapping(path = "/pedidosPorRestaurante")
	public ModelAndView pedidosPorRestaurante(@RequestParam("id") Long idRestaurante, HttpServletRequest request) {
		Long rol = (Long)request.getSession().getAttribute("ROL");
		if (rol != Rol.ADMIN.getId()) 
			return new ModelAndView ("redirect:/logout");
		
		ModelMap modelo = new ModelMap();
		RestauranteModel restaurante = servRestaurante.buscarRestaurantePorId(idRestaurante);

		modelo.put("nombreUsuario", request.getSession().getAttribute("NOMBRE"));
		modelo.put("titulo", "Pedidos " + restaurante.getNombre());
		modelo.put("nombreRestaurante", restaurante.getNombre());
		modelo.put("pedidoModel", servRestaurante.buscarPedidosRestauranteOrdenadosPorFecha(idRestaurante));
		modelo.put("rol", request.getSession().getAttribute("ROL"));

		return new ModelAndView("pedidosPorRestaurante", modelo);
	}
	
	
}
