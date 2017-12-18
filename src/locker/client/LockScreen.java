package locker.client;

import javax.swing.*;
import java.awt.*;

/**
 * The GUI of the lock screen.
 *
 * @author Marco Cipriani
 * @version 0.1
 */
@SuppressWarnings({"unused", "WeakerAccess"})
public class LockScreen extends JFrame {

    /**
     * Run to close the locker.
     */
    Runnable closeLocker;
    /**
     * The parent component.
     */
    private JPanel parent;
    private JTextField nameField;
    private JButton continueButton;

    public LockScreen(GraphicsDevice gp) {
        super("Locker", gp.getDefaultConfiguration());
        setContentPane(parent);
        continueButton.addActionListener(e -> closeLocker.run());
    }


    /**
     * @param r the stuff to execute to unlock the window.
     */
    public void setStopCode(Runnable r) {
        closeLocker = r;
    }
}