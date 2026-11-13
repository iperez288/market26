package gui;

import businessLogic.BLFacade;
import businessLogic.BLFactory;
import domain.Seller;

public class SellerTableMain {

    public static void main(String[] args) {
        // la lógica de negocio es local
        boolean isLocal = true;
        BLFacade blFacade = new BLFactory().getBusinessLogicFactory(isLocal);

        Seller s = blFacade.isLogged("seller1@gmail.com", "1234");
        if (s == null) {
            System.out.println("Login incorrecto: revisa email y password");
            return;
        }

        SellerTable st = new SellerTable(s);
        st.setVisible(true);
    }
}