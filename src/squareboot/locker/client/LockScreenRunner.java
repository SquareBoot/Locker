package squareboot.locker.client;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;

/**
 * Shows a window and makes it behave as a lock screen.
 *
 * @author SquareBoot
 * @version 0.1
 */
@SuppressWarnings({"unused", "WeakerAccess"})
public class LockScreenRunner implements Runnable {

    /**
     * The frame.
     */
    protected JFrame frame;
    /**
     * The graphics device of the frame.
     */
    protected GraphicsDevice gp;
    /**
     * Change it to {@code false} to stop the program.
     */
    protected boolean running;

    /**
     * Class constructor. Shows the window and locks the screen.
     *
     * @param frame the {@code JFrame} to use as lock screen.
     * @param gp    the display to use.
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
     * Shows the window.
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

            Robot robot = new Robot();
            int i = 0;
            while (running) {
                sleep(30L);
                focus();
                releaseKeys(robot);
                sleep(15L);
                focus();
                releaseKeys(robot);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Releases some keys.
     */
    protected void releaseKeys(Robot robot) {
        robot.keyRelease(KeyEvent.VK_CONTROL);
        robot.keyRelease(KeyEvent.VK_ALT);
        robot.keyRelease(KeyEvent.VK_DELETE);
        robot.keyRelease(KeyEvent.VK_WINDOWS);
        robot.keyRelease(9);
    }

    /**
     * Stops the execution for {@code millis} milliseconds.
     * Catches all the exceptions and ignores them.
     *
     * @param millis the number of milliseconds.
     */
    protected void sleep(long millis) {
        try {
            Thread.sleep(millis);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Grabs the focus.
     */
    protected void focus() {
        frame.toFront();
    }
}