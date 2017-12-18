package locker.server;

import javax.swing.*;

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
    private JTable table1;

    public AdminGUI() {
        super("Locker admin");
        setContentPane(parent);
    }
}