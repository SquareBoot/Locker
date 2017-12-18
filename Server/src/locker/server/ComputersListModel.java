package locker.server;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;

/**
 * @author Marco Cipriani
 * @version 0.1
 */
@SuppressWarnings({"unused", "WeakerAccess"})
public class ComputersListModel extends AbstractTableModel {

    private String[] columnNames = {"Name", "PC name", "Login @"};

    private ArrayList<Object[]> data = new ArrayList<>();

    public ArrayList<Object[]> getData() {
        return data;
    }

    public void addData(Object[] newData) {
        data.add(newData);
        fireTableDataChanged();
    }

    public int getColumnCount() {
        return columnNames.length;
    }

    public int getRowCount() {
        return data.size();
    }

    public String getColumnName(int col) {
        return columnNames[col];
    }

    public Object getValueAt(int row, int col) {
        return data.get(row)[col];
    }

    public Class getColumnClass(int c) {
        return getValueAt(0, c).getClass();
    }

    public boolean isCellEditable(int row, int col) {
        return false;
    }

    public void setValueAt(Object value, int row, int col) {
        data.get(row)[col] = value;
        fireTableCellUpdated(row, col);
    }
}