package domain;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;



@Entity
@IdClass(IdMensaje.class)
public class Mensaje {
	
	@Id
	private Conversacion conversacion;

	@Id
	private int messageNumber;
	
	private LocalDateTime fechaEnvio;
	
	private String mensaje;
	
	private Buyer emisor;
	
	
	public Mensaje(){	}
	
	public Mensaje(Conversacion c, int messageNumber, Buyer emisor, LocalDateTime fecha, String texto) {
		
		this.messageNumber = messageNumber;
		this.conversacion = c;
		this.emisor = emisor;
		this.fechaEnvio = fecha;
		this.mensaje = texto;
		
		c.addMensaje(this);
		emisor.addMensaje(this);
		
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

	public LocalDateTime getFechaEnvio() {
		return fechaEnvio;
	}

	public void setFechaEnvio(LocalDateTime fechaEnvio) {
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
	

	
}


