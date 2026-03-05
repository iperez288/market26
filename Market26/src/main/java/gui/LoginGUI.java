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
import javax.swing.JTextArea;
import javax.swing.JLabel;
import java.awt.event.ActionListener;
import java.util.ResourceBundle;
import java.awt.event.ActionEvent;

public class LoginGUI extends JFrame {

	private JPanel contentPane;
	private JTextField emailField;
	private JPasswordField passwordField;
	private JTextArea textArea;
	private JButton btnLogin;

	/**
	 * Launch the application.
	 */
	/*public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					LoginGUI frame = new LoginGUI();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}*/

	
	/**
	 * Create the frame.
	 */
	public LoginGUI(MainGUI parent) {
		
		setBounds(100, 100, 365, 332);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		this.setTitle("Iniciar sesión");
		
		emailField = new JTextField();
		emailField.setBounds(182, 29, 116, 25);
		contentPane.add(emailField);
		emailField.setColumns(10);
		
		btnLogin = new JButton("Iniciar Sesión");
		btnLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
	BLFacade facade = MainGUI.getBusinessLogic();
				
				String email = emailField.getText();
				String pass = new String(passwordField.getPassword());
				if(email.equals("") || pass.equals("")) {
					
					textArea.setText("Rellene todo los campos.");
				}else {
					int  tipo = facade.makeLogin(email, pass);
					if (tipo!=0) {
						textArea.setText("Ha iniciado sesión correctamente.\nPuede cerrar la ventana.");
						
						if(tipo==1) parent.setTipoUsuario("Usuario");
						else parent.setTipoUsuario("Vendedor");	
						
						parent.setTitle(ResourceBundle.getBundle("Etiquetas").getString("MainGUI.MainTitle")+ ": "+ facade.getUsuario().getEmail()+" ("+parent.getTipoUsuario()+")");
						parent.gestionPermisos();
					}else {
						textArea.setText("Usuario o contraseña incorrectos.");
					}
				}
				
			}
		});
		btnLogin.setBounds(122, 127, 123, 43);
		contentPane.add(btnLogin);
		
		passwordField = new JPasswordField();
		passwordField.setBounds(182, 70, 116, 25);
		contentPane.add(passwordField);
		
		textArea = new JTextArea();
		textArea.setBounds(104, 194, 161, 54);
		contentPane.add(textArea);
		
		JLabel lblEmail = new JLabel("Email:");
		lblEmail.setBounds(79, 33, 56, 16);
		contentPane.add(lblEmail);
		
		JLabel lblPassword = new JLabel("Contraseña:");
		lblPassword.setBounds(79, 74, 77, 16);
		contentPane.add(lblPassword);
	}

}
