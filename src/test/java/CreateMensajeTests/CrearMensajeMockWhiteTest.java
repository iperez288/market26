package CreateMensajeTests;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.fail;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import org.junit.Test;
import org.mockito.Mockito;

import data_access.DataAccess;
import domain.Buyer;
import domain.Conversacion;
import domain.Mensaje;

public class CrearMensajeMockWhiteTest {
	
	private EntityManager mock=Mockito.mock(EntityManager.class);
	private EntityTransaction tx =Mockito.mock(EntityTransaction.class);
	private DataAccess sut=new DataAccess(mock);
	
	@Test
	public void test1() {
		try {
			Mockito.when(mock.getTransaction()).thenReturn(tx);
			Mockito.when(mock.find(Buyer.class,"seller1@gmail.com")).thenReturn(new Buyer());
			
			Mensaje resultado=sut.crearMensaje("Hola",null,"seller1@gmail.com");
			assertNull(resultado);
		}catch(Exception e) {
			fail();
		}
	}
	
	@Test
	public void test2() {
		try {
			Conversacion c=new Conversacion();

			Mockito.when(mock.getTransaction()).thenReturn(tx);
			Mockito.when(mock.find(Conversacion.class,c.getCodigo())).thenReturn(c);
			Mockito.when(mock.find(Buyer.class,"sellerfake@ehu.eus")).thenReturn(null);
			
			Mensaje resultado = sut.crearMensaje("Hola",c,"sellerfake@ehu.eus");
			assertNull(resultado);
		}catch(Exception e) {
			fail();
		}
	}
	
	@Test
	public void test3() {
		try {	
			Conversacion cFake=new Conversacion(); 
			Mockito.when(mock.getTransaction()).thenReturn(tx);
			Mockito.when(mock.find(Buyer.class,"seller1@gmail.com")).thenReturn(new Buyer());
			Mockito.when(mock.find(Conversacion.class,cFake.getCodigo())).thenReturn(null);

			Mensaje resultado=sut.crearMensaje("Hola",cFake,"seller1@gmail.com");			
			assertNull(resultado);
		}catch(Exception e) {
			fail();
		}	
	}
	
	@Test
	public void test4() {
		try {
			Conversacion c=new Conversacion();
			Mockito.when(mock.getTransaction()).thenReturn(tx);
			Mockito.when(mock.find(Conversacion.class, c.getCodigo())).thenReturn(c);
			Mockito.when(mock.find(Buyer.class,"seller1@gmail.com")).thenReturn(new Buyer());

			Mensaje resultado=sut.crearMensaje("Hola",c,"seller1@gmail.com");
			assertNotNull(resultado);
		}catch(Exception e) {
			fail();
		}
	}
}