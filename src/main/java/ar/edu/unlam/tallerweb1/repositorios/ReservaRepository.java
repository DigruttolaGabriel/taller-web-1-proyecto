package ar.edu.unlam.tallerweb1.repositorios;

import ar.edu.unlam.tallerweb1.modelo.ReservaModel;
import ar.edu.unlam.tallerweb1.modelo.RestauranteHorarioModel;
import ar.edu.unlam.tallerweb1.modelo.form.FormularioGeneracionReserva;

import java.util.List;

public interface ReservaRepository {
	
	void guardarReserva(ReservaModel reservaModel);
	
	ReservaModel getReservaByIdReserva(Long idReserva);
	
	List<RestauranteHorarioModel> getHorariosDisponiblesParaReservaDeMesa(FormularioGeneracionReserva formularioGeneracionReserva);

	List<ReservaModel> getReservasByClienteOrderByFechaDescendiente(Long idCliente);
	
	List<ReservaModel> getReservasByIdRestauranteAndIdEstado(Long idRestaurante, Long idEstadoReserva);
	
	void updateEstadoReserva(Long idReserva, Long idEstadoReserva);
}
