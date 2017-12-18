package locker.client;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;

/**
 * Shows a window and makes it behave as a lock screen.
 *
 * @author Martijn Courteaux
 * @author Marco Cipriani
 * @version 0.1
 * @see <a href="https://stackoverflow.com/a/6744937">Use Java to lock a screen</a>
 */
@SuppressWarnings({"unused", "WeakerAccess"})
public class LockScreenRunner implements Runnable {

    /**
     * The frame.
     */
    private JFrame frame;
    /**
     * The graphics device of the frame.
     */
    private GraphicsDevice gp;
    /**
     * Change it to {@code false} to stop the program.
     */
    private boolean running;

    /**
     * Class constructor. Runs the window.
     */
    public LockScreenRunner(JFrame frame, GraphicsDevice gp) {
        this.frame = frame;
        this.gp = gp;
        running = true;
        new Thread(this).start();
    }

    /**
     * Stops the lock screen and disposes it.
     */
    public void stop() {
        running = false;
        frame.dispose();
    }

    /**
     * Shows the window and kills {@code explorer.exe}.
     */
    public void run() {
        try {
            frame.setAlwaysOnTop(true);
            frame.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
            boolean fullScreenSupported = gp.isFullScreenSupported();
            frame.setUndecorated(fullScreenSupported);
            frame.setResizable(!fullScreenSupported);
            if (fullScreenSupported) {
                gp.setFullScreenWindow(frame);
                frame.validate();

            } else {
                frame.pack();
                frame.setVisible(true);
            }

            // Kill explorer
            OSTools.killWin("explorer.exe");

            Robot robot = new Robot();
            int i = 0;
            while (running) {
                sleep(30L);
                focus();
                releaseKeys(robot);
                sleep(15L);
                focus();
                //if (i++ % 10 == 0) {
                //    kill("taskmgr.exe");
                //}
                OSTools.killWin("taskmgr.exe");
                focus();
                releaseKeys(robot);
            }

            // Restart explorer
            OSTools.execWin("explorer.exe");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // TODO(marco): add JavaDoc
    private void releaseKeys(Robot robot) {
        robot.keyRelease(KeyEvent.VK_CONTROL);
        robot.keyRelease(KeyEvent.VK_ALT);
        robot.keyRelease(KeyEvent.VK_DELETE);
        robot.keyRelease(KeyEvent.VK_WINDOWS);
        robot.keyRelease(9); //TODO(marco): ?
    }

    /**
     * Stops the execution for {@code millis} milliseconds.
     * Catches all the exceptions and ignores them.
     *
     * @param millis the number of milliseconds.
     */
    private void sleep(long millis) {
        try {
            Thread.sleep(millis);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Grabs the focus.
     */
    private void focus() {
        frame.toFront();
        //frame.requestFocus();
    }
}