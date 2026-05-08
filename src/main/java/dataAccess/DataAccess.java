package dataAccess;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import javax.imageio.ImageIO;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

import configuration.ConfigXML;
import configuration.UtilDate;
import domain.Seller;
import domain.Transaction;
import domain.Transaction.TransactionType;
import domain.User;
import domain.Valoracion;
import domain.Buyer;
import domain.Conversacion;
import domain.Conversacion.EstadoConversacion;
import domain.Mensaje;
import domain.ProposedSale;
import domain.Sale;
import exceptions.FileNotUploadedException;
import exceptions.MustBeLaterThanTodayException;
import exceptions.SaleAlreadyExistException;

/**
 * It implements the data access to the objectDb database
 */
public class DataAccess {
	private EntityManager db;
	private EntityManagerFactory emf;
	private static final int baseSize = 160;

	//private static final String basePath = "src/main/resources/images/";
	//private static final String dbServerDir = "src/main/resources/db/";
	
	private static final String basePath = "resources/images/";
	private static final String dbServerDir = "resources/db/";

	ConfigXML c = ConfigXML.getInstance();

	public DataAccess() {
		if (c.isDatabaseInitialized()) {
			String fileName = c.getDbFilename();

			if (!c.isDatabaseLocal())
				fileName = dbServerDir + fileName;

			File fileToDelete = new File(fileName);
			if (fileToDelete.delete()) {
				File fileToDeleteTemp = new File(fileName + "$");
				fileToDeleteTemp.delete();
				System.out.println("File deleted");
			} else {
				System.out.println("Operation failed");
			}
		}
		open();
		if (c.isDatabaseInitialized())
			initializeDB();
		System.out.println("DataAccess created => isDatabaseLocal: " + c.isDatabaseLocal() + " isDatabaseInitialized: "
				+ c.isDatabaseInitialized());

		close();

	}

	public DataAccess(EntityManager db) {
		this.db = db;
	}

