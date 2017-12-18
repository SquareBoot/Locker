package locker.client;

import java.awt.*;

/**
 * @author Marco Cipriani
 * @version 0.1
 */
@SuppressWarnings({"unused", "WeakerAccess"})
public class Main {

    /**
     * Main.
     */
    public static void main(String[] args) {
        OSTools.OSFamilies f = OSTools.getOSFamily();
        if (f != OSTools.OSFamilies.WINDOWS) {
            // throw new UnsupportedOperationException("Unsupported OS!");
        }
        GraphicsDevice[] devices = GraphicsEnvironment.getLocalGraphicsEnvironment().getScreenDevices();
        // Only one device supported!
        for (int i = 0; i < 1; i++) {
            GraphicsDevice gp = (devices[i]);
            LockScreen ls = new LockScreen(gp);
            LockScreenRunner lsr = new LockScreenRunner(ls, gp);
            ls.setStopCode(lsr::stop);
        }
    }
}