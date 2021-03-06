package ar.edu.unlam.tallerweb1.servicios;

import java.util.List;

import ar.edu.unlam.tallerweb1.modelo.CalificacionComidaModel;
import ar.edu.unlam.tallerweb1.modelo.CalificacionRestauranteModel;
import ar.edu.unlam.tallerweb1.modelo.RestauranteModel;
import ar.edu.unlam.tallerweb1.modelo.form.FormularioCalificacionComida;
import ar.edu.unlam.tallerweb1.modelo.form.FormularioCalificacionRestaurante;

public interface CalificacionService {
	

	List<CalificacionRestauranteModel> buscarCalificaciones();

	void guardarCalificaciones(FormularioCalificacionRestaurante calificacion);

	public List <CalificacionRestauranteModel> buscarCalificacionDelRestaurante(RestauranteModel restaurante);
	
	Integer calcularCalificacionDeRestaurante(Long idRestaurante);

	List<CalificacionRestauranteModel> buscarCalificacionPorRestaurante(Long idRestaurante);

	void guardarCalificacionesComidas(FormularioCalificacionComida formularioCalificacionComida);

	List<CalificacionComidaModel> buscarCalificacionesComidas();

	Integer calcularCalificacionDeComida(Long idComida);

	
}
