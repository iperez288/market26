package dataAccess;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import javax.imageio.ImageIO;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

import configuration.ConfigXML;
import configuration.UtilDate;
import domain.Seller;
import domain.User;
import domain.Buyer;
import domain.ProposedSale;
import domain.Sale;
import exceptions.FileNotUploadedException;
import exceptions.MustBeLaterThanTodayException;
import exceptions.SaleAlreadyExistException;

/**
 * It implements the data access to the objectDb database
 */
public class DataAccess  {
	private  EntityManager  db;
	private  EntityManagerFactory emf;
    private static final int baseSize = 160;

	private static final String basePath="src/main/resources/images/";
	private static final String dbServerDir = "src/main/resources/db/";


	ConfigXML c=ConfigXML.getInstance();

     public DataAccess()  {
		if (c.isDatabaseInitialized()) {
			String fileName=c.getDbFilename();

			if (!c.isDatabaseLocal()) fileName=dbServerDir+fileName;
			
			File fileToDelete= new File(fileName);
			if(fileToDelete.delete()){
				File fileToDeleteTemp= new File(fileName+"$");
				fileToDeleteTemp.delete();
				System.out.println("File deleted");
			 } else {
				 System.out.println("Operation failed");
				}
		}
		open();
		if  (c.isDatabaseInitialized()) 
			initializeDB();
		System.out.println("DataAccess created => isDatabaseLocal: "+c.isDatabaseLocal()+" isDatabaseInitialized: "+c.isDatabaseInitialized());

		close();

	}
     
    public DataAccess(EntityManager db) {
    	this.db=db;
    }

	
	
	/**
	 * This method  initializes the database with some products and sellers.
	 * This method is invoked by the business logic (constructor of BLFacadeImplementation) when the option "initialize" is declared in the tag dataBaseOpenMode of resources/config.xml file
	 */	
	public void initializeDB(){
		
		db.getTransaction().begin();

		try { 
	       
		    //Create sellers 
			Seller user1=new Seller("seller1@gmail.com","Aitor Fernandez","1234");
			Seller user2=new Seller("seller22@gmail.com","Ane Gaztañaga","2345");
			Seller user3=new Seller("seller3@gmail.com","Test Seller","0212");
			
			Buyer user4= new Buyer("buyer1@gmail.com","Test Seller","0212");
			User user5 =new Buyer("buyer2@gmail.com","Test Seller","2222");
			
			//Create products
			Date today = UtilDate.trim(new Date());
		
			
			Sale s1 = user1.addSale("futbol baloia", "oso polita, gutxi erabilita", 2, 10,  today, null);
			Sale s2 =user1.addSale("salomon mendiko botak", "44 zenbakia, 3 ateraldi",2, 20,  today, null);
			user1.addSale("samsung 42\" telebista", "berria, erabili gabe", 2, 175,  today, null);
			
			user4.addProposedSale(s1, 1.2f);   
			

			user2.addSale("imac 27", "7 urte, dena ondo dabil", 1, 200,today, null);
			user2.addSale("iphone 17", "oso gutxi erabilita", 2, 400, today, null);
			user2.addSale("orbea mendiko bizikleta", "29\" 10 urte, mantenua behar du", 3,225, today, null);
			user2.addSale("polar kilor erlojua", "Vantage M, ondo dago", 3, 30, today, null);

			user3.addSale("sukaldeko mahaia", "1.8*0.8, 4 aulkiekin. Prezio finkoa", 3,45, today, null);
			
			
			db.persist(user1);
			db.persist(user2);
			db.persist(user3);
			db.persist(user4);

	
			db.getTransaction().commit();
			System.out.println("Db initialized");
		}
		catch (Exception e){
			e.printStackTrace();
		}
	}
	
	
	/**
	 * This method creates/adds a product to a seller
	 * 
	 * @param title of the product
	 * @param description of the product
	 * @param status 
	 * @param selling price
	 * @param category of a product
	 * @param publicationDate
	 * @return Product
 	 * @throws SaleAlreadyExistException if the same product already exists for the seller
	 */
	public Sale createSale(String title, String description, int status, float price,  Date pubDate, String sellerEmail, File file) throws  FileNotUploadedException, MustBeLaterThanTodayException, SaleAlreadyExistException {
		

		System.out.println(">> DataAccess: createProduct=> title= "+title+" seller="+sellerEmail);
		try {
		

			if(pubDate.before(UtilDate.trim(new Date()))) {
				throw new MustBeLaterThanTodayException(ResourceBundle.getBundle("Etiquetas").getString("DataAccess.ErrorSaleMustBeLaterThanToday"));
			}
			if (file==null)
				throw new FileNotUploadedException(ResourceBundle.getBundle("Etiquetas").getString("DataAccess.ErrorFileNotUploadedException"));

			db.getTransaction().begin();
			
			Seller seller = db.find(Seller.class, sellerEmail);
			if (seller.doesSaleExist(title)) {
				db.getTransaction().commit();
				throw new SaleAlreadyExistException(ResourceBundle.getBundle("Etiquetas").getString("DataAccess.SaleAlreadyExist"));
			}

			Sale sale = seller.addSale(title, description, status, price, pubDate, file);
			//next instruction can be obviated

			db.persist(seller); 
			db.getTransaction().commit();
			 System.out.println("sale stored "+sale+ " "+seller);

			return sale;
		} catch (NullPointerException e) {
			   e.printStackTrace();
			// TODO Auto-generated catch block
			db.getTransaction().commit();
			return null;
		}
		
		
	}
	
