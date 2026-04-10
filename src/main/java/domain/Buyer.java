package domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;

@Entity
public class Buyer extends User implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private float saldo;
	
	@OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, mappedBy="usuario")
	private List<Transaction> monedero;

	@OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
	private List<ProposedSale> proposedSales;

	public List<ProposedSale> getProposedSales() {
		return proposedSales;
	}

	public void setProposedSales(List<ProposedSale> proposedSales) {
		this.proposedSales = proposedSales;
	}

	public Buyer() {
		super();
		this.proposedSales = new ArrayList<ProposedSale>();
		this.monedero = new ArrayList<Transaction>();
	}

	public Buyer(String email, String name, String password) {
		this.setEmail(email);
		this.setName(name);
		this.setPassword(password);
		// this.saldo=0.0f;
		this.proposedSales = new ArrayList<ProposedSale>();
		this.monedero = new ArrayList<Transaction>();
	}

	/**
	 * This method adds a propose sale to a seller
	 * 
	 * @param title           of the sale
	 * @param description     of the sale
	 * @param status
	 * @param selling         price
	 * @param publicationDate
	 * @return Sale
	 */

	public ProposedSale addProposedSale(Sale sale, float price) {

		ProposedSale proposedSale = new ProposedSale(sale, this, price);
		proposedSales.add(proposedSale);
		sale.addProposedSale(proposedSale);
		return proposedSale;
	}

	public boolean doesSaleExist(String title) {
		for (ProposedSale s : proposedSales)
			if (s.getSale().getTitle().compareTo(title) == 0)
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

	public float getSaldo() {
		return saldo;
	}

	public void setSaldo(float saldo) {
		this.saldo = saldo;
	}
	
	public boolean addProposedSale(ProposedSale ps) {

		return this.proposedSales.add(ps);
	}
	
	public List<Transaction> getTransactions(){
		return this.monedero;
	}
	
	public void addTransaction(Transaction transaction) {
		this.monedero.add(transaction);
	}

	public void addValoracion(Valoracion val) {
		// TODO Auto-generated method stub
		
	}

}
