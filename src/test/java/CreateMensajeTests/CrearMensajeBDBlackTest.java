package CreateMensajeTests;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.fail;

import org.junit.Test;

import data_access.DataAccess;
import domain.Conversacion;
import domain.Mensaje;

public class CrearMensajeBDBlackTest {	
	static DataAccess sut=new DataAccess();
	static TestDataAccessCrearMensaje testDA=new TestDataAccessCrearMensaje();
		
	@Test 
	public void test1() {
		testDA.open();
		testDA.createBuyer("seller1@ehu.eus","Seller Test");
		Conversacion conversacionReal = testDA.createConversacion("Tema prueba","seller1@ehu.eus");
		testDA.close();
		try {
			sut.open();
			Mensaje respuesta=sut.crearMensaje("Hola", conversacionReal,"seller1@ehu.eus");
			sut.close();
			assertNotNull(respuesta);
		}catch(Exception e) {
			fail();
		}finally {	
			testDA.open();
			testDA.removeConversacion(conversacionReal.getCodigo());
			testDA.removeBuyer("seller1@ehu.eus");
			testDA.close();
		}
	}
	
	@Test
	public void test2() {
		testDA.open();
		testDA.createBuyer("seller1@ehu.eus","Seller Test");
		Conversacion conversacionReal = testDA.createConversacion("Tema prueba", "seller1@ehu.eus");
		testDA.close();
		try {	
			sut.open();
			Mensaje respuesta=sut.crearMensaje(null, conversacionReal,"seller1@ehu.eus");
			sut.close();
			assertNull(respuesta);
		}catch(Exception e) {
			fail();
		}finally {
			testDA.open();
			testDA.removeConversacion(conversacionReal.getCodigo());
			testDA.removeBuyer("seller1@ehu.eus");
			testDA.close();
		}
	}
	
	@Test
	public void test3() {
		testDA.open();
		testDA.createBuyer("seller1@ehu.eus","Seller Test");
		testDA.close();
		try {	
			sut.open();
			Mensaje respuesta=sut.crearMensaje("Hola", null,"seller1@ehu.eus");
			sut.close();
			assertNull(respuesta);
		}catch(Exception e){
			fail();
		} finally {
			testDA.open();
			testDA.removeBuyer("seller1@ehu.eus");
			testDA.close();
		}
	}	
	
	@Test
	public void test4() {
		testDA.open();
		Conversacion conversacionReal = testDA.createConversacion("Tema prueba","seller1@ehu.eus");
		testDA.close();
		try {
			sut.open();
			Mensaje respuesta=sut.crearMensaje("Hola", conversacionReal, null);
			sut.close();
			assertNull(respuesta);
		}catch(Exception e) {	
			fail();
		}finally {
			testDA.open();
			testDA.removeConversacion(conversacionReal.getCodigo());
			testDA.close();
		}
	}
	
	@Test
	public void test5() {
		testDA.open();
		Conversacion conversacionReal = testDA.createConversacion("Tema prueba","seller1@ehu.eus");
		testDA.close();
		try {
			sut.open(); 
			Mensaje respuesta=sut.crearMensaje("Hola", conversacionReal,"fakeuser@ehu.eus");
			sut.close();
			assertNull(respuesta);
		}catch(Exception e) {	
			fail();
		}finally {
			testDA.open();
			testDA.removeConversacion(conversacionReal.getCodigo());
			testDA.close();
		}
	}
	
	@Test
	public void test6() {
		testDA.open();
		testDA.createBuyer("seller1@ehu.eus","Seller Test");
		testDA.close();
		try {
			Conversacion conversacionFalsa = new Conversacion();
			sut.open();
			Mensaje respuesta=sut.crearMensaje("Hola", conversacionFalsa,"seller1@ehu.eus");
			sut.close();
			assertNull(respuesta);
		}catch(Exception e) {
			fail();
		}finally {
			testDA.open();
			testDA.removeBuyer("seller1@ehu.eus");
			testDA.close();
		}
	}
	
	@Test
	public void test7() {
		testDA.open();
		testDA.createBuyer("seller1@ehu.eus","Seller test");
		Conversacion conversacionReal=testDA.createConversacion("Tema prueba","seller1@ehu.eus");
		testDA.close();
		try {
			sut.open();
			Mensaje result=sut.crearMensaje("", conversacionReal,"seller1@ehu.eus");
			sut.close();
			assertNull(result);
		}catch(Exception e) {
			fail();
		}finally {
			testDA.open();
			testDA.removeBuyer("seller1@ehu.eus");
			testDA.removeConversacion(conversacionReal.getCodigo());
			testDA.close();
		}	
	}
	
	@Test
	public void test8() {
		testDA.open();
		testDA.createBuyer("","Seller test");
		Conversacion conversacionReal=testDA.createConversacion("Tema prueba","");
		testDA.close();
		try {
			sut.open();
			Mensaje result=sut.crearMensaje("Hola", conversacionReal,"");
			sut.close();
			assertNull(result);
		}catch(Exception e) {
			fail();
		}finally {
			testDA.open();
			testDA.removeBuyer("");
			testDA.removeConversacion(conversacionReal.getCodigo());
			testDA.close();
		}	
	}
}