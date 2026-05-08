package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.border.EmptyBorder;

import businessLogic.BLFacade;
import domain.Conversacion;
import domain.Mensaje;

import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextPane;
import javax.swing.SwingUtilities;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.chrono.ChronoLocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.awt.event.ActionEvent;

import javax.swing.Box;
import javax.swing.BoxLayout;

public class MostrarConversacion extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextPane inputPane;
	private JScrollPane scroll;
	private JPanel panelMensajes;

	/**
	 * Launch the application.
	 *
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MostrarConversacion frame = new MostrarConversacion(null,null);
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
	public MostrarConversacion(ConsultarConversacionesGUI parent, Conversacion c, String email) {
		
		JFrame thisFrame = this;
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 473, 429);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		inputPane = new JTextPane();
		inputPane.setBounds(35, 235, 392, 107);
		contentPane.add(inputPane);
		
		JButton btnCancelar = new JButton("Salir");
		btnCancelar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				thisFrame.dispose();
			}
		});
		btnCancelar.setBounds(45, 352, 84, 20);
		contentPane.add(btnCancelar);
		
		JButton btnEnviar = new JButton("Enviar mensaje");
		btnEnviar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				String mensaje = inputPane.getText();
				
				if( mensaje.equals("")) {
					JOptionPane.showMessageDialog(null, "El mensaje está vacío.");
				}else {
					
					BLFacade facade = MainGUI.getBusinessLogic();
					
					Mensaje m = facade.enviarMensaje(mensaje, c, email);
					
					((MostrarConversacion) thisFrame).agregarMensaje(m);
					parent.actualizarLista();
					inputPane.setText("");
				}
			}
		});
		btnEnviar.setBounds(267, 352, 149, 20);
		contentPane.add(btnEnviar);
		
		panelMensajes = new JPanel();
		panelMensajes.setBounds(35, 10, 392, 221);
		contentPane.add(panelMensajes);
		panelMensajes.setLayout(new BoxLayout(panelMensajes, BoxLayout.Y_AXIS));
		
		scroll = new JScrollPane(panelMensajes);
		scroll.setBounds(35, 10, 392, 221);
		contentPane.add(scroll);
		
		
		cargarMensajes(c,email);

	}
	
	
	 private JPanel crearPanelMensaje(Mensaje m) {
	        JPanel panel = new JPanel();
	        panel.setLayout(new BorderLayout());
	        panel.setBorder(new EmptyBorder(8, 8, 8, 8));
	        panel.setBackground(new Color(240, 240, 240));

	        String email = m.getEmisor().getEmail();
	        
	        Date fEnvio = m.getFechaEnvio();
	        
	        
	        SimpleDateFormat formato = new SimpleDateFormat("(dd-MM-yyyy) hh:mm");
	        
	        String fecha = formato.format(fEnvio);
	        
	        // INFO (EMAIL + FECHA)
	        JLabel lblInfo = new JLabel(email + "  |  " + fecha);

	        // TEXTO DEL MENSAJE
	        JTextArea txtMensaje = new JTextArea(m.getMensaje());
	        txtMensaje.setLineWrap(true);
	        txtMensaje.setWrapStyleWord(true);
	        txtMensaje.setEditable(false);
	        txtMensaje.setBackground(new Color(240, 240, 240));

	        panel.add(lblInfo, BorderLayout.NORTH);
	        panel.add(txtMensaje, BorderLayout.CENTER);

	        return panel;
	    }
	 
	 private void agregarMensaje(Mensaje m) {
	        JPanel panel = crearPanelMensaje(m);
	        this.panelMensajes.add(panel);
	        this.panelMensajes.add(Box.createVerticalStrut(5)); // espacio entre mensajes

	        this.panelMensajes.revalidate();
	        this.panelMensajes.repaint();

	        SwingUtilities.invokeLater(() -> {
	            JScrollBar vertical = scroll.getVerticalScrollBar();
	            vertical.setValue(vertical.getMaximum());
	        });
	        
	    }
	 
	 private void cargarMensajes(Conversacion c, String email) {
		 
		 BLFacade facade = MainGUI.getBusinessLogic();
		 
		 List<Mensaje> mensajes = facade.getMensajes(c, email);
		 
		 for (Mensaje m : mensajes) {
	            agregarMensaje(m);
	        }		 
	 }
	
}
