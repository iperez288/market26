package createProposedSaleTests;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.fail;

import org.junit.Test;

import data_access.DataAccess;
import domain.ProposedSale;
import domain.Sale;

public class CreateProposedSaleBDWhiteTest {

	private TestDataAccessCreateProposedSale testDA = new TestDataAccessCreateProposedSale();
	private DataAccess sut = new DataAccess();

	@Test
	public void test1() {

		try {
			sut.open();
			ProposedSale resultado = sut.createProposedSale(30, "buyerTest@gmail.com", 8);
			sut.close();

			assertNull(resultado);

		} catch (Exception e) {
			fail();
		}
	}

	@Test
	public void test2() {
		testDA.open();
		testDA.createBuyer("buyerTest@gmail.com", 10);
		Sale sale = testDA.createSale();
		testDA.close();

		int saleId = sale.getSaleNumber();

		try {
			sut.open();
			ProposedSale resultado = sut.createProposedSale(saleId, "buyerTest@gmail.com", 15);

			assertNull(resultado);

		} catch (Exception e) {
			fail();
		} finally {
			testDA.open();
			testDA.removeBuyer("buyerTest@gmail.com");
			testDA.removeSale(saleId);
			testDA.close();
		}
	}
	
	
	@Test
	public void test3() {
	    testDA.open();
	    testDA.createBuyer("buyer1@gmail.com", 10);
	    Sale sale = testDA.createSale();
	    testDA.close();

	    int saleId = sale.getSaleNumber();
	    ProposedSale resultado = null;

	    try {
	        sut.open();
	        resultado = sut.createProposedSale(saleId, "buyer1@gmail.com", 8);
	        assertNotNull(resultado);


	    } catch (Exception e) {
	        fail();
	    } finally {
	    	testDA.open();
	    	testDA.removeProposedSale(saleId);
			testDA.removeBuyer("buyerTest@gmail.com");
			testDA.removeSale(saleId);
			testDA.close();
	    }
	}
}
