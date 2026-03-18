package businessLogic;
import java.io.File;
import java.util.Date;
import java.util.List;

import javax.jws.WebMethod;
import javax.jws.WebService;

import dataAccess.DataAccess;
import domain.Buyer;
import domain.ProposedSale;
import domain.Sale;
import domain.Seller;
import domain.User;
import exceptions.FileNotUploadedException;
import exceptions.MustBeLaterThanTodayException;
import exceptions.SaleAlreadyExistException;
import gui.MainGUI;

import java.awt.image.BufferedImage;
import java.awt.Image;
import javax.imageio.ImageIO;
import java.io.IOException;


/**
 * It implements the business logic as a web service.
 */
@WebService(endpointInterface = "businessLogic.BLFacade")
public class BLFacadeImplementation  implements BLFacade {
	 private static final int baseSize = 160;

		private static final String basePath="src/main/resources/images/";
	DataAccess dbManager;

	User usuario; //Al iniciar el programa es null, porque no se ha asignado un rol al usuario. Posteriormente, tomará valor de Buyer o Seller.
	//String tipoUsuario;
	
	public BLFacadeImplementation()  {		
		System.out.println("Creating BLFacadeImplementation instance");
		dbManager=new DataAccess();	
		usuario=new User("");
		
	}
	
    public BLFacadeImplementation(DataAccess da)  {
		System.out.println("Creating BLFacadeImplementation instance with DataAccess parameter");
		dbManager=da;
		usuario=new User("");
		
	}
    

	/**
	 * {@inheritDoc}
	 */
   @WebMethod
	public Sale createSale(String title, String description,int status, float price, Date pubDate, String sellerEmail, File file) throws  FileNotUploadedException, MustBeLaterThanTodayException, SaleAlreadyExistException {
		dbManager.open();
		Sale product=dbManager.createSale(title, description, status, price, pubDate, sellerEmail, file);		
		dbManager.close();
		return product;
   };
	
   /**
    * {@inheritDoc}
    */
	@WebMethod 
	public List<Sale> getSales(String desc){
		dbManager.open();
		List<Sale>  rides=dbManager.getSales(desc);
		dbManager.close();
		return rides;
	}
	
	/**
	    * {@inheritDoc}
	    */
		@WebMethod 
		public List<Sale> getPublishedSales(String desc, Date pubDate) {
			dbManager.open();
			List<Sale>  rides=dbManager.getPublishedSales(desc,pubDate);
			dbManager.close();
			return rides;
		}
		public List<ProposedSale> getProposedSales() {
			Seller s = (Seller) usuario;
			
			dbManager.open();
			List<ProposedSale>  rides=dbManager.getProposedSales(s);
			dbManager.close();
			return rides;
		}
		
	/**
	    * {@inheritDoc}
	    */
	@WebMethod public BufferedImage getFile(String fileName) {
		return dbManager.getFile(fileName);
	}

    
	public void close() {
		DataAccess dB4oManager=new DataAccess();
		dB4oManager.close();

	}

	/**
	 * {@inheritDoc}
	 */
    @WebMethod	
	 public void initializeBD(){
    	dbManager.open();
		dbManager.initializeDB();
		dbManager.close();
	}
    /**
	 * {@inheritDoc}
	 */
    @WebMethod public Image downloadImage(String imageName) {
        File image = new File(basePath+imageName);
        try {
            return ImageIO.read(image);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
    
    
    public int createAccount(String email, String name, String pass, boolean seller) {
    	
    	int tipo;
    	int res=0;
    	User newUser;
    	
    	if(seller) {
    		newUser=new Seller(email,name,pass);
    		tipo=2;
    	}
    	else {
    		newUser=new Buyer(email,name,pass);
    		tipo=1;
    	}
    	dbManager.open();
    	boolean anadido = dbManager.addUser(newUser);
    	dbManager.close();
    	if(anadido) {
    		usuario=newUser;
    		res=tipo;
    	}
    	
    	return res;
    }
    
    //0: no añadido; 1: buyer ; 2:seller
    public int makeLogin(String email, String password) {
    	
    	int tipo = 0;
    	dbManager.open();
    	User u = dbManager.browseUser(email);
    	dbManager.close();
    	if(u!=null) {
    		boolean exito = u.checkLogin(password);
    		if(exito) {
    			usuario=u;
    			if (usuario instanceof Seller) {
    				tipo = 2;
    			}else {
    				tipo = 1;
    			}
    			
    		}
    	}
    	return tipo;
    }

	public User getUsuario() {
		return usuario;
	}

	public void setUsuario(User usuario) {
		this.usuario = usuario;
	}

	public ProposedSale createProposedSale(Sale s, float p) {
		
		Buyer b= (Buyer)usuario;
		dbManager.open();
		
		ProposedSale proposal=dbManager.createProposedSale(s.getSaleNumber(), b,p);		
		dbManager.close();
		return proposal;	
	}
}

