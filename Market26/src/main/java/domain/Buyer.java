package domain;

import java.util.List;

import javax.persistence.OneToMany;

public class Buyer extends User{

	private float saldo;
	@OneToMany
	List<ProposedSale> proposedSales;
	
	
}
