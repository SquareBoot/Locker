package squareboot.locker.client;

import squareboot.locker.netlib.Client;

import javax.swing.*;
import java.awt.*;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * The Locker Client main class.
 *
 * @author SquareBoot
 * @version 0.1
 */
@SuppressWarnings({"unused", "WeakerAccess"})
public class LockerClient {

    /**
     * Class constructor.
     *
     * @param address the IP address of the server.
     * @param port    the port of the server.
     */
    public LockerClient(String address, int port) {
        System.out.println("Welcome\nSetting up Locker...");
        Client client = new Client(address, port) {
            @Override
            public void onMessage(String msg) {

            }
        };

        System.out.println("Connecting to the server...");
        if (!client.connect()) {
            System.err.println("An error occurred while connecting!");
            System.out.println("Good bye.");
            System.exit(0);
        }

        System.out.println("Done.\nSetting up the GUI...");
        GraphicsDevice[] devices = GraphicsEnvironment.getLocalGraphicsEnvironment().getScreenDevices();
        // Only one graphics device supported!
        GraphicsDevice gp = (devices[0]);
        LockScreen ls = new LockScreen(gp);
        LockScreenRunner lsr = (OSTools.getOSFamily() == OSTools.OSFamilies.WINDOWS) ?
                new WinLockScreenRunner(ls, gp) : new LockScreenRunner(ls, gp);

        ls.setContinueAction(name -> {
            try {
                System.out.println("Printing info...");
                client.send(name + "-" +
                        System.getProperty("user.name").replace("-", "_") + "@" +
                        InetAddress.getLocalHost().getHostName().replace("-", "_") + "-" +
                        new SimpleDateFormat("MM/dd/yyyy HH:mm").format(new Date()));
                lsr.stop();
                System.out.println("Done.\nGood bye.");
                System.exit(0);

            } catch (UnknownHostException e) {
                e.printStackTrace();
            }
        });
        System.out.println("Done.\nWaiting for user response...");
    }

    /**
     * Main.
     */
    public static void main(String[] args) {
        try {
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (OSTools.getOSFamily() == OSTools.OSFamilies.WINDOWS) {
            System.out.println("Windows detected! Locker will kill explorer.exe for a better result.");
        }

        if (args.length == 2) {
            try {
                new LockerClient(args[0], Integer.valueOf(args[1]));

            } catch (NumberFormatException e) {
                System.err.println("Invalid port!");
            }

        } else if (args.length == 1) {
            String[] sa = args[0].split(":");
            if (sa.length == 2) {
                try {
                    new LockerClient(sa[0], Integer.valueOf(sa[1]));

                } catch (NumberFormatException e) {
                    System.err.println("Invalid port!");
                }

            } else {
                System.err.println("Could not parse arguments!");
            }

        } else {
            System.err.println("Invalid arguments!");
        }
    }
}