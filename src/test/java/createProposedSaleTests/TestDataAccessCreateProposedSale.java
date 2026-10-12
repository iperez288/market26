package createProposedSaleTests;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import configuration.ConfigXML;
import domain.Buyer;
import domain.ProposedSale;
import domain.Sale;

public class TestDataAccessCreateProposedSale {
	
	protected  EntityManager  db;
	protected  EntityManagerFactory emf;

	
	ConfigXML  c=ConfigXML.getInstance();
	public TestDataAccessCreateProposedSale()  {
		
		System.out.println("TestDataAccess created");

		
	}
	
	public void open(){
		String fileName=c.getDbFilename();
		if (c.isDatabaseLocal()) {
			  emf = Persistence.createEntityManagerFactory("objectdb:"+fileName);
			  db = emf.createEntityManager();
		} else {
			Map<String, String> properties = new HashMap<String, String>();
			  properties.put("javax.persistence.jdbc.user", c.getUser());
			  properties.put("javax.persistence.jdbc.password", c.getPassword());

			  emf = Persistence.createEntityManagerFactory("objectdb://"+c.getDatabaseNode()+":"+c.getDatabasePort()+"/"+fileName, properties);
			  db = emf.createEntityManager();
    	   }
		System.out.println("TestDataAccess opened");

	}
	
	public void close(){
		db.close();
		System.out.println("TestDataAccess closed");
	}
	

	public Buyer createBuyer(String email, float saldo) {
		System.out.println(">> TestDataAccess: createBuyer");

		Buyer buyer = null;
		db.getTransaction().begin();
		try {
			buyer = new Buyer(email, null, null, saldo); 
			db.persist(buyer);
			db.getTransaction().commit();
		}
		catch (Exception e){
			e.printStackTrace();
		}
		return buyer;
	}
	
	public boolean removeBuyer(String email) {
		System.out.println(">> TestDataAccess: removeBuyer");

		Buyer b = db.find(Buyer.class, email);
		if (b != null) {
			db.getTransaction().begin();
			db.remove(b);
			db.getTransaction().commit();
			return true;
		} else  return false;
		
	}
	
	public Sale createSale() {
	    System.out.println(">> DataAccessTest: createSale");
	    Sale sale = null;
	    try {
	        db.getTransaction().begin();
	        sale = new Sale("titulo test", "descripcion test", 1, 20f,
	                        new Date(), null, null);
	        db.persist(sale);
	        db.getTransaction().commit();
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	    return sale;
	}

	public boolean removeSale(Integer saleNumber) {
	    System.out.println(">> DataAccessTest: removeSale");
	    Sale sale = db.find(Sale.class, saleNumber);
	    if (sale != null) {
	        db.getTransaction().begin();
	        db.remove(sale);
	        db.getTransaction().commit();
	        return true;
	    }
	    return false;
	}
	
	public void removeProposedSale(int saleId) {
	    System.out.println(">> TestDataAccess: removeProposedSale");
	    try {
	        db.getTransaction().begin();
	        Sale sale = db.find(Sale.class, saleId);
	        if (sale != null) {
	            for (ProposedSale ps : new ArrayList<>(sale.getProposedSales())) {
	                ProposedSale managed = db.find(ProposedSale.class, ps.getID());
	                if (managed != null) {
	                    Buyer b = managed.getBuyer();
	                    if (b != null) {
	                        b.getProposedSales().remove(managed);
	                    }
	                    db.remove(managed);
	                }
	            }
	            sale.getProposedSales().clear();
	        }
	        db.getTransaction().commit();
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	}

	public Buyer getBuyer(String email) {
	    System.out.println(">> TestDataAccess: getBuyer");
	    return db.find(Buyer.class, email);
	}
	
	public Sale getSale(Integer saleNumber) {
	    System.out.println(">> TestDataAccess: getSale");
	    return db.find(Sale.class, saleNumber);
	}
	
	
	
}
