package ar.edu.unlam.tallerweb1.servicios;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.ServletContext;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import ar.edu.unlam.tallerweb1.modelo.ComidaModel;
import ar.edu.unlam.tallerweb1.modelo.PedidoModel;
import ar.edu.unlam.tallerweb1.modelo.RestauranteModel;
import ar.edu.unlam.tallerweb1.repositorios.PedidoRepository;
import ar.edu.unlam.tallerweb1.repositorios.RestauranteRepository;

@Service("restauranteService")
@Transactional
public class RestauranteServiceImpl implements RestauranteService {

	@Autowired
	private RestauranteRepository repositorioRestaurante;

	@Autowired
	ServletContext servletContext;

	@Autowired
	private PedidoRepository pedidoRepository;

	@Autowired
	private CalificacionService calificacionService;

	@Override
	public ArrayList<RestauranteModel> buscarRestaurantes() {
		ArrayList<RestauranteModel> restaurantes = repositorioRestaurante.buscarRestaurantes();

		for (RestauranteModel restauranteModel : restaurantes)
			restauranteModel.setPromedioCalificaciones(
					calificacionService.calcularCalificacionDeRestaurante(restauranteModel.getIdRestaurante()));

		return restaurantes;
	}

	@Override
	public RestauranteModel buscarRestaurantePorId(Long id) {
		RestauranteModel restauranteModel = repositorioRestaurante.buscarRestaurantePorId(id);
		restauranteModel.setPromedioCalificaciones(calificacionService.calcularCalificacionDeRestaurante(id));
		return restauranteModel;
	}

	@Override
	public ArrayList<RestauranteModel> buscarRestaurantePorNombre(String nombre) {
		ArrayList<RestauranteModel> listaReturn = new ArrayList<>();
		ArrayList<RestauranteModel> listadoDb = repositorioRestaurante.buscarRestaurantePorNombre(nombre);

		for (RestauranteModel restauranteModel : listadoDb) {
			if (restauranteModel.getNombre().toLowerCase().contains(nombre.toLowerCase())) {
				restauranteModel.setPromedioCalificaciones(
						calificacionService.calcularCalificacionDeRestaurante(restauranteModel.getIdRestaurante()));
				listaReturn.add(restauranteModel);
			}
		}

		return listaReturn;
	}


	@Override
	public List<ComidaModel> buscarMenuPorRestaurante(RestauranteModel restaurante) {
		List<ComidaModel> listaReturn = new LinkedList<>();
		List<ComidaModel> listadoDB = repositorioRestaurante.buscarMenuPorRestaurante(restaurante);
		for (ComidaModel comidaModel : listadoDB) {
			comidaModel.setPromedioCalificaciones(calificacionService.calcularCalificacionDeComida(comidaModel.getIdComida()));
			listaReturn.add(comidaModel);

		}

		return listaReturn;
	}

	@Override
	public RestauranteModel buscarRestaurantePorDireccion(String direccion) {
		String direccionToLower = direccion.toLowerCase();
		RestauranteModel restauranteModel = repositorioRestaurante.buscarRestaurantePorDireccion(direccionToLower);
		restauranteModel.setPromedioCalificaciones(
				calificacionService.calcularCalificacionDeRestaurante(restauranteModel.getIdRestaurante()));
		return restauranteModel;
	}

	@Override
	public Boolean validarRestaurante(RestauranteModel restaurante) {
		Boolean valido = false;

		if (repositorioRestaurante.buscarRestaurantePorDireccion(restaurante.getDireccion()) == null)
			valido = true;

		return valido;
	}

	@Override
	public void guardarRestaurante(RestauranteModel restaurante) {
		repositorioRestaurante.guardarRestaurante(restaurante);
	}

	@Override
	public void editarRestaurante(RestauranteModel restaurante) {
		repositorioRestaurante.editarRestaurante(restaurante);
	}

	@Override
	public void eliminarRestaurante(RestauranteModel restaurante) {
		repositorioRestaurante.eliminarRestaurante(restaurante);
	}

	@Override
	public ModelAndView procesarNuevoRestaurante(RestauranteModel restaurante, MultipartFile imagen, ModelMap modelo) {
		if (this.validarRestaurante(restaurante)) {

			this.subirImagenSiNoEstaVacia(restaurante, imagen);
			this.guardarRestaurante(restaurante);

			return new ModelAndView("redirect:/restaurantes");

		} else {
			modelo.put("errorValidacion",
					"La direccion del restaurante ya se encuentra " + "en la base de datos, contacte al administrador");
		}

		return new ModelAndView("agregarRestaurante", modelo);
	}

	@Override
	public void procesarEdicionRestaurante(RestauranteModel restaurante, MultipartFile imagen) {
		this.reemplazarImagenRestauranteSiNuevaImagenNoEstaVacia(restaurante, imagen);
		this.editarRestaurante(restaurante);
	}

	@Override
	public void procesarEliminacionRestaurante(RestauranteModel restaurante) {
		this.eliminarRestaurante(restaurante);
		this.eliminarImagenRestauranteSiExiste(restaurante);
	}

	@Override
	public void subirImagenRestaurante(MultipartFile imagen) {
		String fileName = servletContext.getRealPath("/") + "\\img\\restaurantes\\" + imagen.getOriginalFilename();

		try {
			imagen.transferTo(new File(fileName));
		} catch (IllegalStateException | IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void subirImagenSiNoEstaVacia(RestauranteModel restaurante, MultipartFile imagen) {
		if (!imagen.isEmpty()) {
			if (this.verificarExtensionDeImagen(imagen)) {
				this.subirImagenRestaurante(imagen);
				restaurante.setImageName(imagen.getOriginalFilename());
			}
		}
	}

	@Override
	public void eliminarImagenRestauranteSiExiste(RestauranteModel restaurante) {
		try {
			if (!restaurante.getImageName().isEmpty()) {
				String fileName = servletContext.getRealPath("/") + "\\img\\restaurantes\\"
						+ restaurante.getImageName();

				File imagen = new File(fileName);

				imagen.delete();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void reemplazarImagenRestauranteSiNuevaImagenNoEstaVacia(RestauranteModel restaurante,
			MultipartFile imagen) {
		if (!imagen.isEmpty()) {
			if (this.verificarExtensionDeImagen(imagen)) {
				this.eliminarImagenRestauranteSiExiste(restaurante);
				this.subirImagenRestaurante(imagen);
				restaurante.setImageName(imagen.getOriginalFilename());
			}
		}
	}

	@Override
	public Boolean verificarExtensionDeImagen(MultipartFile imagen) {
		if (imagen.getContentType().equals("image/png") || imagen.getContentType().equals("image/jpeg"))
			return true;

		return false;
	}

	@Override
	public List<PedidoModel> buscarPedidosRestauranteOrdenadosPorFecha(Long idRestaurante) {
		return pedidoRepository.buscarPedidosRestauranteOrdenadosPorFecha(idRestaurante);
	}

}
