package ar.edu.unlam.tallerweb1.controladores;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import ar.edu.unlam.tallerweb1.modelo.ClienteModel;
import ar.edu.unlam.tallerweb1.modelo.UsuarioModel;
import ar.edu.unlam.tallerweb1.modelo.enums.Rol;
import ar.edu.unlam.tallerweb1.modelo.form.FormularioRegistro;
import ar.edu.unlam.tallerweb1.servicios.ClienteService;
import ar.edu.unlam.tallerweb1.servicios.LoginService;
import ar.edu.unlam.tallerweb1.servicios.MailService;
import ar.edu.unlam.tallerweb1.servicios.ReservaService;
import ar.edu.unlam.tallerweb1.servicios.UsuarioRolService;

@Controller
public class ClienteController {

	@Autowired
	private ClienteService clienteService;

	@Autowired
	private LoginService loginService;
	
	@Autowired
	private MailService mailService;
	
	@Autowired 
	private UsuarioRolService usuarioRolService;
	
	@Autowired 
	private ReservaService reservaService;

	public ClienteController(ClienteService clienteService, LoginService loginService) {
		this.clienteService = clienteService;
		this.loginService = loginService;
	}
	

	public ClienteController() {
		super();
	}

	@RequestMapping(path = "/registrate")
	public ModelAndView registro() {
		ModelMap model = new ModelMap();
		FormularioRegistro formulario = new FormularioRegistro();

		model.put("formularioRegistro", formulario);

		return new ModelAndView("registrarCliente", model);
	}

	@RequestMapping(path = "/guardarRegistro", method = RequestMethod.POST)
	public ModelAndView guardarRegistro(@ModelAttribute("formularioRegistro") FormularioRegistro registro) {
		ModelMap modelo = new ModelMap();
		UsuarioModel usuario = loginService.consultarUsuarioRegistrado(registro);

		if (usuario != null) {
			modelo.put("error", "El usuario ya existe");
			return new ModelAndView("registrarCliente", modelo);
		} else {
			loginService.guardarUsuarioRegistrado(registro.getUsuarioModel());
			clienteService.guardarClienteRegistrado(registro);
			usuarioRolService.guardarUsuarioRol(registro.getUsuarioModel().getIdUsuario(), 2L);
			
			mailService.enviarMail(registro.getUsuarioModel().getEmail(),
								   mailService.getAsuntoConfirmacionRegistro(),
								   mailService.getMensajeRegistro(registro.getClienteModel().getNombre()));

			return new ModelAndView("redirect:/login");
		}

	}

	@RequestMapping(path = "/historicoPedidos")
	public ModelAndView irAHistorico(HttpServletRequest request) {
		Long rol = (Long)request.getSession().getAttribute("ROL");
		if (rol != Rol.ADMIN.getId()) 
			return new ModelAndView ("redirect:/logout");
		
		ModelMap model = new ModelMap();

		model.put("clienteModel", clienteService.buscarClientes());
		model.put("nombreUsuario", request.getSession().getAttribute("NOMBRE"));
		model.put("rol", request.getSession().getAttribute("ROL"));
		model.put("titulo", "Listado de clientes");

		return new ModelAndView("consultarHistorico", model);
	}

	@RequestMapping(path = "/consultarPedidos", method = RequestMethod.POST)
	public ModelAndView pedidos(@ModelAttribute("clienteModel") ClienteModel cliente,
								HttpServletRequest request) {
		Long rol = (Long)request.getSession().getAttribute("ROL");
		if (rol != Rol.ADMIN.getId()) 
			return new ModelAndView ("redirect:/logout");
		
		ModelMap modelo = new ModelMap();
		modelo.put("pedidoModel", clienteService.buscarPedidosClienteOrdenadosPorFecha(cliente));
		modelo.put("nombreUsuario", request.getSession().getAttribute("NOMBRE"));
		modelo.put("rol", request.getSession().getAttribute("ROL"));
		modelo.put("titulo", "Historico de pedidos");

		return new ModelAndView("pedidosPorCliente", modelo);

	}
	
	@RequestMapping(path = "/misPedidos", method = RequestMethod.GET)
	public ModelAndView misPedidos(HttpServletRequest request) {
		Long rol = (Long)request.getSession().getAttribute("ROL");
		if (rol != Rol.CLIENTE.getId()) 
			return new ModelAndView ("redirect:/logout");
		
		ClienteModel cliente = new ClienteModel((Long)request.getSession().getAttribute("id"));

		ModelMap modelo = new ModelMap();
		modelo.put("pedidoModel", clienteService.buscarPedidosClienteOrdenadosPorFecha(cliente));
		modelo.put("rol", request.getSession().getAttribute("ROL"));
		modelo.put("nombreUsuario", request.getSession().getAttribute("NOMBRE"));
		modelo.put("titulo", "Historico de pedidos");

		return new ModelAndView("pedidosPorCliente", modelo);
	}
	
	@RequestMapping(path = "/misReservas", method = RequestMethod.GET)
	public ModelAndView misReservas(HttpServletRequest request) {
		Long rol = (Long)request.getSession().getAttribute("ROL");
		if (rol != Rol.CLIENTE.getId()) 
			return new ModelAndView ("redirect:/logout");
		
		ModelMap modelo = new ModelMap();
		modelo.put("reservas", reservaService.getReservasDeCliente((Long)request.getSession().getAttribute("id")));
		modelo.put("rol", request.getSession().getAttribute("ROL"));
		modelo.put("nombreUsuario", request.getSession().getAttribute("NOMBRE"));
		modelo.put("titulo", "Mis Reservas");

		return new ModelAndView("misReservas", modelo);
	}
}
