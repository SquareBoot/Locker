package locker.client;

import javax.swing.*;
import java.awt.*;

/**
 * The GUI of the lock screen.<br>
 * <b>Note: this window doesn't lock the screen, show a message to the user or whatever you expect.
 * Run it in a {@link LockScreenRunner} object to do that.</b>
 *
 * @author Marco Cipriani
 * @version 0.1
 */
@SuppressWarnings({"unused", "WeakerAccess"})
public class LockScreen extends JFrame {

    /**
     * Run to close the locker.
     */
    ContinueRunnable closeLocker;
    /**
     * The parent component.
     */
    private JPanel parent;
    private JTextField nameField;
    private JButton continueButton;

    /**
     * Class constructor.
     *
     * @param gp the {@link GraphicsDevice} to use for this window.
     */
    public LockScreen(GraphicsDevice gp) {
        super("Locker", gp.getDefaultConfiguration());
        setContentPane(parent);
        continueButton.addActionListener(e -> {
            String name = nameField.getText();
            if (!name.equals("")) {
                closeLocker.run(name);
            }
        });
    }


    /**
     * @param r the stuff to execute to unlock the window.
     */
    public void setContinueAction(ContinueRunnable r) {
        closeLocker = r;
    }

    /**
     * Implement its method to close the window and save the name the user wrote.
     *
     * @author Marco Cipriani
     * @version 0.1
     */
    public interface ContinueRunnable {

        /**
         * Implement this method to close the window and save the name the user wrote.
         */
        void run(String name);
    }
}