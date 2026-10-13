package retirarSaldoMockTest;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import org.junit.Test;
import org.mockito.Mockito;

import data_access.DataAccess;
import domain.Buyer;
import domain.Transaction;
import domain.User;


public class RetirarSaldoDBBlackTest {
	private EntityManager mock = Mockito.mock(EntityManager.class);
    private EntityTransaction tx = Mockito.mock(EntityTransaction.class);
    private DataAccess sut = new DataAccess(mock);
    
    //fail en el mail
    @Test
    public void test1() {
        float importe = 50.0f;
        String emailFalso = "test1@gmail.com";

       
        when(mock.find(User.class, emailFalso));
        sut.retirarSaldo(emailFalso, importe);
    
 }
//importe negativo
    @Test
    public void test2() {
        float importeNegativo = -20.0f;
      
        when(mock.find(User.class, "test2@gmail.com"));
        sut.retirarSaldo("test2@gmail.com", importeNegativo);
    }




}



