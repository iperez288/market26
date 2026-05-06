package domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class Conversacion   {

	@Id
	@GeneratedValue
	private long codigo;
	
	private String tema;
	
	/**
	 * EstadoConversacion representa el estado de una conversación.
	 */
	public enum EstadoConversacion{
		/**
		 * El último en escribir ha sido un Buyer, y el seller del producto todavía no 
		 * lo ha leído
		 */
		PREGUNTADA, 
		/**
		 * El último en escribir ha sido un Seller, y el buyer todavía no 
		 * lo ha leído
		 */
		RESPONDIDA, 
		/**
		 *Ambos implicados (buyer y seller) lo han leído, y se está esperando una respuesta o una nueva pregunta
		 */
		ESPERA,

		/**
		 * La venta del producto ya se ha realizado
		 */
		FINALIZADA
	}
	
	private EstadoConversacion estado;
	
	@OneToMany (fetch=FetchType.EAGER, cascade = CascadeType.PERSIST)
	private List<Mensaje> mensajes;
	
	private Sale producto;
	
	private Buyer iniciador;
	
	private int nMensajes; //Número de mensajes
	
	public Conversacion() {
		this.mensajes = new ArrayList<Mensaje>();
		this.nMensajes=0;
	}
	
	public Conversacion(String tema, Sale producto, Buyer iniciador) {
		this();
		this.tema=tema;
		this.producto = producto;
		this.iniciador=iniciador;
		this.estado= EstadoConversacion.PREGUNTADA;
	}

	public long getCodigo() {
		return codigo;
	}

	public void setCodigo(long codigo) {
		this.codigo = codigo;
	}

	public String getTema() {
		return tema;
	}

	public void setTema(String tema) {
		this.tema = tema;
	}

	public EstadoConversacion getEstado() {
		return estado;
	}

	public void setEstado(EstadoConversacion estado) {
		this.estado = estado;
	}

	public List<Mensaje> getMensajes() {
		return mensajes;
	}

	public void setMensajes(List<Mensaje> mensajes) {
		this.mensajes = mensajes;
	}

	public Sale getProducto() {
		return producto;
	}

	public void setProducto(Sale producto) {
		this.producto = producto;
	}

	public Buyer getIniciador() {
		return iniciador;
	}

	public void setIniciador(Buyer iniciador) {
		this.iniciador = iniciador;
	}

	public int getCantidadMensajes() {
		return nMensajes;
	}

	public void setnMensajes(int nMensajes) {
		this.nMensajes = nMensajes;
	}
	
	public void addMensaje(Mensaje msg) {
		this.mensajes.add(msg);
		this.nMensajes++;
	}
	
	
	
}
