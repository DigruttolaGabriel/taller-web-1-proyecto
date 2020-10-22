package ar.edu.unlam.tallerweb1.modelo;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;

@Entity
@Table(name = "comida")
public class ComidaModel {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_comida")
	private Long idComida;
	
    @Column(name = "nombre")
    private String nombre;
    
    @Column(name = "descripcion")
    private String descripcion;
    
    @Column(name = "tipo")
	private String tipo;
	
	@ManyToOne
	@JoinColumn(name = "id_restaurante")
	private RestauranteModel restaurante;
    
    @Column(name = "precio")
	private Double precio;
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "comidaModel", cascade = CascadeType.ALL)
	private List<PedidoComidaModel> listaComidasPedidos;
	

	public ComidaModel() {
		listaComidasPedidos = new ArrayList<PedidoComidaModel>();
    }
    
//  Constructor para tests
    public ComidaModel(String nombre, Long id) {
    	this.nombre = nombre;
    	this.idComida = idComida;
    }
    
	public Long getIdComida() {
		return idComida;
	}

	public void setIdComida(Long idComida) {
		this.idComida = idComida;
	}

	public String getNombre() {
		return nombre;
	}
	
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public RestauranteModel getRestaurante() {
		return restaurante;
	}

	public void setRestaurante(RestauranteModel restaurante) {
		this.restaurante = restaurante;
	}
	public Double getPrecio() {
		return precio;
	}

	public void setPrecio(Double precio) {
		this.precio = precio;
	}
	

	
	
	
	
}
