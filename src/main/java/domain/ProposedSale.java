package domain;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.ManyToOne;

@Entity @IdClass(ProposedSale.class)
public class ProposedSale implements Serializable {
	
	@Id
	@GeneratedValue
	int pSaleID;
	
	@ManyToOne(fetch=FetchType.EAGER, cascade=CascadeType.PERSIST)
	Sale sale;
	
	@ManyToOne(fetch=FetchType.EAGER, cascade=CascadeType.PERSIST)
	Buyer buyer;
	
	float price;
	
	public ProposedSale(Sale s, Buyer b, float p) {
		sale=s;
		buyer=b;
		price=p;
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

	

}
