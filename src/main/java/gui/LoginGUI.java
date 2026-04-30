package gui;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import businessLogic.BLFacade;

import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JPasswordField;
import javax.swing.JLabel;
import java.awt.event.ActionListener;
import java.util.ResourceBundle;
import java.awt.event.ActionEvent;
import javax.swing.JOptionPane;

public class LoginGUI extends JFrame {

	private JPanel contentPane;
	private JTextField emailField;
	private JPasswordField passwordField;
	private JButton btnLogin;
	private JFrame thisFrame;

	
	/**
	 * Create the frame.
	 */
	public LoginGUI(MainGUI parent) {
		
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 365, 332);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		this.setTitle("Iniciar sesion");
		
		emailField = new JTextField();
		emailField.setBounds(182, 29, 116, 25);
		contentPane.add(emailField);
		emailField.setColumns(10);
		
		thisFrame = this;
		
		
		btnLogin = new JButton("Iniciar Sesión");
		btnLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
	BLFacade facade = MainGUI.getBusinessLogic();
				
				String email = emailField.getText();
				String pass = new String(passwordField.getPassword());
				if(email.equals("") || pass.equals("")) {
					
					JOptionPane.showMessageDialog(null, "Rellene todo los campos.");
					
				}else {
					int  tipo = facade.makeLogin(email, pass);
					
					
					if (tipo!=0) {
						JOptionPane.showMessageDialog(null, "Ha iniciado sesión correctamente.");
						
						thisFrame.setVisible(false);
						
						parent.setEmail(email);
						if(tipo==1) parent.setTipoUsuario("Comprador");
						else parent.setTipoUsuario("Vendedor");	
						
						//parent.setTitle(ResourceBundle.getBundle("Etiquetas").getString("MainGUI.MainTitle")+ ": "+ email+" ("+parent.getTipoUsuario()+")");
						parent.gestionPermisos();
					}else {
						JOptionPane.showMessageDialog(null,"Usuario o contrasena incorrectos.");
					}
				}
				
			}
		});
		btnLogin.setBounds(122, 127, 123, 43);
		contentPane.add(btnLogin);
		
		passwordField = new JPasswordField();
		passwordField.setBounds(182, 70, 116, 25);
		contentPane.add(passwordField);
		
		
		JLabel lblEmail = new JLabel("Email:");
		lblEmail.setBounds(79, 33, 56, 16);
		contentPane.add(lblEmail);
		
		JLabel lblPassword = new JLabel("Contraseña:");
		lblPassword.setBounds(79, 74, 77, 16);
		contentPane.add(lblPassword);
	}

}
