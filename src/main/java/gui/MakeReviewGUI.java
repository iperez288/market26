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

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MakeReviewGUI frame = new MakeReviewGUI(null,null);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public MakeReviewGUI(ProposedSale sale, String email) {
		this.setVisible(true);
		setBounds(100, 100, 447, 300);
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
				
				facade.hacerValoracion(sale.getID(),rate,text);
				
			}
		});
		btnPublicar.setBounds(106, 144, 85, 21);
		contentPane.add(btnPublicar);
		
		btnCancelar = new JButton("Cancelar");
		btnCancelar.setBounds(239, 144, 84, 20);
		contentPane.add(btnCancelar);
		
		textReview = new JTextPane();
		textReview.setBounds(127, 56, 196, 66);
		contentPane.add(textReview);
		
		spinnerRate = new JSpinner();
		spinnerRate.setModel(new SpinnerNumberModel(1, 1, 10, 1));
		spinnerRate.setBounds(251, 26, 29, 20);
		contentPane.add(spinnerRate);
		
		JLabel lblRate = new JLabel("Puntuaci\u00F3n");
		lblRate.setBounds(148, 29, 69, 12);
		contentPane.add(lblRate);

	}
}