	/**
	 * This method initializes the database with some products and sellers. This
	 * method is invoked by the business logic (constructor of
	 * BLFacadeImplementation) when the option "initialize" is declared in the tag
	 * dataBaseOpenMode of resources/config.xml file
	 */
	public void initializeDB() {

		db.getTransaction().begin();

		try {

			 //Create sellers 
			Seller user1=new Seller("seller1@gmail.com","Aitor Fernandez","1234");
			Seller user2=new Seller("seller2@gmail.com","Ane GaztaÃ±aga","1234");
			Seller user3=new Seller("seller3@gmail.com","Test Seller","0212");
			
			Buyer user4= new Buyer("buyer1@gmail.com","Test Seller","1234");
			User user5 =new Buyer("buyer2@gmail.com","Test Seller","1234");
			
			//Create products
			Date today = UtilDate.trim(new Date());
		
			
			Sale s1 = user1.addSale("futbol baloia", "oso polita, gutxi erabilita", 2, 10,  today, null);
			Sale s2 =user1.addSale("salomon mendiko botak", "44 zenbakia, 3 ateraldi",2, 20,  today, null);
			
			
			user1.addSale("samsung 42\" telebista", "berria, erabili gabe", 2, 175,  today, null);
			
			Transaction t = new Transaction( 100.0f, today, TransactionType.income,  user4);
			
			user4.addTransaction(t);
			
			
			
			user4.addProposedSale(s1, 1.2f);   
			user4.getProposedSales().get(0).setFechaCompra(today);
			s1.doPurchase();
			
			user4.addProposedSale(s2, 4.2f);   
			ProposedSale ps1 = user4.getProposedSales().get(1);
			ps1.setFechaCompra(today);
			s2.doPurchase();
			

			user2.addSale("imac 27", "7 urte, dena ondo dabil", 1, 200,today, null);
			user2.addSale("iphone 17", "oso gutxi erabilita", 2, 400, today, null);
			user2.addSale("orbea mendiko bizikleta", "29\" 10 urte, mantenua behar du", 3,225, today, null);
			user2.addSale("polar kilor erlojua", "Vantage M, ondo dago", 3, 30, today, null);

			user3.addSale("sukaldeko mahaia", "1.8*0.8, 4 aulkiekin. Prezio finkoa", 3,45, today, null);
			
			
			db.persist(user1);
			db.persist(user2);
			db.persist(user3);
			db.persist(user4);
			db.persist(user5);
			
			db.getTransaction().commit();
			
			
			
//Conversaciones
			
			Conversacion c1 = this.crearConversacion("Esferidad", s1, "buyer1@gmail.com");
			this.crearMensaje("ï¿½Es redondo?", c1, "buyer1@gmail.com");
			//int pID, String email, int rate, String text
			this.hacerValoracion(ps1.getID(), user4.getEmail(),8, "Está bien.");
			
			
			
			System.out.println("Db initialized");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * This method creates/adds a product to a seller
	 * 
	 * @param title           of the product
	 * @param description     of the product
	 * @param status
	 * @param selling         price
	 * @param category        of a product
	 * @param publicationDate
	 * @return Product
	 * @throws SaleAlreadyExistException if the same product already exists for the
	 *                                   seller
	 */
	public Sale createSale(String title, String description, int status, float price, Date pubDate, String sellerEmail,
			File file) throws FileNotUploadedException, MustBeLaterThanTodayException, SaleAlreadyExistException {

		System.out.println(">> DataAccess: createProduct=> title= " + title + " seller=" + sellerEmail);
		try {

			if (pubDate.before(UtilDate.trim(new Date()))) {
				throw new MustBeLaterThanTodayException(
						ResourceBundle.getBundle("Etiquetas").getString("DataAccess.ErrorSaleMustBeLaterThanToday"));
			}
			if (file == null)
				throw new FileNotUploadedException(
						ResourceBundle.getBundle("Etiquetas").getString("DataAccess.ErrorFileNotUploadedException"));

			db.getTransaction().begin();

			Seller seller = db.find(Seller.class, sellerEmail);
			if (seller.doesSaleExist(title)) {
				db.getTransaction().commit();
				throw new SaleAlreadyExistException(
						ResourceBundle.getBundle("Etiquetas").getString("DataAccess.SaleAlreadyExist"));
			}

			Sale sale = seller.addSale(title, description, status, price, pubDate, file);
			// next instruction can be obviated

			db.persist(seller);
			db.getTransaction().commit();
			System.out.println("sale stored " + sale + " " + seller);

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
		System.out.println(">> DataAccess: getProducts=> from= " + desc);

		List<Sale> res = new ArrayList<Sale>();
		TypedQuery<Sale> query = db.createQuery("SELECT s FROM Sale s WHERE s.title LIKE ?1", Sale.class);
		query.setParameter(1, "%" + desc + "%");

		List<Sale> sales = query.getResultList();
		for (Sale sale : sales) {
			res.add(sale);
		}
		return res;
	}

	/**
	 * This method retrieves the products that contain a desc text in a title and
	 * the publicationDate today or before
	 * 
	 * @param desc the text to search
	 * @return collection of products that contain desc in a title
	 */
	public List<Sale> getPublishedSales(String desc, Date pubDate) {
		System.out.println(">> DataAccess: getProducts=> from= " + desc);

		List<Sale> res = new ArrayList<Sale>();
		TypedQuery<Sale> query = db.createQuery("SELECT s FROM Sale s WHERE s.title LIKE ?1 AND s.pubDate <=?2 AND s.purchased = false",
				Sale.class);
		query.setParameter(1, "%" + desc + "%");
		query.setParameter(2, pubDate);

		List<Sale> sales = query.getResultList();
		for (Sale sale : sales) {
			res.add(sale);
		}
		return res;
	}


	public void open() {

		//if (emf == null) {
			String fileName = c.getDbFilename();
			if (c.isDatabaseLocal()) {
				emf = Persistence.createEntityManagerFactory("objectdb:" + fileName);
				
			} else {
				Map<String, String> properties = new HashMap<String, String>();
				properties.put("javax.persistence.jdbc.user", c.getUser());
				properties.put("javax.persistence.jdbc.password", c.getPassword());

				emf = Persistence.createEntityManagerFactory(
						"objectdb://" + c.getDatabaseNode() + ":" + c.getDatabasePort() + "/" + fileName, properties);
				
			}
			
			
		//}
		
		db = emf.createEntityManager();
		System.out.println("DataAccess opened => isDatabaseLocal: " + c.isDatabaseLocal());

	}

	public BufferedImage getFile(String fileName) {
		File file = new File(basePath + fileName);
		BufferedImage targetImg = null;
		try {
			targetImg = rescale(ImageIO.read(file));
		} catch (IOException ex) {
			// Logger.getLogger(MainAppFrame.class.getName()).log(Level.SEVERE, null, ex);
		}
		return targetImg;

	}

	public BufferedImage rescale(BufferedImage originalImage) {
		System.out.println("rescale " + originalImage);
		BufferedImage resizedImage = new BufferedImage(baseSize, baseSize, BufferedImage.TYPE_INT_RGB);
		Graphics2D g = resizedImage.createGraphics();
		g.drawImage(originalImage, 0, 0, baseSize, baseSize, null);
		g.dispose();
		return resizedImage;
	}

	public void close() {
		db.close();
		System.out.println("DataAcess closed");
	}

	public boolean addUser(User u) {
		boolean res = false;
		String email = u.getEmail();

		if (db.find(User.class, email) == null) {
			db.getTransaction().begin();
			db.persist(u);
			db.getTransaction().commit();
			res = true;
		}
		return res;

	}

	public User browseUser(String email) {
		User u = db.find(User.class, email);
		return u;
	}

	public ProposedSale createProposedSale(int sID, String email, float p) {

		// System.out.println(">> DataAccess: createProduct=> title= "+Sale+"
		// seller="+sellerEmail);
		try {

			db.getTransaction().begin();

			Sale sale = db.find(Sale.class, sID);
			Buyer buyer = db.find(Buyer.class, email);
			
			if (buyer.getSaldo() < p){
				return null;
			}
			
			
			ProposedSale proposal = new ProposedSale(sale, buyer, p);

			sale.addProposedSale(proposal);
			buyer.addProposedSale(proposal);

			db.persist(proposal);
			db.getTransaction().commit();
			// System.out.println("sale stored "+sale+ " "+seller);

			return proposal;
		} catch (NullPointerException e) {
			e.printStackTrace();
			// TODO Auto-generated catch block
			db.getTransaction().commit();
			return null;
		}
	}
	
	public List<ProposedSale> getProposedSales(String email) {

		System.out.println(">> DataAccess: getProducts=> from= ");

		List<ProposedSale> res = new ArrayList<ProposedSale>();

		TypedQuery<Sale> query1 = db.createQuery("SELECT s FROM Sale s WHERE s.seller.email LIKE ?1 AND s.purchased = false", Sale.class);
		query1.setParameter(1, email);
		List<Sale> sales = query1.getResultList();

		for (Sale s : sales) {
			res.addAll(s.getProposedSales());
		}

		return res;
	}

	public void annadirSaldo(String email, float importe) {
		

		db.getTransaction().begin();

		Buyer b = db.find(Buyer.class, email);

		Transaction t = new Transaction(importe, new Date(), Transaction.TransactionType.income, b);

		b.addTransaction(t);

		db.persist(t);

		db.getTransaction().commit();

	}

	public void retirarSaldo(String email, float importe) {
		
		User u = db.find(User.class, email);

		db.getTransaction().begin();

		Buyer b = (Buyer) u;

		Transaction t = new Transaction(importe, new Date(), Transaction.TransactionType.extraction, b);

		b.addTransaction(t);

		db.persist(t);

		db.getTransaction().commit();

	}
	
	public void hacerValoracion(int pID, String email, int rate, String text) {
			
			Valoracion val;
			Buyer u;
			ProposedSale ps;
			int snum; //Nï¿½mero de la venta a la que hace referencia el ProposedSale.
			long numV; //Nï¿½mero de valoraciones que tiene el vendedor.
			float sum; //Suma de valoraciones del vendedor
			
			//Traer de vuelta la Sale y el email
			
			db.getTransaction().begin();
			
			//Se da por sentado que ya se ha comprobado antes que es comprador.
			u = (Buyer) getUser(email);
			ps = getProposedSale(pID);
			val = new Valoracion(ps,u,rate,text);
			ps.setValoracion(val);
			u.addValoracion(val);
			db.persist(val);
			
			snum = ps.getSale().getSaleNumber();
			
			Seller s;
			
			s = db.find(Seller.class, ps.getSeller());
			
			/*TypedQuery<Seller> q1= db.createQuery("SELECT sl FROM Sale s JOIN s.seller sl WHERE  s.saleNumber = ?1", Seller.class);
			q1.setParameter(1, snum);
			
			List<Seller> sl = q1.getResultList();
			if(sl.size()>1) {
				System.out.println("Más de un vendedor encontrado");
			}
			s = sl.get(0);*/
			
			TypedQuery<Long> q2= db.createQuery("SELECT count(ps) FROM Seller sl JOIN sl.sales s JOIN s.proposedSales ps WHERE s.purchased = TRUE AND ps.valoracion IS NOT NULL AND sl.email = ?1" , Long.class);
			q2.setParameter(1, s.getEmail());
			
			numV = q2.getResultList().get(0);
			
			System.out.println("Num valoraciones de seller "+ s.getEmail() + ": " + numV);
			
			sum = s.getRate()*numV;
			sum = sum + rate;
			numV++;
			
			s.setRate(((float)sum)/numV);
			
			db.getTransaction().commit();
			
		}
	
	
	public List<ProposedSale> getPurchasedSales(String desc, String mail) {
		System.out.println(">> DataAccess: getProducts=> from= "+desc);
	
		List<ProposedSale> res = new ArrayList<ProposedSale>();	
		TypedQuery<ProposedSale> query = db.createQuery("SELECT ps FROM ProposedSale ps WHERE ps.sale.title LIKE ?1 AND ps.sale.purchased = true AND ps.buyer.email=?2",ProposedSale.class);   
		query.setParameter(1, "%"+desc+"%");
		query.setParameter(2, mail);
		
		List<ProposedSale> sales = query.getResultList();
	 	 for (ProposedSale sale:sales){
		   res.add(sale);
		  }
	 	return res;
	}

	public boolean doPurchase(String email, ProposedSale ps) {
		Buyer comprador;
		Seller vendedor;
		ProposedSale venta;
		Sale s;
		float precio;
		
		Transaction cobroComprador;
		Transaction pagoVendedor;
		
		
		Date today = UtilDate.trim(new Date());
		
		venta=ps;
		precio=venta.getPrice();
		
		db.getTransaction().begin();
		
		//Voy a modificar ProposedSale, Sale, Vendedor y Comprador.
	
		//1ï¿½ Quitarle el dinero al comprador
		String bmail = ps.getBuyer().getEmail();
		comprador = (Buyer) getUser(bmail);
		
		//Creo la transacciï¿½n
		cobroComprador = new Transaction(precio,today,TransactionType.purchase,comprador);
		
		//al comprador, llamo un mï¿½todo para que aï¿½ade la transacciï¿½n y modifique el saldo segï¿½n toque
		comprador.addTransaction(cobroComprador);
		
		db.persist(cobroComprador);
	
	// 2 Marcar la compra como vendida
		
		venta = getProposedSale(ps.getID());
		venta.setFechaCompra(today);
		
		int sID = venta.getSale().getSaleNumber();
		s= db.find(Sale.class, sID);
		s.doPurchase();
		
		
	// 3 Darle el dinero al vendedor
		vendedor = (Seller) getUser(email);
		pagoVendedor = new Transaction(precio,today,TransactionType.sale,vendedor);
		vendedor.addTransaction(pagoVendedor);
		db.persist(pagoVendedor);
		
		db.getTransaction().commit();
		
		return true;
		
	}
	
	public float getSaldoUsuario(String email) {
		
		float saldo = 0.0f;
		Buyer b = (Buyer) browseUser(email);
		
		if(b!=null)
			saldo = b.getSaldo();
		return saldo;
	}

	private ProposedSale getProposedSale(int pID) {
		
		return db.find(ProposedSale.class, pID);
	}
	
	private User getUser(String email) {
		
		return db.find(User.class, email);
	}

	public List<Transaction> getTransactions(String email) {
		TypedQuery<Transaction> query = db.createQuery("SELECT t FROM Transaction t WHERE t.usuario.email = ?1", Transaction.class);
		query.setParameter(1, email);
		
		return query.getResultList();
	}
	
	public Conversacion crearConversacion(String tema, Sale s, String email) {
		
		
		
	
			db.getTransaction().begin();
			
			Conversacion c = new Conversacion();
			
			Buyer b = db.find(Buyer.class, email);;
			
			if(b==null)
			{
				System.out.printf("No se encontrï¿½ usuario con email %s\n",email);
				return null;
				
			}else {			
			Sale sale = db.find(Sale.class, s.getSaleNumber());
			
			c = new Conversacion(tema,sale,b);
			b.addConversacion(c);
			sale.addConversacion(c);
			
			db.persist(c);
			}
			
			db.getTransaction().commit();
		
		
		
		return c;
		
	}
	
	public Mensaje crearMensaje(String texto, Conversacion c, String emailEmisor) {
		
		try {
			db.getTransaction().begin();
			
			Mensaje msg = new Mensaje();
			
			Buyer emisor = db.find(Buyer.class, emailEmisor);
			
			Conversacion conver = db.find(Conversacion.class, c.getCodigo());
			
			if(emisor == null || conver == null) {
				
				db.getTransaction().rollback();
				return null;
			}
			
			int mNumber = conver.getCantidadMensajes();
			
			Date now = new Date();
			
			msg = new Mensaje(conver,mNumber, emisor, now ,texto);
			
			conver.addMensaje(msg);
			emisor.addMensaje(msg);
			
			db.persist(msg);
			
			db.getTransaction().commit();
			
			
			
			return msg;
			

			
		} catch (NullPointerException e) {
			e.printStackTrace();
			// TODO Auto-generated catch block
			db.getTransaction().commit();
			return null;
		}
		
	
	}
	
	/**
	 * 
	 * @param email
	 * @return Conversaciones que tienen los productos del vendedor
	 */
	public List<Conversacion> getConversacionesDeProductos(String email){
		
		User u = this.browseUser(email);
		
		if(!(u instanceof Seller)) {
			return new ArrayList<Conversacion>();
		}
		
		TypedQuery<Conversacion> query = 
				db.createQuery("SELECT c FROM Conversacion c WHERE c.producto.seller.email = ?1 AND c.estado <> ?2", Conversacion.class);
		query.setParameter(1, email);
		query.setParameter(2, EstadoConversacion.FINALIZADA);
		
		return query.getResultList();
		
	}
	
	/**
	 * 
	 * @param email
	 * @return Conversaciones iniciadas por el usuario con correo electrï¿½nico email.
	 */
	public List<Conversacion> getConversacionesIniciadas(String email){
		
		TypedQuery<Conversacion> query = 
				db.createQuery("SELECT c FROM Conversacion c WHERE c.iniciador.email = ?1 AND c.estado<>?2", Conversacion.class);
		query.setParameter(1, email);
		query.setParameter(2, EstadoConversacion.FINALIZADA);
		
		return query.getResultList();	
	}
	
	public List<Mensaje> getMensajes(long cid){
		
		TypedQuery<Mensaje> query = 
				db.createQuery("SELECT m FROM Conversacion c JOIN c.mensajes m WHERE c.codigo = ?1 ORDER BY m.messageNumber ASC", Mensaje.class);
		query.setParameter(1, cid);
		
		return query.getResultList();
	}
	
	public void actualizarEstadoConversacion(long cid, EstadoConversacion nuevoEstado) {
		db.getTransaction().begin();
		
		Conversacion conv = db.find(Conversacion.class, cid);
	
		conv.setEstado(nuevoEstado);

		db.getTransaction().commit();
	}
	
	public List<ProposedSale> getVentasUsuario(String text, String email) {
	    TypedQuery<ProposedSale> query = db.createQuery(
	        "SELECT ps FROM ProposedSale ps WHERE ps.sale.seller.email = ?1 AND ps.sale.title LIKE ?2 AND ps.fechaCompra IS NOT NULL", 
	        ProposedSale.class
	    );
	    query.setParameter(1, email);
	    query.setParameter(2, "%" + text + "%");
	    return query.getResultList();
	}

	public float getPuntuacion(String email) {

		Seller s = db.find(Seller.class, email);
		
		return s.getRate();
	}
}

