package domain;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.ManyToOne;

@Entity @IdClass(ProposedSale.class)
public class ProposedSale {
	
	@Id @ManyToOne
	Sale sale;
	
	@Id @ManyToOne
	Buyer buyer;
	
	float price;
	


}
