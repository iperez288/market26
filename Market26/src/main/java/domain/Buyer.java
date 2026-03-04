package domain;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;

@Entity
public class Buyer extends User implements Serializable {

	private float saldo;
	
	@OneToMany (fetch=FetchType.EAGER, cascade=CascadeType.PERSIST)
	private List<ProposedSale> proposedSales;
	
	
	public Buyer() {
		super();
	}
	
	public Buyer(String email, String name, String password) {
		this.setEmail(email);
		this.setName(name);
		this.setPassword(password);
		this.saldo=0.0f;
		this.proposedSales=new ArrayList<ProposedSale>();
	}
	
	
	/**
	 * This method adds a propose sale to a seller
	 * 
	 * @param title of the sale
	 * @param description of the sale
	 * @param status 
	 * @param selling price
	 * @param publicationDate
	 * @return Sale
	 */
	
	public ProposedSale addProposedSale(Sale sale, float price)  {
		
		ProposedSale proposedSale= new ProposedSale(sale, this, price);
        proposedSales.add(proposedSale);
        return proposedSale;
	}
	
	
	public boolean doesSaleExist(String title)  {	
		for (ProposedSale s:proposedSales)
			if ( s.getSale().getTitle().compareTo(title)==0 )
			 return true;
		return false;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Seller other = (Seller) obj;
		if (getEmail() != other.getEmail())
			return false;
		return true;
	}
	
	
}
