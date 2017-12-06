package locker.client;

import javax.swing.*;
import java.awt.*;

/**
 * Shows a window and makes it behave as a lock screen.
 *
 * @author Martijn Courteaux
 * @author Marco Cipriani
 * @version 0.1
 * @see <a href="https://stackoverflow.com/a/6744937">Use Java to lock a screen</a>
 */
@SuppressWarnings("unused")
public class LockScreenRunnable implements Runnable {

    /**
     * The frame.
     */
    private JFrame frame;
    /**
     * Change it to {@code false} to stop the program.
     */
    private boolean running;

    /**
     * Class constructor. Runs the window.
     */
    public LockScreenRunnable(JFrame frame) {
        this.frame = frame;
        new Thread(this).start();
    }

    /**
     * Stops the program.
     */
    public void stop() {
        this.running = false;
    }

    /**
     * Shows the window and kills {@code explorer.exe}.
     */
    public void run() {
        try {
            //this.terminal.getParentFrame().setAlwaysOnTop(true);
            //this.terminal.getParentFrame().setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
            frame.setAlwaysOnTop(true);
            frame.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);

            // Kill explorer
            kill("explorer.exe");

            Robot robot = new Robot();
            int i = 0;
            while (running) {
                sleep(30L);
                focus();
                releaseKeys(robot);
                sleep(15L);
                focus();
                if (i++ % 10 == 0) {
                    kill("taskmgr.exe");
                }
                focus();
                releaseKeys(robot);
            }

            // Restart explorer
            Runtime.getRuntime().exec("explorer.exe");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // TODO(marco): add JavaDoc
    private void releaseKeys(Robot robot) {
        robot.keyRelease(17);
        robot.keyRelease(18);
        robot.keyRelease(127);
        robot.keyRelease(524);
        robot.keyRelease(9);
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

        } catch (Exception ignored) {

        }
    }

    /**
     * Kills the specified process.
     *
     * @param processName the name of the process to kill.
     */
    private void kill(String processName) {
        try {
            Runtime.getRuntime().exec("taskkill /F /IM " + processName).waitFor();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Grabs the focus.
     */
    private void focus() {
        //this.frame.grabFocus();
        frame.toFront();
        frame.requestFocus();
    }
}