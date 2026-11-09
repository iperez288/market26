package gui;

import java.awt.Color;
import java.util.Locale;

import javax.swing.UIManager;

import businessLogic.BLFacade;
import businessLogic.BLFactory;
import configuration.ConfigXML;

public class ApplicationLauncher { 
	
	private static final BLFactory blFactory=new BLFactory();
	
	public static void main(String[] args) {

		ConfigXML c=ConfigXML.getInstance();		
		Locale.setDefault(new Locale(c.getLocale()));
		MainGUI a=new MainGUI("seller3@gmail.com");
		a.setVisible(true);

		try {
			
			UIManager.setLookAndFeel("javax.swing.plaf.metal.MetalLookAndFeel");

			BLFacade appFacadeInterface=blFactory.getBusinessLogicFactory(c.isBusinessLogicLocal());
			
			MainGUI.setBussinessLogic(appFacadeInterface);
			
		}catch (Exception e) {
			a.jLabelSelectOption.setText("Error: "+e.toString());
			a.jLabelSelectOption.setForeground(Color.RED);	
			
			System.out.println("Error in ApplicationLauncher: "+e.toString());
		}
	}
}
