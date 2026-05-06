package gui;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import businessLogic.BLFacade;
import domain.ProposedSale;
import domain.Valoracion;

import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.JSpinner;
import javax.swing.JLabel;
import javax.swing.SpinnerNumberModel;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class VerValoracionGUI extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextPane textReview;
	private JButton btnCancelar;

	
	/**
	 * Create the frame.
	 */
	public VerValoracionGUI(VerVentasGUI parent,Valoracion v) {
		this.setVisible(true);
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		JFrame thisFrame = this;
		setBounds(100, 100, 263, 248);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		btnCancelar = new JButton("Cerrar");
		btnCancelar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				thisFrame.dispose();
			}
		});
		btnCancelar.setBounds(26, 181, 84, 20);
		contentPane.add(btnCancelar);
		
		textReview = new JTextPane();
		textReview.setEditable(false);
		textReview.setBounds(10, 105, 222, 66);
		contentPane.add(textReview);
		textReview.setText(v.getComentario());
		
		
		
		JLabel lblRate = new JLabel("Puntuación: "+v.getPuntuacion());
		lblRate.setBounds(80, 83, 90, 12);
		contentPane.add(lblRate);
		
		JLabel lblProducto = new JLabel(v.getSale().getTitle());
		lblProducto.setBounds(26, 10, 156, 12);
		contentPane.add(lblProducto);
		
		JLabel lblRealizador = new JLabel(v.getValorador().getEmail());
		lblRealizador.setBounds(26, 38, 144, 12);
		contentPane.add(lblRealizador);

	}
}
