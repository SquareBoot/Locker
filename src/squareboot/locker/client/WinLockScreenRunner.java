package squareboot.locker.client;

import javax.swing.*;
import java.awt.*;
import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 * Shows a window and makes it behave as a lock screen. Windows-specific locker that kills {@code explorer.exe}.
 *
 * @author Martijn Courteaux
 * @author SquareBoot
 * @version 0.1
 * @see <a href="https://stackoverflow.com/a/6744937">Use Java to lock a screen</a>
 */
@SuppressWarnings({"unused", "WeakerAccess"})
public class WinLockScreenRunner extends LockScreenRunner {

    /**
     * Class constructor. Shows the window and locks the screen.
     *
     * @param frame the {@code JFrame} to use as lock screen.
     * @param gp    the display to use.
     */
    public WinLockScreenRunner(JFrame frame, GraphicsDevice gp) {
        super(frame, gp);
    }

    /**
     * Shows the window and kills {@code explorer.exe}.
     */
    @Override
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
                if (i++ % 10 == 0) {
                    OSTools.killWin("taskmgr.exe");
                }
                focus();
                releaseKeys(robot);
            }

            // Restart explorer (or try to - it sometimes doesn't)
            StringBuilder pidInfo = new StringBuilder();
            do {
                OSTools.execWin("explorer.exe");
                sleep(1000);
                String line;
                BufferedReader input = new BufferedReader(
                        new InputStreamReader(
                                Runtime.getRuntime().exec(
                                        System.getenv("windir") + "\\system32\\" + "tasklist.exe").getInputStream()));
                while ((line = input.readLine()) != null) {
                    pidInfo.append(line);
                }
                input.close();

            } while (!pidInfo.toString().contains("explorer.exe"));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}