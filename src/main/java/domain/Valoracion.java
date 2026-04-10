package domain;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

@Entity
public class Valoracion {
	
	@Id
	@GeneratedValue
	private int id;
	
	private int puntuacion; // Del 1 - 10
	private String comentario;

	@OneToOne (fetch=FetchType.EAGER, cascade=CascadeType.PERSIST)
	private ProposedSale sale;
	
	@ManyToOne (fetch=FetchType.EAGER, cascade=CascadeType.PERSIST)
	private Buyer valorador;

	
	public Valoracion() {
		
	}
	
	public Valoracion(ProposedSale ps, Buyer valorador, int rate, String comentario) {
		this.valorador=valorador;
		this.sale=ps;
		this.puntuacion=rate;
		this.comentario=comentario;	
	}
	

	public void setPuntuacion(int puntuacion) {
		this.puntuacion = puntuacion;
	}

	public void setComentario(String comentario) {
		this.comentario = comentario;
	}

	public int getPuntuacion() {
		return this.puntuacion;
	}

	public String getComentario() {
		return this.comentario;
	}

}
