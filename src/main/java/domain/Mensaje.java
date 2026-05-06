package domain;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.IdClass;



@Entity
public class Mensaje {
	
	
	@Id
	@GeneratedValue
	private long codigo;
	
	private Conversacion conversacion;

	
	private int messageNumber;
	
	private Date fechaEnvio;
	
	private String mensaje;
	
	private Buyer emisor;
	
	
	public Mensaje(){	}
	
	public Mensaje(Conversacion c, int messageNumber, Buyer emisor, Date fecha, String texto) {
		
		this.messageNumber = messageNumber;
		this.conversacion = c;
		this.emisor = emisor;
		this.fechaEnvio = fecha;
		this.mensaje = texto;
		
		
	}

	public Conversacion getConversacion() {
		return conversacion;
	}

	public void setConversacion(Conversacion conversacion) {
		this.conversacion = conversacion;
	}

	public int getMessageNumber() {
		return messageNumber;
	}

	public void setMessageNumber(int messageNumber) {
		this.messageNumber = messageNumber;
	}

	public Date getFechaEnvio() {
		return fechaEnvio;
	}

	public void setFechaEnvio(Date fechaEnvio) {
		this.fechaEnvio = fechaEnvio;
	}

	public String getMensaje() {
		return mensaje;
	}

	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}

	public Buyer getEmisor() {
		return emisor;
	}

	public void setEmisor(Buyer emisor) {
		this.emisor = emisor;
	}

	public long getCodigo() {
		return codigo;
	}

	public void setCodigo(long codigo) {
		this.codigo = codigo;
	}
	
	

	
}


