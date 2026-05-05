package gui;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import businessLogic.BLFacade;
import domain.Sale;

import javax.swing.JTextPane;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class CrearConversacionGUI extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField temaField;
	private JTextPane messagePane;
	private JButton btnCrear;
	private JButton btnCancelar;
	private String email;
	private Sale s;

	
	/**
	 * Launch the application.
	 */
	/*public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					CrearConversacionGUI frame = new CrearConversacionGUI();
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
	public CrearConversacionGUI(String email, Sale s) {
		this.s = s;
		this.email=email;
		JFrame thisFrame = this;
		setTitle("Iniciar Conversaci\u00F3n");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 418, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		messagePane = new JTextPane();
		messagePane.setBounds(57, 59, 274, 159);
		contentPane.add(messagePane);
		
		temaField = new JTextField();
		temaField.setBounds(120, 10, 211, 18);
		contentPane.add(temaField);
		temaField.setColumns(10);
		
		JLabel lblNewLabel = new JLabel("Tema:");
		lblNewLabel.setBounds(66, 13, 44, 13);
		contentPane.add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("Mensaje:");
		lblNewLabel_1.setBounds(57, 37, 44, 12);
		contentPane.add(lblNewLabel_1);
		
		btnCrear = new JButton("Crear");
		btnCrear.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				String tema = temaField.getText();
				String mensaje = messagePane.getText();
				
				if(tema.equals("") || mensaje.equals("")) {
					JOptionPane.showMessageDialog(null, "Rellene todo los campos.");
				}else {
					
					BLFacade facade = MainGUI.getBusinessLogic();
					
					facade.iniciarConversacion(tema, s, email, mensaje);
					JOptionPane.showMessageDialog(null, "Conversación creada con exito.");
					thisFrame.dispose();
				}
				
			}
		});
		btnCrear.setBounds(93, 228, 84, 20);
		contentPane.add(btnCrear);
		
		btnCancelar = new JButton("Cancelar");
		btnCancelar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				thisFrame.dispose();
			}
		});
		btnCancelar.setBounds(208, 228, 84, 20);
		contentPane.add(btnCancelar);

	}
}
