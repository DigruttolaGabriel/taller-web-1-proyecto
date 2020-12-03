package ar.edu.unlam.tallerweb1.servicios;

import com.mercadopago.exceptions.MPException;
import com.mercadopago.resources.Preference;

public interface MercadoPagoService {
	
	public Preference procesarPagoDePedido(String[] items, Float[] precios) throws MPException;
	
}