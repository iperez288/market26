package createProposedSaleTests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.when;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import org.junit.Test;
import org.mockito.Mockito;

import data_access.DataAccess;
import domain.Buyer;
import domain.ProposedSale;
import domain.Sale;

public class CreateProposedSaleMockBlackTest {

	private EntityManager mock = Mockito.mock(EntityManager.class);
	private EntityTransaction tx = Mockito.mock(EntityTransaction.class);

	private DataAccess sut = new DataAccess(mock);

	private static final Integer SALE_ID = 30;
	private static final String BUYER_EMAIL = "buyerTest@gmail.com";

	
	@Test
	public void test1() {
		Buyer buyer = new Buyer(BUYER_EMAIL, null, null, 10);
		when(mock.getTransaction()).thenReturn(tx);
		when(mock.find(Sale.class, SALE_ID)).thenReturn(null);
		when(mock.find(Buyer.class, BUYER_EMAIL)).thenReturn(buyer);

		try {
			ProposedSale resultado = sut.createProposedSale(SALE_ID, BUYER_EMAIL, 9);

			assertNull(resultado);

		} catch (Exception e) {
			fail();
		}
	}

	@Test
	public void test2() {
		Sale sale = new Sale();
		when(mock.getTransaction()).thenReturn(tx);
		when(mock.find(Sale.class, SALE_ID)).thenReturn(sale);

		try {
			ProposedSale resultado = sut.createProposedSale(SALE_ID, null, 9);

			assertNull(resultado);
			assertEquals(0, sale.getProposedSales().size());

		} catch (Exception e) {
			fail();
		}
	}

	@Test
	public void test3() {
		Sale sale = new Sale();
		Buyer buyer = new Buyer(BUYER_EMAIL, null, null, 10);
		when(mock.getTransaction()).thenReturn(tx);
		when(mock.find(Sale.class, SALE_ID)).thenReturn(sale);
		when(mock.find(Buyer.class, BUYER_EMAIL)).thenReturn(buyer);

		try {
			ProposedSale resultado = sut.createProposedSale(SALE_ID, BUYER_EMAIL, -5);

			assertNull(resultado);

		} catch (Exception e) {
			fail();
		}
	}

	@Test
	public void test4() {
		when(mock.getTransaction()).thenReturn(tx);
		when(mock.find(Sale.class, SALE_ID)).thenReturn(null);
		when(mock.find(Buyer.class, BUYER_EMAIL)).thenReturn(null);

		try {
			ProposedSale resultado = sut.createProposedSale(SALE_ID, BUYER_EMAIL, 9);

			assertNull(resultado);

		} catch (Exception e) {
			fail();
		}
	}

	@Test
	public void test5() {
		Sale sale = new Sale();
		when(mock.getTransaction()).thenReturn(tx);
		when(mock.find(Sale.class, SALE_ID)).thenReturn(sale);
		when(mock.find(Buyer.class, BUYER_EMAIL)).thenReturn(null);

		try {
			ProposedSale resultado = sut.createProposedSale(SALE_ID, BUYER_EMAIL, 9);

			assertNull(resultado);

		} catch (Exception e) {
			fail();
		}
	}
	
	@Test
	public void test6() {
		Sale sale = new Sale();
		Buyer buyer = new Buyer(BUYER_EMAIL, null, null, 10);
		when(mock.getTransaction()).thenReturn(tx);
		when(mock.find(Sale.class, SALE_ID)).thenReturn(sale);
		when(mock.find(Buyer.class, BUYER_EMAIL)).thenReturn(buyer);
 
		try {
			ProposedSale resultado = sut.createProposedSale(SALE_ID, BUYER_EMAIL, 11);
 
			assertNull(resultado);
			assertEquals(10, buyer.getSaldo(), 0.001);
			assertEquals(0, sale.getProposedSales().size());
 
		} catch (Exception e) {
			fail();
		}
	}
	
	@Test
	public void test7() {
		Sale sale = new Sale();
		Buyer buyer = new Buyer(BUYER_EMAIL, null, null, 10);
		when(mock.getTransaction()).thenReturn(tx);
		when(mock.find(Sale.class, SALE_ID)).thenReturn(sale);
		when(mock.find(Buyer.class, BUYER_EMAIL)).thenReturn(buyer);
 
		try {
			ProposedSale resultado = sut.createProposedSale(SALE_ID, BUYER_EMAIL, 9);
 
			assertNotNull(resultado);
			assertEquals(9, resultado.getPrice(), 0.001);
 
		} catch (Exception e) {
			fail();
		}
	}

}
