package ar.edu.unlam.tallerweb1.servicios;

import java.util.List;
import java.util.Properties;

import javax.mail.Session;

import ar.edu.unlam.tallerweb1.modelo.ComidaModel;
import ar.edu.unlam.tallerweb1.modelo.PedidoComidaModel;

public interface MailService {
	
	public void init();
	public Boolean enviarMail(String destinatario, String asunto, String mensaje);
	public String getAsuntoConfirmacionRegistro();
	public String getMensajeRegistro(String nombreUsuario);
	public String getAsuntoConfirmacionPedido();
	public String getMensajePedido(List<PedidoComidaModel> comidas);
	public Session getSession();
	public Properties getProperties();
	
}