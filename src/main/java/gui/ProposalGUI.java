package gui;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import businessLogic.BLFacade;
import domain.Sale;

import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JLabel;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class ProposalGUI extends JFrame {

	private JPanel contentPane;
	private JTextField priceField;
	private JButton jButtonProposal;
	private JFrame thisFrame;


	/**
	 * Create the frame.
	 */
	public ProposalGUI( Sale sale) {
		setTitle("Propuesta");
		setBounds(100, 100, 354, 194);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		thisFrame=this;
		
		priceField = new JTextField();
		priceField.setBounds(134, 25, 136, 27);
		contentPane.add(priceField);
		priceField.setColumns(10);
		
		jButtonProposal = new JButton("Hacer propuesta");
		jButtonProposal.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				float price = Float.parseFloat(priceField.getText());
				BLFacade facade = MainGUI.getBusinessLogic();
				facade.createProposedSale(sale, price);
				thisFrame.setVisible(false);
			}
		});
		jButtonProposal.setBounds(73, 65, 136, 43);
		contentPane.add(jButtonProposal);
		
		JLabel lblPrecio = new JLabel("Precio:");
		lblPrecio.setBounds(53, 30, 56, 16);
		contentPane.add(lblPrecio);
	}
}
