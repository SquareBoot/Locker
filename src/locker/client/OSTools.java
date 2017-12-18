package locker.client;

import java.awt.*;
import java.io.IOException;

/**
 * OS-specific tools, like bash commands invocation, Windows programs killing, etc...
 *
 * @author Marco Cipriani
 * @version 0.1
 */
@SuppressWarnings({"unused", "WeakerAccess"})
public final class OSTools {

    /**
     * @return the name of the current OS.
     */
    public static String getOSName() {
        return System.getProperty("os.name");
    }

    /**
     * @return the OS family of the current running operating system.
     */
    public static OSFamilies getOSFamily() {
        String os = getOSName().toLowerCase();
        if (os.equals("linux")) {
            return OSFamilies.LINUX;

        } else if (os.contains("win")) {
            return OSFamilies.WINDOWS;

        } else if ((os.contains("nix")) || (os.contains("nix")) || (os.contains("nix"))) {
            return OSFamilies.UNIX;

        } else if (os.contains("mac")) {
            return OSFamilies.MAC;

        } else if (os.contains("sunos")) {
            return OSFamilies.SOLARIS;

        } else {
            return OSFamilies.OTHER;
        }
    }

    /**
     * Makes a beep. Similar to calling {@code Toolkit.getDefaultToolkit().beep()}, but with Linux-specific support.
     */
    public static void beep() {
        if (getOSFamily() == OSFamilies.LINUX) {
            beep0(900, 0.9);

        } else {
            Toolkit.getDefaultToolkit().beep();
        }
    }

    /**
     * Makes a beep. Similar to calling {@code Toolkit.getDefaultToolkit().beep()}, but with Linux-specific support, not available on other platforms.
     *
     * @param freq     the beep frequency.
     * @param duration the beep duration.
     */
    @SuppressWarnings("SameParameterValue")
    private static void beep0(int freq, double duration) {
        execBash("( speaker-test -t sine -f " + String.valueOf(freq) + " )& pid=$! ; sleep " +
                String.valueOf(duration) + "s ; kill -9 $pid");
    }

    /**
     * Executes a bash command (Linux) without throwing any exception if one occurs.
     *
     * @param cmd the bash command you want to execute.
     */
    public static void execBash(String cmd) {
        try {
            Runtime.getRuntime().exec(new String[]{"/bin/sh", "-c", cmd});

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Executes a cmd command (Windows) without throwing any exception if one occurs.
     *
     * @param cmd the command to execute.
     */
    public static void execWin(String cmd) {
        try {
            Runtime.getRuntime().exec("taskkill /F /IM " + cmd).waitFor();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Kills the specified process (Windows native).
     *
     * @param pn the name of the process to kill.
     */
    public static void killWin(String pn) {
        execWin("taskkill /F /IM " + pn);
    }

    /**
     * This enum contains all the known operating systems.
     *
     * @author Marco Cipriani
     * @version 0.1
     */
    public enum OSFamilies {
        WINDOWS, MAC, LINUX, UNIX, SOLARIS, OTHER
    }
}