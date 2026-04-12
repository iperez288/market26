package gui;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import businessLogic.BLFacade;
import domain.ProposedSale;

import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.JSpinner;
import javax.swing.JLabel;
import javax.swing.SpinnerNumberModel;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class MakeReviewGUI extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JSpinner spinnerRate;
	private JTextPane textReview;
	private JButton btnPublicar;
	private JButton btnCancelar;
	private JTextPane infoPane;

	
	/**
	 * Create the frame.
	 */
	public MakeReviewGUI(QueryPurchasesGUI parent, ProposedSale sale, String email) {
		this.setVisible(true);
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		JFrame thisFrame = this;
		setBounds(100, 100, 263, 248);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		btnPublicar = new JButton("Publicar");
		btnPublicar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				//Hacer que la valoración ya no se pueda cambiar
				spinnerRate.setEnabled(false);
				textReview.setEnabled(false);
				
				BLFacade facade = MainGUI.getBusinessLogic();
				int rate = (int) spinnerRate.getValue();
				String text = textReview.getText();
				
				facade.hacerValoracion(email,sale.getID(),rate,text);
				infoPane.setText("Valoración realizada correctamente.\n Puede cerrar esta ventana.");
				btnPublicar.setEnabled(false);
				parent.actualizarLista();

			}
		});
		btnPublicar.setBounds(10, 118, 85, 21);
		contentPane.add(btnPublicar);
		
		btnCancelar = new JButton("Cerrar");
		btnCancelar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				thisFrame.dispose();
			}
		});
		btnCancelar.setBounds(148, 118, 84, 20);
		contentPane.add(btnCancelar);
		
		textReview = new JTextPane();
		textReview.setBounds(10, 32, 222, 66);
		contentPane.add(textReview);
		
		spinnerRate = new JSpinner();
		spinnerRate.setModel(new SpinnerNumberModel(1, 1, 10, 1));
		spinnerRate.setBounds(177, 2, 29, 20);
		contentPane.add(spinnerRate);
		
		JLabel lblRate = new JLabel("Puntuación");
		lblRate.setBounds(26, 10, 69, 12);
		contentPane.add(lblRate);
		
		infoPane = new JTextPane();
		infoPane.setEditable(false);
		infoPane.setBounds(10, 149, 221, 38);
		contentPane.add(infoPane);

	}
}
