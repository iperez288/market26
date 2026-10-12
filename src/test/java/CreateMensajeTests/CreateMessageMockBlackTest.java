package CreateMensajeTests;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.fail;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import data_access.DataAccess;
import domain.Buyer;
import domain.Conversacion;
import domain.Mensaje;

public class CreateMessageMockBlackTest {

	private EntityManager mock=Mockito.mock(EntityManager.class);
	private EntityTransaction tx =Mockito.mock(EntityTransaction.class);
	
	private DataAccess sut=new DataAccess(mock);
	
	@Test
	public void test1() {
		try {
			Conversacion c=new Conversacion();
			Mockito.when(mock.getTransaction()).thenReturn(tx);
			Mockito.when(mock.find(Buyer.class,"seller1@ehu.eus")).thenReturn(new Buyer());
			Mockito.when(mock.find(Conversacion.class,c.getCodigo())).thenReturn(c);
			
			Mensaje respuesta=sut.crearMensaje("Hola",c,"seller1@ehu.eus");
			assertNotNull(respuesta);
		}catch(Exception e) {
			fail();
		}
	} 
	
	@Test
	public void test2() {
		try {
			Conversacion c=new Conversacion();
			Mockito.when(mock.getTransaction()).thenReturn(tx);
			Mockito.when(mock.find(Buyer.class,"seller1@ehu.eus")).thenReturn(new Buyer());
			Mockito.when(mock.find(Conversacion.class,c.getCodigo())).thenReturn(c);

			Mensaje respuesta=sut.crearMensaje(null,c,"seller1@ehu.eus");
			assertNull(respuesta);
		}catch(Exception e) { 
			fail();
		} 
	}
	
	@Test
	public void test3() {
		try {
			Mockito.when(mock.find(Buyer.class,"seller1@ehu.eus")).thenReturn(new Buyer());
			Mockito.when(mock.getTransaction()).thenReturn(tx);
			
			Mensaje respuesta=sut.crearMensaje("Hola",null,"seller1@ehu.eus");
			assertNull(respuesta);
		}catch(Exception e) {
			fail();
		}	
	}
	
	@Test
	public void test4() {
		try {
			Conversacion c=new Conversacion();
			Mockito.when(mock.getTransaction()).thenReturn(tx);
			Mockito.when(mock.find(Conversacion.class,c.getCodigo())).thenReturn(c);
			Mockito.when(mock.find(Buyer.class,null)).thenThrow(new IllegalArgumentException());

			Mensaje respuesta=sut.crearMensaje("Hola",c,null);
			assertNull(respuesta);
		}catch(Exception e) {
			fail();
		}	
	}
	
	@Test
	public void test5() {
		try {
			Conversacion c=new Conversacion();
			Mockito.when(mock.getTransaction()).thenReturn(tx);
			Mockito.when(mock.find(Buyer.class, "fakeuser@ehu.eus")).thenReturn(null);
			Mockito.when(mock.find(Conversacion.class,c.getCodigo())).thenReturn(c);

			Mensaje respuesta=sut.crearMensaje("Hola",c,"fakeuser@ehu.eus");
			assertNull(respuesta);		
		}catch(Exception e) {
			fail();
		}
	}
	
	@Test
	public void test6() {
		try {
			Conversacion cFake=new Conversacion(); 
			Mockito.when(mock.getTransaction()).thenReturn(tx);
			Mockito.when(mock.find(Conversacion.class, cFake.getCodigo())).thenReturn(null);
			Mockito.when(mock.find(Buyer.class,"seller1@ehu.eus")).thenReturn(new Buyer());
			
			Mensaje respuesta=sut.crearMensaje("Hola",cFake,"seller1@ehu.eus");
			assertNull(respuesta);			
		}catch(Exception e) {
			fail();
		}		
	}
	
	@Test
	public void test7() {
		try {
			Conversacion c=new Conversacion();
			Mockito.when(mock.getTransaction()).thenReturn(tx);
			Mockito.when(mock.find(Buyer.class,"seller1@ehu.eus")).thenReturn(new Buyer());
			Mockito.when(mock.find(Conversacion.class,c.getCodigo())).thenReturn(c);
			
			Mensaje respuesta=sut.crearMensaje("",c,"seller1@ehu.eus");
			assertNull(respuesta); 
		}catch(Exception e) {
			fail();
		}
	}	
	
	@Test
	public void test8() {
		try {
			Conversacion c=new Conversacion();
			Mockito.when(mock.getTransaction()).thenReturn(tx);
			Mockito.when(mock.find(Buyer.class,"")).thenReturn(new Buyer());
			Mockito.when(mock.find(Conversacion.class,c.getCodigo())).thenReturn(c);
			
			Mensaje respuesta=sut.crearMensaje("Hola",c,"");
			assertNull(respuesta); 
		}catch(Exception e) {
			fail();
		}
	}
}