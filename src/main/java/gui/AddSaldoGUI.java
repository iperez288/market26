package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import businessLogic.BLFacade;

public class AddSaldoGUI extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField textFieldImporte;
	private JLabel lblMensaje;
	private MainGUI mainFrame;

	public AddSaldoGUI(MainGUI main) {
		this.mainFrame = main;
		setTitle("Añadir Saldo");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 300, 200);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel lblTitulo = new JLabel("Introduce el importe:");
		lblTitulo.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblTitulo.setBounds(30, 20, 200, 20);
		contentPane.add(lblTitulo);

		textFieldImporte = new JTextField();
		textFieldImporte.setBounds(30, 50, 220, 30);
		contentPane.add(textFieldImporte);
		textFieldImporte.setColumns(10);

		JButton btnAceptar = new JButton("Confirmar");
		btnAceptar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					float importe = Float.parseFloat(textFieldImporte.getText());
					if (importe <= 0) throw new NumberFormatException();

					BLFacade facade = MainGUI.getBusinessLogic();
					String email = facade.getUsuario().getEmail();
					
					facade.annadirSaldo(email, importe); 
					
					mainFrame.actualizarSaldo();
					dispose();
					
				} catch (NumberFormatException ex) {
					lblMensaje.setText("Importe no válido");
					lblMensaje.setForeground(Color.RED);
				}
			}
		});
		btnAceptar.setBounds(85, 90, 110, 30);
		contentPane.add(btnAceptar);

		lblMensaje = new JLabel("");
		lblMensaje.setHorizontalAlignment(SwingConstants.CENTER);
		lblMensaje.setBounds(30, 130, 220, 20);
		contentPane.add(lblMensaje);
		
		this.setLocationRelativeTo(null);
	}
}