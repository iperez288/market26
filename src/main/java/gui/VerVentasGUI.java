package gui;

import businessLogic.BLFacade;
import domain.ProposedSale;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.List;
import javax.swing.table.DefaultTableModel;

public class VerVentasGUI extends JFrame {

	private String email;
	private static final long serialVersionUID = 1L;
	private final JLabel jLabelProducts = new JLabel("Ventas publicadas:");

	private JButton jButtonSearch = new JButton(
			ResourceBundle.getBundle("Etiquetas").getString("QuerySalesGUI.Search"));
	private JButton jButtonClose = new JButton(ResourceBundle.getBundle("Etiquetas").getString("Close"));

	private JScrollPane scrollPanelProducts = new JScrollPane();
	private JTable tableProducts = new JTable();

	private DefaultTableModel tableModelProducts;
	private JFrame thisFrame;

	private String[] columnNamesProducts = new String[] {
			ResourceBundle.getBundle("Etiquetas").getString("CreateSaleGUI.Title"),
			ResourceBundle.getBundle("Etiquetas").getString("CreateSaleGUI.Price"), "Estado", "Comprador", "Objeto" };

	private JTextField jTextFieldSearch;

	public VerVentasGUI(String email) {
		this.email = email;
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		tableProducts.setEnabled(false);
		thisFrame = this;
		this.getContentPane().setLayout(null);
		this.setSize(new Dimension(700, 500));
		this.setTitle("Mis Ventas");

		jLabelProducts.setBounds(52, 108, 427, 16);
		this.getContentPane().add(jLabelProducts);

		jButtonClose.setBounds(new Rectangle(220, 379, 130, 30));
		jButtonClose.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				thisFrame.setVisible(false);
			}
		});
		this.getContentPane().add(jButtonClose);

		scrollPanelProducts.setBounds(new Rectangle(52, 137, 580, 200));
		scrollPanelProducts.setViewportView(tableProducts);

		tableModelProducts = new DefaultTableModel(null, columnNamesProducts);
		tableProducts.setModel(tableModelProducts);

		this.getContentPane().add(scrollPanelProducts);

		jTextFieldSearch = new JTextField();
		jTextFieldSearch.setBounds(52, 56, 357, 26);
		getContentPane().add(jTextFieldSearch);

		jButtonSearch.setBounds(427, 56, 117, 29);
		jButtonSearch.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				actualizarLista();
			}
		});
		getContentPane().add(jButtonSearch);
		
		
		tableProducts.addMouseListener(new MouseAdapter() {
	        @Override
	        public void mousePressed(MouseEvent mouseEvent) {
	            
	            if(mouseEvent.getClickCount() == 2)
	            {
			        JTable table =(JTable) mouseEvent.getSource();
	            	Point point = mouseEvent.getPoint();
			        int row = table.rowAtPoint(point);
	            	domain.ProposedSale ps=(domain.ProposedSale) tableModelProducts.getValueAt(row, 4);
	            	if(!ps.hasReview());
	            		new VerValoracionGUI((VerVentasGUI) thisFrame,ps.getReview());
		              
	            }
	        }
	 });
		actualizarLista();
	}

	public void actualizarLista() {
		try {
			tableModelProducts.setDataVector(null, columnNamesProducts);
			tableModelProducts.setColumnCount(5);

			BLFacade facade = MainGUI.getBusinessLogic();
			tableProducts.setEnabled(true);

			List<domain.ProposedSale> sales = facade.getVentasUsuario(jTextFieldSearch.getText(), email);

			if (sales.isEmpty()) {
				jLabelProducts.setText("No tienes ventas publicadas.");
			} else {
				jLabelProducts.setText("Estas son tus ventas:");
				for (domain.ProposedSale sale : sales) {
					Vector<Object> row = new Vector<Object>();
					row.add(sale.getTitle());
					row.add(sale.getPrice());

					if (sale.getBuyer() != null) {
						row.add("Vendido");
						row.add(sale.getBuyer().getEmail());
					} else {
						row.add("En venta");
						row.add("-");
					}

					row.add(sale);
					tableModelProducts.addRow(row);
				}
			}
		} catch (Exception e1) {
			e1.printStackTrace();
		}

		tableProducts.getColumnModel().getColumn(0).setPreferredWidth(200);
		tableProducts.getColumnModel().getColumn(1).setPreferredWidth(50);
		tableProducts.getColumnModel().getColumn(2).setPreferredWidth(70);
		tableProducts.getColumnModel().getColumn(3).setPreferredWidth(150);

		tableProducts.getColumnModel().removeColumn(tableProducts.getColumnModel().getColumn(4));
	}
}