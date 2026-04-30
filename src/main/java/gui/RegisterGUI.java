package gui;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import businessLogic.BLFacade;

import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.JLabel;
import javax.swing.JRadioButton;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JOptionPane;


public class RegisterGUI extends JFrame {

	private JPanel contentPane;
	private JTextField txtEmail;
	private JTextField txtName;
	private JPasswordField pwdPassword;
	private final ButtonGroup buttonGroup = new ButtonGroup();
	private JRadioButton rdbtnVendedor;
	private JRadioButton rdbtnComprador;
	private JButton btnRegister;
	private JFrame thisFrame;


	
	
	/**
	 * Launch the application.
	 */
	/*public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					RegisterGUI frame = new RegisterGUI();
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
	public RegisterGUI(MainGUI parent) {
		
		thisFrame = this;
		
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 460, 379);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		this.setTitle("Registrarse");
		
		txtEmail = new JTextField();
		txtEmail.setBounds(177, 44, 116, 22);
		contentPane.add(txtEmail);
		txtEmail.setColumns(10);
		
		txtName = new JTextField();
		txtName.setBounds(177, 79, 116, 22);
		contentPane.add(txtName);
		txtName.setColumns(10);
		
		pwdPassword = new JPasswordField();
		pwdPassword.setBounds(177, 114, 116, 28);
		contentPane.add(pwdPassword);
		
		JLabel lblEmail = new JLabel("Email:");
		lblEmail.setBounds(73, 47, 56, 16);
		contentPane.add(lblEmail);
		
		JLabel lblName = new JLabel("Nombre:");
		lblName.setBounds(59, 82, 56, 16);
		contentPane.add(lblName);
		
		JLabel lblPassword = new JLabel("Contraseña:");
		lblPassword.setBounds(59, 120, 70, 16);
		contentPane.add(lblPassword);
		
		rdbtnVendedor = new JRadioButton("Vendedor");
		buttonGroup.add(rdbtnVendedor);
		rdbtnVendedor.setBounds(78, 164, 127, 25);
		contentPane.add(rdbtnVendedor);
		
		rdbtnComprador = new JRadioButton("Comprador");
		buttonGroup.add(rdbtnComprador);
		rdbtnComprador.setBounds(209, 164, 127, 25);
		contentPane.add(rdbtnComprador);
		
		btnRegister = new JButton("Registrarse");
		btnRegister.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				BLFacade facade = MainGUI.getBusinessLogic();
				
				String email = txtEmail.getText();
				String name = txtName.getText();
				String pass = new String(pwdPassword.getPassword());
				if(email.equals("") || name.equals("") || pass.equals("")) {
					
					JOptionPane.showMessageDialog(null, "Rellene todo los campos.");
				}else {
					if(!rdbtnVendedor.isSelected()&&!rdbtnComprador.isSelected()) {
						JOptionPane.showMessageDialog(null, "Seleccione un tipo de usuario.");
					}
					else{
						int  anadido;
						boolean seller = rdbtnVendedor.isSelected();
						anadido=facade.createAccount(email,name,pass,seller);
						if(anadido==0) {
							JOptionPane.showMessageDialog(null, "Email ya registrado.");
							
							
						}else {
							
							if(anadido==1)parent.setTipoUsuario("Comprador");
							else parent.setTipoUsuario("Vendedor");						
								
							JOptionPane.showMessageDialog(null, "Registrado con exito.");
							
							thisFrame.setVisible(false);
							//parent.setTitle(ResourceBundle.getBundle("Etiquetas").getString("MainGUI.MainTitle")+ ": "+ email+" ("+parent.getTipoUsuario()+")");
							parent.setEmail(email);
							parent.gestionPermisos();
						}
					}
				}
				
				
			}
		});
		btnRegister.setBounds(127, 208, 166, 44);
		contentPane.add(btnRegister);
	}
}
