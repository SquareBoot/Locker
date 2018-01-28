package squareboot.locker.client;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

/**
 * The GUI of the lock screen.<br>
 * <b>Note: this window doesn't lock the screen, show a message to the user or whatever you expect.
 * Run it in a {@link LockScreenRunner} object to do that.</b>
 *
 * @author SquareBoot
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
    /**
     * User name text field.
     */
    private JTextField nameField;
    /**
     * The "continue" button.
     */
    private JButton continueButton;

    /**
     * Class constructor.
     *
     * @param gp the {@link GraphicsDevice} to use for this window.
     */
    public LockScreen(GraphicsDevice gp) {
        super("Locker", gp.getDefaultConfiguration());
        setContentPane(parent);
        ActionListener al = new ActionListener();
        continueButton.addActionListener(al);
        nameField.addActionListener(al);
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
     * @author SquareBoot
     * @version 0.1
     */
    public interface ContinueRunnable {

        /**
         * Implement this method to close the window and save the name the user wrote.
         */
        void run(String name);
    }

    /**
     * @author SquareBoot
     * @version 0.1
     */
    private class ActionListener implements java.awt.event.ActionListener {

        /**
         * Invoked when an action occurs.
         */
        @Override
        public void actionPerformed(ActionEvent e) {
            String name = nameField.getText();
            if (!name.equals("")) {
                closeLocker.run(name);
            }
        }
    }
}