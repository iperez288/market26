package gui;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
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

	public RetirarSaldoGUI(MainGUI main, String email) {
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


		JButton btnRetirar = new JButton("Confirmar Retiro");
		btnRetirar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					float importeARetirar = Float.parseFloat(textFieldImporte.getText());
					BLFacade facade = MainGUI.getBusinessLogic();
					float saldo = facade.getSaldo(email);

					if (importeARetirar <= 0) {
						JOptionPane.showMessageDialog(null, "Introduce un importe positivo.");
					} else if (importeARetirar > saldo) {
						JOptionPane.showMessageDialog(null, "Saldo insuficiente (Saldo: " + saldo + "€)");
					} else {

						facade.retirarSaldo(email, importeARetirar);

						main.actualizarSaldo();
						
						JOptionPane.showMessageDialog(null, "Has sacado " + importeARetirar + "€ de tu saldo");

						dispose();
					}
				} catch (NumberFormatException ex) {
					JOptionPane.showMessageDialog(null, "Por favor, introduce un numero valido");
				}
			}
		});
		btnRetirar.setBounds(90, 140, 150, 30);
		contentPane.add(btnRetirar);
	}
}