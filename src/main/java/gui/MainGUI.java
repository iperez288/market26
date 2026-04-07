package gui;

import javax.swing.*;
import businessLogic.BLFacade;
import domain.Buyer;
import domain.User;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Locale;
import java.util.ResourceBundle;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.FlowLayout;

public class MainGUI extends JFrame {
	
    private String tipoUsuario; 
	private static final long serialVersionUID = 1L;

	private JPanel jContentPane = null;
	private JButton jButtonCreateQuery = null;
	private JButton jButtonQueryQueries = null;
	private JButton jButtonAddMoney = null;
	private JLabel jLabelSaldo;

    private static BLFacade appFacadeInterface;
	
	public static BLFacade getBusinessLogic(){
		return appFacadeInterface;
	}
		
	public String getTipoUsuario() {
		return tipoUsuario;
	}

	public void setTipoUsuario(String tipoUsuario) {
		this.tipoUsuario = tipoUsuario;
	}

	public static void setBussinessLogic (BLFacade facade){
		appFacadeInterface=facade;
	}
	
	protected JLabel jLabelSelectOption;
	private JRadioButton rdbtnNewRadioButton;
	private JRadioButton rdbtnNewRadioButton_1;
	private JRadioButton rdbtnNewRadioButton_2;
	private JPanel panel;
	private final ButtonGroup buttonGroup = new ButtonGroup();
	private JButton btnLogin;
	private JButton btnRegister;
	private JPanel user_panel;
	private JPanel seller_panel;
	private JPanel buyer_panel;
	private JButton jButtonViewAcceptedSales;
	
	public MainGUI(String mail) {
		super();

		this.tipoUsuario="xxxxxx";
		this.setSize(495, 495);
		
		// --- CONFIGURACIÓN DE IDIOMAS ---
		rdbtnNewRadioButton = new JRadioButton("English");
		rdbtnNewRadioButton.addActionListener(e -> { Locale.setDefault(new Locale("en")); paintAgain(); });
		
		rdbtnNewRadioButton_1 = new JRadioButton("Euskara");
		rdbtnNewRadioButton_1.addActionListener(e -> { Locale.setDefault(new Locale("eus")); paintAgain(); });
		
		rdbtnNewRadioButton_2 = new JRadioButton("Castellano");
		rdbtnNewRadioButton_2.addActionListener(e -> { Locale.setDefault(new Locale("es")); paintAgain(); });
	
		panel = new JPanel();
		panel.add(rdbtnNewRadioButton_1);
		panel.add(rdbtnNewRadioButton_2);
		panel.add(rdbtnNewRadioButton);
		
		user_panel = new JPanel();
		user_panel.setLayout(new GridLayout(0, 3, 0, 0));
		
		btnRegister = new JButton("Registrarse");
		btnRegister.addActionListener(arg0 -> {
			JFrame a = new RegisterGUI(MainGUI.this);
			a.setVisible(true);
		});
		user_panel.add(btnRegister);
		
		btnLogin = new JButton("Iniciar sesión");
		btnLogin.addActionListener(e -> {
			JFrame a = new LoginGUI(MainGUI.this);
			a.setVisible(true);
		});
		user_panel.add(btnLogin);

		jLabelSaldo = new JLabel("Saldo: 0.00 �");
		jLabelSaldo.setHorizontalAlignment(SwingConstants.RIGHT);
		jLabelSaldo.setFont(new Font("Tahoma", Font.BOLD, 12));
		user_panel.add(jLabelSaldo);
		
		jLabelSelectOption = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("MainGUI.SelectOption"));
		jLabelSelectOption.setFont(new Font("Tahoma", Font.BOLD, 13));
		jLabelSelectOption.setHorizontalAlignment(SwingConstants.CENTER);
		
		seller_panel = new JPanel();
		seller_panel.setLayout(new GridLayout(0, 2, 0, 0));
		
		jButtonCreateQuery = new JButton(ResourceBundle.getBundle("Etiquetas").getString("MainGUI.CreateSale"));
		jButtonCreateQuery.setEnabled(false);
		jButtonCreateQuery.addActionListener(e -> {
			JFrame a = new CreateSaleGUI(appFacadeInterface.getUsuario().getEmail());
			a.setVisible(true);
		});
		seller_panel.add(jButtonCreateQuery);
		
		jButtonViewAcceptedSales = new JButton("Ver ofertas aceptadas");
		jButtonViewAcceptedSales.setEnabled(false);
		jButtonViewAcceptedSales.addActionListener(e -> {
			JFrame a = new QueryProposedSalesGUI();
			a.setVisible(true);
		});
		seller_panel.add(jButtonViewAcceptedSales);

		buyer_panel = new JPanel();
		buyer_panel.setLayout(new GridLayout(0, 2, 0, 0));
		jButtonQueryQueries = new JButton(ResourceBundle.getBundle("Etiquetas").getString("MainGUI.QuerySales"));
		jButtonQueryQueries.setEnabled(false);
		jButtonQueryQueries.addActionListener(e -> {
			JFrame a = new QuerySalesGUI();
			a.setVisible(true);
		});
		buyer_panel.add(jButtonQueryQueries);

		jButtonAddMoney = new JButton("Agregar Saldo");
		jButtonAddMoney.setEnabled(false);
		jButtonAddMoney.addActionListener(e -> {
			System.out.println("Abriendo ventana de añadir saldo...");
			JFrame a = new AddSaldoGUI(MainGUI.this);
			a.setVisible(true);
		});
		buyer_panel.add(jButtonAddMoney);
		
		jContentPane = new JPanel();
		jContentPane.setLayout(new GridLayout(5, 1, 0, 0));
		jContentPane.add(user_panel);
		jContentPane.add(jLabelSelectOption);
		jContentPane.add(seller_panel);
		jContentPane.add(buyer_panel);
		jContentPane.add(panel);
		
		setContentPane(jContentPane);
		setTitle(ResourceBundle.getBundle("Etiquetas").getString("MainGUI.MainTitle") +": "+tipoUsuario);
		
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				System.exit(1);
			}
		});
	}
	
	private void paintAgain() {
		jLabelSelectOption.setText(ResourceBundle.getBundle("Etiquetas").getString("MainGUI.SelectOption"));
		jButtonQueryQueries.setText(ResourceBundle.getBundle("Etiquetas").getString("MainGUI.QuerySales"));
		jButtonCreateQuery.setText(ResourceBundle.getBundle("Etiquetas").getString("MainGUI.CreateSale"));
		this.setTitle(ResourceBundle.getBundle("Etiquetas").getString("MainGUI.MainTitle")+ ": "+appFacadeInterface.getUsuario().getEmail() +" ("+tipoUsuario+")");
		actualizarSaldo();
	}

	public void actualizarSaldo() {
		if (appFacadeInterface.getUsuario() != null) {
			User u = appFacadeInterface.getUsuario();
			float saldo = ((Buyer) u).getSaldo();
			jLabelSaldo.setText("Saldo: " + String.format("%.2f", saldo) + "€");
		}
	}
	
	public void gestionPermisos() {
		boolean esComprador = tipoUsuario.equals("Comprador");
		this.jButtonQueryQueries.setEnabled(true);
		this.jButtonAddMoney.setEnabled(esComprador || tipoUsuario.equals("Vendedor")); 
		
		if (esComprador) {
			this.jButtonCreateQuery.setEnabled(false);
			this.jButtonViewAcceptedSales.setEnabled(false);
		} else {
			this.jButtonCreateQuery.setEnabled(true);
			this.jButtonViewAcceptedSales.setEnabled(true);
		}
		actualizarSaldo();
	}
}