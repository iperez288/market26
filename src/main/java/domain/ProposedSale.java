package domain;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import javax.persistence.ManyToOne;
import java.util.Date;

@Entity 
public class ProposedSale implements Serializable {
	
	@Id
	@GeneratedValue
	int pSaleID;
	
	@ManyToOne(fetch=FetchType.EAGER, cascade=CascadeType.PERSIST)
	Sale sale;
	
	@ManyToOne(fetch=FetchType.EAGER, cascade=CascadeType.PERSIST)
	Buyer buyer;
	
	Valoracion valoracion;
	
	float price;
	private Date fechaCompra;
	
	public ProposedSale(Sale s, Buyer b, float p) {
		sale=s;
		buyer=b;
		price=p;
		valoracion=null;
	}
	
	public ProposedSale() {
		super();
	}

	public Sale getSale() {
		return sale;
	}

	public void setSale(Sale sale) {
		this.sale = sale;
	}

	public Buyer getBuyer() {
		return buyer;
	}

	public void setBuyer(Buyer buyer) {
		this.buyer = buyer;
	}

	public float getPrice() {
		return price;
	}

	public void setPrice(float price) {
		this.price = price;
	}

	
	/**
	 * Gets the title of sale.
	 * 
	 * @return
	 */
	public String getTitle() {
		
		return sale.getTitle();
	}

	public void setFechaCompra(Date fecha) {
		this.fechaCompra=fecha;
	}
	
	public Date getFechaCompra(){
		return this.fechaCompra;
	}

	public void setValoracion(Valoracion val) {
		this.valoracion=val;
		
	}

	public int getID() {
		
		return this.pSaleID;
	}
	

}
