package CreateMensajeTests;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.fail;

import org.junit.Test;

import data_access.DataAccess;
import domain.Conversacion;
import domain.Mensaje;

public class CrearMensajeBDWhiteTest {
	
	private TestDataAccessCrearMensaje testDA=new TestDataAccessCrearMensaje();
	private DataAccess sut=new DataAccess();
	
	@Test
	public void test1() {
		testDA.open();
		testDA.createBuyer("seller1@ehu.eus","sellerTest1");
		testDA.close();
		try {
			sut.open();
			Mensaje resultado=sut.crearMensaje("Hola",null,"seller1@ehu.eus");
			sut.close();
			assertNull(resultado);
		}catch(Exception e) {
			fail();
		}finally {
			testDA.open();
			testDA.removeBuyer("seller1@ehu.eus");
			testDA.close();
		}
	}
	
	@Test
	public void test2() {
		testDA.open();
		Conversacion c=testDA.createConversacion("Tema prueba","seller1@ehu.eus");
		testDA.close();
		try {		
			sut.open();
			Mensaje resultado = sut.crearMensaje("Hola",c,"sellerfake@ehu.eus");
			sut.close();
			assertNull(resultado);
		}catch(Exception e) {
			fail();
		}finally {
			testDA.open();
			testDA.removeConversacion(c.getCodigo());
			testDA.close();
		}
	}
	
	@Test
	public void test3() {
		testDA.open();
		testDA.createBuyer("seller1@ehu.eus","sellerTest1");
		testDA.close();
		try {	
			Conversacion cFake=new Conversacion();
			sut.open();
			Mensaje resultado=sut.crearMensaje("Hola",cFake,"seller1@ehu.eus");	
			sut.close();
			assertNull(resultado);
		}catch(Exception e) {
			fail();
		}	finally {
			testDA.open();
			testDA.removeBuyer("seller1@ehu.eus");
			testDA.close();
		}
	}
	
	@Test
	public void test4() {
		testDA.open();
		testDA.createBuyer("seller1@ehu.eus","sellerTest1");
		Conversacion c=testDA.createConversacion("Tema prueba","seller1@ehu.eus");
		testDA.close();
		try {
			sut.open();
			Mensaje resultado=sut.crearMensaje("Hola",c,"seller1@ehu.eus");
			sut.close();
			assertNotNull(resultado);
		}catch(Exception e) {
			fail();
		}finally {
			testDA.open();
			testDA.removeBuyer("seller1@ehu.eus");
			testDA.removeConversacion(c.getCodigo());
			testDA.close();
		}
	}
	

}