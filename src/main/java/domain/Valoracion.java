package domain;

public class Valoracion {

	private int puntuacion; //Del 1 - 10
	private String comentario;
	
	public Valoracion() {}
	
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
