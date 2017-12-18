package locker.server;

import javax.swing.*;
import java.awt.event.ActionListener;

/**
 * The admin GUI.
 *
 * @author Marco Cipriani
 * @version 0.1
 */
@SuppressWarnings({"unused", "WeakerAccess"})
public class AdminGUI extends JFrame {

    /**
     * The parent component.
     */
    private JPanel parent;
    private JTable table;
    private JButton save;

    /**
     * Class constructor.
     *
     * @param model the model to use in the table.
     */
    public AdminGUI(ComputersListModel model) {
        super("Locker admin");
        setContentPane(parent);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setExtendedState(getExtendedState() | JFrame.MAXIMIZED_BOTH);
        table.setModel(model);
        table.setRowSelectionAllowed(true);
        pack();
        setVisible(true);
    }

    /**
     * Adds a listener that will be invoked when the user clicks "Save".
     *
     * @param listener the listener.
     */
    public void addSaveLister(ActionListener listener) {
        save.addActionListener(listener);
    }
}