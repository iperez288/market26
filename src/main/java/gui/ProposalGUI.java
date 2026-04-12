package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import businessLogic.BLFacade;
import domain.Sale;

import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.JButton;
import javax.swing.JLabel;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class ProposalGUI extends JFrame {

	private JPanel contentPane;
	private JTextField priceField;
	private JButton jButtonProposal;
	private JFrame thisFrame;
	private JLabel lblMensaje;

	/**
	 * Create the frame.
	 */
	public ProposalGUI(String email, int sn) {
		setTitle("Propuesta");
		setBounds(100, 100, 354, 194);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		thisFrame = this;

		priceField = new JTextField();
		priceField.setBounds(134, 25, 136, 27);
		contentPane.add(priceField);
		priceField.setColumns(10);

		jButtonProposal = new JButton("Hacer propuesta");
		jButtonProposal.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					float price = Float.parseFloat(priceField.getText());
					BLFacade facade = MainGUI.getBusinessLogic();
					if (facade.createProposedSale(email, sn, price) == null) {
						System.out.print("saldo insuficiente");
						lblMensaje.setText("Saldo insuficiente");
						lblMensaje.setForeground(Color.RED);
					} else {
						thisFrame.setVisible(false);
					}
				} catch (NumberFormatException e) {
					lblMensaje.setText("Importe no valido");
					lblMensaje.setForeground(Color.RED);
				} catch (Exception e) {
					lblMensaje.setText("Error inesperado");
		            lblMensaje.setForeground(Color.RED);
		            e.printStackTrace();
				}

			}
		});
		jButtonProposal.setBounds(73, 65, 136, 43);
		contentPane.add(jButtonProposal);

		lblMensaje = new JLabel("");
		lblMensaje.setHorizontalAlignment(SwingConstants.CENTER);
		lblMensaje.setBounds(30, 130, 220, 20);
		contentPane.add(lblMensaje);

		JLabel lblPrecio = new JLabel("Precio:");
		lblPrecio.setBounds(53, 30, 56, 16);
		contentPane.add(lblPrecio);
	}
}
