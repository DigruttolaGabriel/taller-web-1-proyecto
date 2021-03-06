package ar.edu.unlam.tallerweb1.repositorios;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import ar.edu.unlam.tallerweb1.modelo.ClienteModel;
import ar.edu.unlam.tallerweb1.modelo.EstadoPedidoModel;
import ar.edu.unlam.tallerweb1.modelo.PedidoModel;

@Repository
@Transactional
public class PedidoRepositoryImpl implements PedidoRepository {

	@Inject
	private SessionFactory sessionFactory;

	@Override
	public void guardarPedido(PedidoModel pedido) {
		sessionFactory.getCurrentSession().save(pedido);

	}

	@Override
	public PedidoModel consultarPedidoPorId(Long id) {
		PedidoModel pedido = sessionFactory.getCurrentSession().get(PedidoModel.class, id);
		return pedido;
	}

	@Override
	public List<PedidoModel> buscarPedido() {
		return (ArrayList<PedidoModel>) sessionFactory.getCurrentSession().createCriteria(PedidoModel.class).list();
	}

	@Override
	public List<PedidoModel> buscarPedidoPorCliente(ClienteModel cliente) {
		return sessionFactory.getCurrentSession().createCriteria(PedidoModel.class)
				.add(Restrictions.eq("clienteModel.idCliente", cliente.getIdCliente())).list();
	}

	@Override
	public List<PedidoModel> buscarPedidosClienteOrdenadosPorFecha(ClienteModel cliente) {
		return sessionFactory.getCurrentSession().createCriteria(PedidoModel.class)
				.add(Restrictions.eq("clienteModel.idCliente", cliente.getIdCliente()))
				.addOrder(Order.desc("fechaPedido"))
				.list();
	}

	@Override
	public List<PedidoModel> buscarPedidosRestauranteOrdenadosPorFecha(Long idRestaurante) {
		return sessionFactory.getCurrentSession().createCriteria(PedidoModel.class)
				.add(Restrictions.eq("restaurante.idRestaurante", idRestaurante))
				.addOrder(Order.desc("fechaPedido"))
				.list();
	}

	@Override
	public void guardarNroReferencia(Long idPedido, Long nroReferencia) {
		PedidoModel pedido = sessionFactory.getCurrentSession().get(PedidoModel.class, idPedido);
		pedido.setNroReferenciaMP(nroReferencia);
		sessionFactory.getCurrentSession().update(pedido);
	}

	@Override
	public void cambiarEstadoDePedido(Long idPedido, Long idEstadoPedido) {
		PedidoModel pedido = sessionFactory.getCurrentSession().get(PedidoModel.class, idPedido);
		EstadoPedidoModel estado = new EstadoPedidoModel(idEstadoPedido);
		
		pedido.setEstadoPedidoModel(estado);
		sessionFactory.getCurrentSession().update(pedido);
	}

	@Override
	public void generarFechaFinalizacionDe(Long idPedido) {
		PedidoModel pedido = sessionFactory.getCurrentSession().get(PedidoModel.class, idPedido);
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		Date fechaFinalizacionPedido = new Date();
		
		pedido.setFechaFinalizacionPedido(dateFormat.format(fechaFinalizacionPedido));
		sessionFactory.getCurrentSession().update(pedido);
	}

}
