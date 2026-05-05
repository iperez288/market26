
package gui;

import businessLogic.BLFacade;
import configuration.UtilDate;
import domain.Sale;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.List;

import javax.swing.table.DefaultTableModel;


public class ConsultarConversacionesGUI extends JFrame {
	
	private String email;
	
	private static final long serialVersionUID = 1L;
	private final JLabel jLabelProducts = new JLabel("Conversaciones"); 
	private JButton jButtonClose = new JButton(ResourceBundle.getBundle("Etiquetas").getString("Close"));

	private JScrollPane scrollPanelProducts = new JScrollPane();
	private JTable tableProducts= new JTable();

	private DefaultTableModel tableModelProducts;

	private JFrame thisFrame; 

	private String[] columnNamesProducts = new String[] {
			"Producto", 
			"Precio",
			"Tema",
			"Estado",
			//"Fecha de Compra",

	};
	

	public ConsultarConversacionesGUI(String email) {
		this.email=email;
		
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		tableProducts.setEnabled(false);
		thisFrame=this;
		this.getContentPane().setLayout(null);
		this.setSize(new Dimension(700, 500));
		this.setTitle("Consultar conversaciones");
		jLabelProducts.setBounds(52, 108, 427, 16);
		this.getContentPane().add(jLabelProducts);

		jButtonClose.setBounds(new Rectangle(220, 379, 130, 30));

		jButtonClose.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				thisFrame.setVisible(false);
				tableProducts.setEnabled(false);

			}
		});		
		
		this.getContentPane().add(jButtonClose, null);
		scrollPanelProducts.setEnabled(false);

		scrollPanelProducts.setBounds(new Rectangle(52, 137, 459, 150));

		scrollPanelProducts.setViewportView(tableProducts);
		tableModelProducts = new DefaultTableModel(null, columnNamesProducts);

		tableProducts.setModel(tableModelProducts);

		tableModelProducts.setDataVector(null, columnNamesProducts);
		tableModelProducts.setColumnCount(5); // another column added to allocate ride objects

		tableProducts.getColumnModel().getColumn(0).setPreferredWidth(200);
		tableProducts.getColumnModel().getColumn(1).setPreferredWidth(10);
		tableProducts.getColumnModel().getColumn(2).setPreferredWidth(70);
		tableProducts.getColumnModel().getColumn(3).setPreferredWidth(10);


		tableProducts.getColumnModel().removeColumn(tableProducts.getColumnModel().getColumn(4)); // not shown in JTable

		this.getContentPane().add(scrollPanelProducts, null);
		
	    
		tableProducts.addMouseListener(new MouseAdapter() {
		        @Override
		        public void mousePressed(MouseEvent mouseEvent) {
		            
		            if(mouseEvent.getClickCount() == 2)
		            {
				        JTable table =(JTable) mouseEvent.getSource();
		            	Point point = mouseEvent.getPoint();
				        int row = table.rowAtPoint(point);
		            	domain.Conversacion c=(domain.Conversacion) tableModelProducts.getValueAt(row, 4);
		            	new MostrarConversacion(c, email);
		            	
		            }
		        }
		 });
	
		actualizarLista();
	
	
	}
	
	public void actualizarLista() {
		try {
			tableModelProducts.setDataVector(null, columnNamesProducts);
			tableModelProducts.setColumnCount(5); // another column added to allocate product object

			BLFacade facade = MainGUI.getBusinessLogic();
			
			tableProducts.setEnabled(true);
			List<domain.Conversacion> conversaciones=facade.getConversaciones(email);

			if (conversaciones.isEmpty() ) jLabelProducts.setText("No hay conversaciones activas.");
			else jLabelProducts.setText("Estas son las conversaciones activas:");
			for (domain.Conversacion conversacion:conversaciones){
				Vector<Object> row = new Vector<Object>();
				row.add(conversacion.getProducto().getTitle());
				row.add(conversacion.getProducto().getPrice());
				row.add(conversacion.getTema());
				row.add(conversacion.getEstado());
				row.add(conversacion);
				
				tableModelProducts.addRow(row);		
			}
		} catch (Exception e1) {

			e1.printStackTrace();
		}
		tableProducts.getColumnModel().getColumn(0).setPreferredWidth(200);
		tableProducts.getColumnModel().getColumn(1).setPreferredWidth(10);
		tableProducts.getColumnModel().getColumn(2).setPreferredWidth(70);
		tableProducts.getColumnModel().getColumn(3).setPreferredWidth(10);
		
		tableProducts.getColumnModel().removeColumn(tableProducts.getColumnModel().getColumn(4)); // not shown in JTable
	}
	
}	
	



