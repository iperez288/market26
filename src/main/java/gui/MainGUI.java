package gui;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import businessLogic.BLFacade;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Locale;
import java.util.ResourceBundle;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class MainGUI extends JFrame {

	private String tipoUsuario;
	private String email;
	private float saldo;
	
	private static final long serialVersionUID = 1L;

	private JPanel jContentPane = null;
	private JButton jButtonCreateQuery = null;
	private JButton jButtonQueryQueries = null;
	private JButton jButtonAddMoney = null;
	private JButton jButtonWithdrawMoney = null;
	private JLabel jLabelSaldo;

	private static BLFacade appFacadeInterface;

	public static BLFacade getBusinessLogic() {
		return appFacadeInterface;
	}

	public String getTipoUsuario() {
		return tipoUsuario;
	}

	public void setTipoUsuario(String tipoUsuario) {
		this.tipoUsuario = tipoUsuario;
	}

	public static void setBussinessLogic(BLFacade facade) {
		appFacadeInterface = facade;
	}

	protected JLabel jLabelSelectOption;
	private JRadioButton rdbtnNewRadioButton;
	private JRadioButton rdbtnNewRadioButton_1;
	private JRadioButton rdbtnNewRadioButton_2;
	private JPanel panel_idiomas;
	private JButton btnLogin;
	private JButton btnRegister;
	private JPanel user_panel;

	private JPanel panel_ventas;
	private JPanel panel_consultas;
	private JPanel panel_dinero;

	private JButton jButtonViewAcceptedSales;
	private JPanel panel_consultas_1;
	private JButton jButtonTransactionHistory=null;
	private JButton jButtonQueryPurchases;
	private JButton jButtonConversaciones;
	
	private JButton jButtonVerVentas;

	public MainGUI(String mail) {
		super();

		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
		}

		this.saldo=0.0f;
		this.tipoUsuario = "xxxxxx";
		this.setSize(600, 700);
		this.setLocationRelativeTo(null);

		rdbtnNewRadioButton = new JRadioButton("English");
		rdbtnNewRadioButton.addActionListener(e -> {
			Locale.setDefault(new Locale("en"));
			paintAgain();
		});
		rdbtnNewRadioButton_1 = new JRadioButton("Euskara");
		rdbtnNewRadioButton_1.addActionListener(e -> {
			Locale.setDefault(new Locale("eus"));
			paintAgain();
		});
		rdbtnNewRadioButton_2 = new JRadioButton("Castellano");
		rdbtnNewRadioButton_2.addActionListener(e -> {
			Locale.setDefault(new Locale("es"));
			paintAgain();
		});

		ButtonGroup group = new ButtonGroup();
		group.add(rdbtnNewRadioButton);
		group.add(rdbtnNewRadioButton_1);
		group.add(rdbtnNewRadioButton_2);

		panel_idiomas = new JPanel();
		panel_idiomas.setLayout(new FlowLayout(FlowLayout.CENTER));
		panel_idiomas.add(rdbtnNewRadioButton_1);
		panel_idiomas.add(rdbtnNewRadioButton_2);
		panel_idiomas.add(rdbtnNewRadioButton);

		user_panel = new JPanel();
		user_panel.setLayout(new GridLayout(1, 3, 10, 0));
		user_panel.setBorder(new EmptyBorder(10, 0, 10, 0));
		
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
		
		jLabelSaldo = new JLabel("Saldo: 0,00 €"); 
		jLabelSaldo.setHorizontalAlignment(SwingConstants.RIGHT);
		jLabelSaldo.setFont(new Font("Tahoma", Font.BOLD, 13));
		jLabelSaldo.setForeground(new Color(0, 102, 0));
		user_panel.add(jLabelSaldo);

		jLabelSelectOption = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("MainGUI.SelectOption"));
		jLabelSelectOption.setFont(new Font("Tahoma", Font.BOLD, 15));
		jLabelSelectOption.setHorizontalAlignment(SwingConstants.CENTER);
		jLabelSelectOption.setBorder(new EmptyBorder(15, 0, 15, 0));

		panel_ventas = new JPanel();
		panel_ventas.setLayout(new GridLayout(1, 2, 10, 10));
		panel_ventas.setBorder(new TitledBorder(null, "Operaciones de Venta", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		
		jButtonCreateQuery = new JButton(ResourceBundle.getBundle("Etiquetas").getString("MainGUI.CreateSale"));
		jButtonCreateQuery.setEnabled(false);
		jButtonCreateQuery.addActionListener(e -> {
			JFrame a = new CreateSaleGUI(email);
			a.setVisible(true);
		});
		panel_ventas.add(jButtonCreateQuery);
		
		jButtonViewAcceptedSales = new JButton("Ver ofertas aceptadas");
		jButtonViewAcceptedSales.setEnabled(false);
		jButtonViewAcceptedSales.addActionListener(e -> {
			JFrame a = new QueryProposedSalesGUI(email,this);
			a.setVisible(true);
		});
		panel_ventas.add(jButtonViewAcceptedSales);

		panel_consultas = new JPanel();
		panel_consultas.setLayout(new GridLayout(1, 2, 10, 10));
		panel_consultas.setBorder(new TitledBorder(null, "Consultas", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		
		jButtonQueryQueries = new JButton(ResourceBundle.getBundle("Etiquetas").getString("MainGUI.QuerySales"));
		jButtonQueryQueries.setEnabled(false);
		jButtonQueryQueries.addActionListener(e -> {
			JFrame a = new QuerySalesGUI(email);
			a.setVisible(true);
		});
		panel_consultas.add(jButtonQueryQueries);

		panel_dinero = new JPanel();
		panel_dinero.setLayout(new GridLayout(1, 2, 10, 10));
		panel_dinero.setBorder(new TitledBorder(null, "Cartera", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		
		jButtonAddMoney = new JButton("Agregar Saldo");
		jButtonAddMoney.setEnabled(false);
		jButtonAddMoney.addActionListener(e -> {
			JFrame a = new AddSaldoGUI(MainGUI.this, email);
			a.setVisible(true);
		});
		panel_dinero.add(jButtonAddMoney);

		jButtonWithdrawMoney = new JButton("Retirar Saldo");
		jButtonWithdrawMoney.setEnabled(false);
		jButtonWithdrawMoney.addActionListener(e -> {
			JFrame a = new RetirarSaldoGUI(MainGUI.this,email);
			a.setVisible(true);
		});
		panel_dinero.add(jButtonWithdrawMoney);

		jContentPane = new JPanel();
		jContentPane.setLayout(new BoxLayout(jContentPane, BoxLayout.Y_AXIS));
		jContentPane.setBorder(new EmptyBorder(20, 20, 20, 20));
		
		jContentPane.add(user_panel);
		jContentPane.add(jLabelSelectOption);
		jContentPane.add(panel_ventas);
		jContentPane.add(Box.createVerticalStrut(10));
		jContentPane.add(panel_consultas);
		
		jButtonConversaciones = new JButton("Ver Conversaciones");
		jButtonConversaciones.setEnabled(false);
		jButtonConversaciones.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFrame transactions = new ConsultarConversacionesGUI(email);
				transactions.setVisible(true);
			}
		});
		panel_consultas.add(jButtonConversaciones);
		
		panel_consultas_1 = new JPanel();
		panel_consultas.add(panel_consultas_1);
		panel_consultas_1.setLayout(new GridLayout(3, 1, 5, 5));
		
		jButtonVerVentas = new JButton("Ver Ventas");
		jButtonVerVentas.setEnabled(false);
		jButtonVerVentas.addActionListener(e -> {
			JFrame a = new VerVentasGUI(email);
			a.setVisible(true);
		});
		panel_consultas_1.add(jButtonVerVentas);

		jButtonQueryPurchases = new JButton("Ver compras");
		jButtonQueryPurchases.setEnabled(false);
		jButtonQueryPurchases.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				JFrame compras = new QueryPurchasesGUI(email);
				compras.setVisible(true);
			}
		});
		panel_consultas_1.add(jButtonQueryPurchases);
		
		jButtonTransactionHistory = new JButton("Historial Transacciones");
		jButtonTransactionHistory.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				JFrame transactions = new TransactionHistoryGUI(email);
				transactions.setVisible(true);
			}
		});
		jButtonTransactionHistory.setEnabled(false);
		panel_consultas_1.add(jButtonTransactionHistory);
		
		jContentPane.add(Box.createVerticalStrut(10));
		jContentPane.add(panel_dinero);
		jContentPane.add(Box.createVerticalGlue());
		jContentPane.add(panel_idiomas);

		setContentPane(jContentPane);
		setTitle(ResourceBundle.getBundle("Etiquetas").getString("MainGUI.MainTitle") + ": " + tipoUsuario);

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
		this.setTitle(ResourceBundle.getBundle("Etiquetas").getString("MainGUI.MainTitle") + ": "
				+ this.email + " (" + tipoUsuario + ")");
		jLabelSaldo.setText("Saldo: " + String.format("%.2f", saldo) + "€");
		
	}

	public void actualizarSaldo() {
		this.saldo = appFacadeInterface.getSaldo(email);
		paintAgain();
	}

	public void gestionPermisos() {
		boolean esComprador = tipoUsuario.equals("Comprador");
		this.jButtonQueryQueries.setEnabled(true);
		this.jButtonAddMoney.setEnabled(true);
		this.jButtonWithdrawMoney.setEnabled(true);
		this.jButtonTransactionHistory.setEnabled(true);
		this.jButtonQueryPurchases.setEnabled(true);
		
		this.jButtonConversaciones.setEnabled(true);

		if (esComprador) {
			this.jButtonCreateQuery.setEnabled(false);
			this.jButtonViewAcceptedSales.setEnabled(false);
			this.jButtonVerVentas.setEnabled(false);
		} else {
			this.jButtonCreateQuery.setEnabled(true);
			this.jButtonViewAcceptedSales.setEnabled(true);
			this.jButtonVerVentas.setEnabled(true);
		}
		actualizarSaldo();
		paintAgain();
	}
	
	public void setEmail(String email) {
		this.email=email;
	}
}