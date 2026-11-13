package gui;

import javax.swing.table.AbstractTableModel;

import domain.Sale;
import domain.Seller;

/**
 * ADAPTER: adapta la interfaz de Seller (Adaptee) a la que espera JTable (TableModel).
 */
public class SellerAdapter extends AbstractTableModel {

    private static final long serialVersionUID = 1L;

    private final Seller seller;
    private final String[] columnNames = { "title", "description", "price" };

    public SellerAdapter(Seller seller) {
        this.seller = seller;
    }

    @Override
    public int getRowCount() {
        return seller.getSales().size();
    }

    @Override
    public int getColumnCount() {
        return columnNames.length;
    }

    @Override
    public String getColumnName(int column) {
        return columnNames[column];
    }

    @Override
    public Class<?> getColumnClass(int column) {
        return column == 2 ? Float.class : String.class;
    }

    @Override
    public Object getValueAt(int row, int column) {
        Sale s = seller.getSales().get(row);
        switch (column) {
            case 0: return s.getTitle();
            case 1: return s.getDescription();
            case 2: return s.getPrice();
            default: return null;
        }
    }
}