	/**
	 * This method retrieves all the products that contain a desc text in a title
	 * 
	 * @param desc the text to search
	 * @return collection of products that contain desc in a title
	 */
	public List<Sale> getSales(String desc) {
		System.out.println(">> DataAccess: getProducts=> from= "+desc);

		List<Sale> res = new ArrayList<Sale>();	
		TypedQuery<Sale> query = db.createQuery("SELECT s FROM Sale s WHERE s.title LIKE ?1",Sale.class);   
		query.setParameter(1, "%"+desc+"%");
		
		List<Sale> sales = query.getResultList();
	 	 for (Sale sale:sales){
		   res.add(sale);
		  }
	 	return res;
	}
	
	/**
	 * This method retrieves the products that contain a desc text in a title and the publicationDate today or before
	 * 
	 * @param desc the text to search
	 * @return collection of products that contain desc in a title
	 */
	public List<Sale> getPublishedSales(String desc, Date pubDate) {
		System.out.println(">> DataAccess: getProducts=> from= "+desc);

		List<Sale> res = new ArrayList<Sale>();	
		TypedQuery<Sale> query = db.createQuery("SELECT s FROM Sale s WHERE s.title LIKE ?1 AND s.pubDate <=?2",Sale.class);   
		query.setParameter(1, "%"+desc+"%");
		query.setParameter(2,pubDate);
		
		List<Sale> sales = query.getResultList();
	 	 for (Sale sale:sales){
		   res.add(sale);
		  }
	 	return res;
	}
	
	public List<ProposedSale> getProposedSales(Seller sel){
		
		System.out.println(">> DataAccess: getProducts=> from= ");

		List<ProposedSale> res = new ArrayList<ProposedSale>();	
		
		TypedQuery<Sale> query1 = db.createQuery("SELECT s FROM Sale s WHERE s.seller.email LIKE ?1",Sale.class); 
		query1.setParameter(1, "%"+sel.getEmail()+"%");
		List<Sale> sales = query1.getResultList();

		for(Sale s:sales) {
			res.addAll(s.getProposedSales());
		}
		
	 	return res;
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
		System.out.println("DataAccess opened => isDatabaseLocal: "+c.isDatabaseLocal());

		
	}

	public BufferedImage getFile(String fileName) {
		File file=new File(basePath+fileName);
		BufferedImage targetImg=null;
		try {
             targetImg = rescale(ImageIO.read(file));
        } catch (IOException ex) {
            //Logger.getLogger(MainAppFrame.class.getName()).log(Level.SEVERE, null, ex);
        }
		return targetImg;

	}
	
	public BufferedImage rescale(BufferedImage originalImage)
    {
		System.out.println("rescale "+originalImage);
        BufferedImage resizedImage = new BufferedImage(baseSize, baseSize, BufferedImage.TYPE_INT_RGB);
        Graphics2D g = resizedImage.createGraphics();
        g.drawImage(originalImage, 0, 0, baseSize, baseSize, null);
        g.dispose();
        return resizedImage;
    }
	
	
	
	public void close(){
		db.close();
		System.out.println("DataAcess closed");
	}
	
	public boolean addUser(User u) {
		boolean res = false;
		String email = u.getEmail();
		
		if(db.find(User.class, email)==null) {
			db.getTransaction().begin();
			db.persist(u);
			db.getTransaction().commit();
			res=true;
		}
	return res;
		
	}
	
	public User browseUser(String email) {
		User u= db.find(User.class,email);
		return u;
	}

	public ProposedSale createProposedSale(int sID, Buyer b, float p) {
		
		
		
		//System.out.println(">> DataAccess: createProduct=> title= "+Sale+" seller="+sellerEmail);
		try {
		
			db.getTransaction().begin();
			
			Sale sale = db.find(Sale.class, sID);
			Buyer buyer = db.find(Buyer.class, b.getEmail());
			ProposedSale proposal= new ProposedSale(sale,buyer,p);
			
			sale.getProposedSales().add(proposal);
			buyer.getProposedSales().add(proposal);
			
			db.persist(proposal); 
			db.getTransaction().commit();
			 //System.out.println("sale stored "+sale+ " "+seller);

			return proposal;
		} catch (NullPointerException e) {
			   e.printStackTrace();
			// TODO Auto-generated catch block
			db.getTransaction().commit();
			return null;
		}
		
		
	}
	
	

	
	
	
}
