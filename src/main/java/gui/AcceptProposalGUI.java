package gui;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import businessLogic.BLFacade;
import domain.ProposedSale;

import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class AcceptProposalGUI extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;

	
	public AcceptProposalGUI(String email, ProposedSale ps) {
		
		JFrame thisFrame = this;
		String format = "Aceptar propuesta por %.2f";
		
		setBounds(100, 100, 213, 145);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNewLabel = new JLabel(String.format(format, ps.getPrice()));
		lblNewLabel.setBounds(29, 20, 122, 12);
		contentPane.add(lblNewLabel);
		
		JButton jButtonAccept = new JButton("Aceptar");
		jButtonAccept.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				BLFacade facade = MainGUI.getBusinessLogic();
				facade.doPurchase(email, ps);
			}
		});
		jButtonAccept.setBounds(10, 52, 84, 20);
		contentPane.add(jButtonAccept);
		
		JButton jButtonCancel = new JButton("Cancelar");
		jButtonCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				thisFrame.setVisible(false);
			}
		});
		jButtonCancel.setBounds(104, 52, 84, 20);
		contentPane.add(jButtonCancel);
		
	}
}
