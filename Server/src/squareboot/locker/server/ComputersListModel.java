package squareboot.locker.server;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;

/**
 * @author SquareBoot
 * @version 0.1
 */
@SuppressWarnings({"unused", "WeakerAccess"})
public class ComputersListModel extends AbstractTableModel {

    /**
     * The header of the table.
     */
    public static final String[] COLUMN_NAMES = {"Name", "PC name", "Login @"};

    /**
     * All the data.
     */
    private ArrayList<Object[]> data = new ArrayList<>();

    /**
     * @return all the stored data.
     */
    public ArrayList<Object[]> getData() {
        return data;
    }

    /**
     * Adds a row.
     *
     * @param newData the new stuff to add: {@code {"Name", "PC name", "Login @"}}.
     */
    public void addData(Object[] newData) {
        data.add(newData);
        fireTableDataChanged();
    }

    /**
     * Returns the number of columns in the model. A
     * <code>JTable</code> uses this method to determine how many columns it
     * should create and display by default.
     *
     * @return the number of columns in the model
     * @see #getRowCount
     */
    public int getColumnCount() {
        return COLUMN_NAMES.length;
    }

    /**
     * Returns the number of rows in the model. A
     * <code>JTable</code> uses this method to determine how many rows it
     * should display.  This method should be quick, as it
     * is called frequently during rendering.
     *
     * @return the number of rows in the model
     * @see #getColumnCount
     */
    public int getRowCount() {
        return data.size();
    }

    /**
     * Returns the name of the required column.
     *
     * @param column the column being queried
     * @return a string containing the name of {@code column}.
     */
    public String getColumnName(int column) {
        return COLUMN_NAMES[column];
    }

    /**
     * Returns the value for the cell at {@code columnIndex} and {@code rowIndex}.
     *
     * @param rowIndex    the row whose value is to be queried.
     * @param columnIndex the column whose value is to be queried.
     * @return the value Object at the specified cell.
     */
    public Object getValueAt(int rowIndex, int columnIndex) {
        return data.get(rowIndex)[columnIndex];
    }

    /**
     * Returns {@code Object.class} regardless of {@code columnIndex}.
     *
     * @param columnIndex the column being queried
     * @return the Object.class
     */
    public Class getColumnClass(int columnIndex) {
        return getValueAt(0, columnIndex).getClass();
    }

    /**
     * Simple implementation to set a value.
     *
     * @param aValue      value to assign to cell
     * @param rowIndex    row of cell
     * @param columnIndex column of cell
     */
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
        data.get(rowIndex)[columnIndex] = aValue;
        fireTableCellUpdated(rowIndex, columnIndex);
    }
}