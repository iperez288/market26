package createProposedSaleTests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.fail;

import org.junit.Test;

import data_access.DataAccess;
import domain.ProposedSale;
import domain.Sale;

public class CreateProposedSaleBDBlackTest {

	private TestDataAccessCreateProposedSale testDA = new TestDataAccessCreateProposedSale();
	private DataAccess sut = new DataAccess();

	private static final String BUYER = "buyerTest@gmail.com";

	@Test
	public void test1() {

		testDA.open();
		testDA.createBuyer(BUYER, 10);
		testDA.close();

		try {
			sut.open();
			ProposedSale resultado = sut.createProposedSale(-1, BUYER, 9);

			assertNull(resultado);

		} catch (Exception e) {
			fail();
		} finally {
			sut.close();
			testDA.open();
			testDA.removeBuyer(BUYER);
			testDA.close();
		}
	}

	@Test
	public void test2() {

		testDA.open();
		testDA.createBuyer(BUYER, 10);
		Sale sale = testDA.createSale();
		testDA.close();

		int saleID = sale.getSaleNumber();

		try {
			sut.open();
			ProposedSale resultado = sut.createProposedSale(saleID, null, 9);

			assertNull(resultado);

		} catch (Exception e) {
			fail();
		} finally {
			sut.close();
			testDA.open();
			testDA.removeProposedSale(saleID);
			testDA.removeBuyer(BUYER);
			testDA.removeSale(saleID);
			testDA.close();
		}
	}

	@Test
	public void test3() {

		testDA.open();
		testDA.createBuyer(BUYER, 10);
		Sale sale = testDA.createSale();
		testDA.close();

		int saleID = sale.getSaleNumber();

		try {
			sut.open();
			ProposedSale resultado = sut.createProposedSale(saleID, BUYER, -5);

			assertNull(resultado);

		} catch (Exception e) {
			fail();
		} finally {
			sut.close();
			testDA.open();
			testDA.removeProposedSale(saleID);
			testDA.removeBuyer(BUYER);
			testDA.removeSale(saleID);
			testDA.close();
		}
	}

	@Test
	public void test4() {

		try {
			sut.open();
			ProposedSale resultado = sut.createProposedSale(-1, BUYER, 9);

			assertNull(resultado);

			testDA.open();
			assertNull(testDA.getBuyer(BUYER));
			testDA.close();

		} catch (Exception e) {
			fail();
		} finally {
			sut.close();
		}
	}

	@Test
	public void test5() {

		testDA.open();
		Sale sale = testDA.createSale();
		testDA.close();

		int saleID = sale.getSaleNumber();

		try {
			sut.open();
			ProposedSale resultado = sut.createProposedSale(saleID, BUYER, 9);

			assertNull(resultado);

		} catch (Exception e) {
			fail();
		} finally {
			sut.close();
			testDA.open();
			testDA.removeSale(saleID);
			testDA.close();
		}
	}

	@Test
	public void test6() {

		testDA.open();
		testDA.createBuyer(BUYER, 10);
		Sale sale = testDA.createSale();
		testDA.close();

		int saleID = sale.getSaleNumber();

		try {
			sut.open();
			ProposedSale resultado = sut.createProposedSale(saleID, BUYER, 11);

			assertNull(resultado);

			testDA.open();
			assertEquals(10, testDA.getBuyer(BUYER).getSaldo(), 0.001);
			assertEquals(0, testDA.getSale(saleID).getProposedSales().size());
			testDA.close();

		} catch (Exception e) {
			fail();
		} finally {
			sut.close();
			testDA.open();
			testDA.removeProposedSale(saleID);
			testDA.removeBuyer(BUYER);
			testDA.removeSale(saleID);
			testDA.close();
		}
	}

	@Test
	public void test7() {

		testDA.open();
		testDA.createBuyer(BUYER, 10);
		Sale sale = testDA.createSale();
		testDA.close();

		int saleID = sale.getSaleNumber();

		try {
			sut.open();
			ProposedSale resultado = sut.createProposedSale(saleID, BUYER, 9);

			assertNotNull(resultado);
			assertEquals(9, resultado.getPrice(), 0.001);


		} catch (Exception e) {
			fail();
		} finally {
			sut.close();
			testDA.open();
			testDA.removeProposedSale(saleID);
			testDA.removeBuyer(BUYER);
			testDA.removeSale(saleID);
			testDA.close();
		}
	}

}