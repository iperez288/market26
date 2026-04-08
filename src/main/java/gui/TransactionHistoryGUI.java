package gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.util.List;
import java.util.ResourceBundle;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import businessLogic.BLFacade;
import domain.Buyer;
import domain.Transaction; // Asumiendo que tu clase se llama Transaction
import domain.User;

public class TransactionHistoryGUI extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTable table;
	private DefaultTableModel tableModel;
	private String[] columnNames = new String[] { "Fecha", "Tipo", "Cantidad" };

	public TransactionHistoryGUI() {
		setTitle("Historial de Transacciones");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setSize(new Dimension(450, 400));
		setLocationRelativeTo(null); // Centrar en pantalla

		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(10, 10, 10, 10));
		contentPane.setLayout(new BorderLayout(0, 10));
		setContentPane(contentPane);

		// Título de la ventana
		JLabel lblTitle = new JLabel("Mis Transacciones");
		lblTitle.setHorizontalAlignment(SwingConstants.CENTER);
		lblTitle.setFont(new Font("Tahoma", Font.BOLD, 15));
		contentPane.add(lblTitle, BorderLayout.NORTH);

		// Configuración de la tabla
		tableModel = new DefaultTableModel(null, columnNames);
		table = new JTable(tableModel);

		// Impedir la edición de las celdas
		table.setDefaultEditor(Object.class, null);

		JScrollPane scrollPane = new JScrollPane(table);
		contentPane.add(scrollPane, BorderLayout.CENTER);

		// Cargar los datos desde la lógica de negocio
		cargarDatos();
	}

	private void cargarDatos() {
		try {
			BLFacade facade = MainGUI.getBusinessLogic();
			// Asumiendo que tienes un método en la fachada que devuelve las transacciones
			// del usuario actual
			
			User usuarios = facade.getUsuario();
			
			
			List<Transaction> transactions = ((Buyer) usuarios).getTransactions();
			
			
			tableModel.setDataVector(null, columnNames); // Limpiar tabla

			for (Transaction t : transactions) {
				Object[] row = new Object[3];
				row[0] = t.getDate(); // Tipo Date
				row[1] = t.getType(); // Tipo String o Enum (Extraction, Income, etc.)
				row[2] = String.format("%.2f €", t.getAmount());
				tableModel.addRow(row);
				
				System.out.print(t.getDate());
			}

			// Ajustar anchos de columna básicos
			table.getColumnModel().getColumn(0).setPreferredWidth(150);
			table.getColumnModel().getColumn(1).setPreferredWidth(100);
			table.getColumnModel().getColumn(2).setPreferredWidth(80);

		} catch (Exception e) {
			System.out.println("Error al cargar transacciones: " + e.getMessage());
		}
	}
}