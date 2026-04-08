package gui;

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
import domain.Buyer;

public class RetirarSaldoGUI extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField textFieldImporte;
	private MainGUI main;

	public RetirarSaldoGUI(MainGUI main) {
		this.main = main;
		setTitle("Retirar Saldo");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setSize(350, 250);
		setLocationRelativeTo(null);

		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel lblTitulo = new JLabel("Retirar dinero");
		lblTitulo.setFont(new Font("Tahoma", Font.BOLD, 16));
		lblTitulo.setHorizontalAlignment(SwingConstants.CENTER);
		lblTitulo.setBounds(10, 20, 314, 25);
		contentPane.add(lblTitulo);

		JLabel lblImporte = new JLabel("Importe a retirar (€):");
		lblImporte.setBounds(40, 70, 150, 14);
		contentPane.add(lblImporte);

		textFieldImporte = new JTextField();
		textFieldImporte.setBounds(190, 67, 86, 20);
		contentPane.add(textFieldImporte);
		textFieldImporte.setColumns(10);

		JLabel lblError = new JLabel("");
		lblError.setHorizontalAlignment(SwingConstants.CENTER);
		lblError.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblError.setForeground(java.awt.Color.RED);
		lblError.setBounds(10, 100, 314, 20);
		contentPane.add(lblError);

		JButton btnRetirar = new JButton("Confirmar Retiro");
		btnRetirar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				lblError.setText("");
				try {
					float importeARetirar = Float.parseFloat(textFieldImporte.getText());
					BLFacade facade = MainGUI.getBusinessLogic();
					Buyer b = (Buyer) facade.getUsuario();

					if (importeARetirar <= 0) {
						lblError.setText("Introduce un importe positivo.");
					} else if (importeARetirar > b.getSaldo()) {
						lblError.setText("Saldo insuficiente (Saldo: " + b.getSaldo() + "€)");
					} else {

						facade.retirarSaldo(b.getEmail(), importeARetirar);

						main.actualizarSaldo();

						dispose();
					}
				} catch (NumberFormatException ex) {
					lblError.setText("Por favor, introduce un número válido.");
				}
			}
		});
		btnRetirar.setBounds(90, 140, 150, 30);
		contentPane.add(btnRetirar);
	}
}