package CreateMensajeTests;


import java.util.HashMap;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import configuration.ConfigXML;
import domain.Buyer;
import domain.Conversacion;

public class TestDataAccessCrearMensaje {
	protected  EntityManager  db;
	protected  EntityManagerFactory emf;

	//Clase inspirada y adaptada del laboratorio 1.3 (JUnit + Mockito)
	
	ConfigXML  c=ConfigXML.getInstance();
	public TestDataAccessCrearMensaje()  {
		
		System.out.println("TestDataAccess created");

		//open();
		
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

	public Buyer createBuyer(String email, String name) {
		System.out.println(">> TestDataAccess: createBuyer");

		Buyer buyer = null;
		db.getTransaction().begin();
		try {
			buyer = new Buyer(email, name, "1234"); 
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
	public Conversacion createConversacion(String tema, String buyerEmail) {
		System.out.println(">> TestDataAccess: createConversacion");

		Conversacion c = null;
		db.getTransaction().begin();
		try {
			Buyer b = db.find(Buyer.class, buyerEmail);
			c = new Conversacion(tema, null, b); 
			db.persist(c);
			db.getTransaction().commit();
		}
		catch (Exception e){
			e.printStackTrace();
		}
		return c;
	}

	public boolean removeConversacion(long codigo) {
		System.out.println(">> TestDataAccess: removeConversacion");

		Conversacion c = db.find(Conversacion.class, codigo);
		if (c != null) {
			db.getTransaction().begin();
			db.remove(c);
			db.getTransaction().commit();
			return true;
		} else return false;
	}	
}