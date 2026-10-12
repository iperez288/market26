package createProposedSaleTests;

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

public class CreateProposedSaleMockWhiteTest {

	private EntityManager mock = Mockito.mock(EntityManager.class);
	private EntityTransaction tx = Mockito.mock(EntityTransaction.class);

	private DataAccess sut = new DataAccess(mock);

	private static final Integer SALE_ID = 30;
	private static final String BUYER_EMAIL = "buyerTest@gmail.com";

	@Test
	public void test1() {
		when(mock.getTransaction()).thenReturn(tx);
		when(mock.find(Sale.class, SALE_ID)).thenReturn(null);
		when(mock.find(Buyer.class, BUYER_EMAIL)).thenReturn(null);

		try {
			ProposedSale resultado = sut.createProposedSale(SALE_ID, BUYER_EMAIL, 8);

			assertNull(resultado);

		} catch (Exception e) {
			fail();
		}
	}

	@Test
	public void test2() {
		Sale sale = new Sale();
		Buyer buyer = new Buyer(BUYER_EMAIL, null, null, 10);
		when(mock.getTransaction()).thenReturn(tx);
		when(mock.find(Sale.class, SALE_ID)).thenReturn(sale);
		when(mock.find(Buyer.class, BUYER_EMAIL)).thenReturn(buyer);

		try {
			ProposedSale resultado = sut.createProposedSale(SALE_ID, BUYER_EMAIL, 15);

			assertNull(resultado);

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
			ProposedSale resultado = sut.createProposedSale(SALE_ID, BUYER_EMAIL, 8);

			assertNotNull(resultado);


		} catch (Exception e) {
			fail();
		}
	}

}
