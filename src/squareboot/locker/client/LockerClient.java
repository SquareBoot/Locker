package squareboot.locker.client;

import squareboot.locker.netlib.Client;

import java.awt.*;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * The locker client starter.
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
        client.connect();
        System.out.println("Done.\nSetting up the GUI...");
        GraphicsDevice[] devices = GraphicsEnvironment.getLocalGraphicsEnvironment().getScreenDevices();
        // Only one graphics device supported!
        GraphicsDevice gp = (devices[0]);
        LockScreen ls = new LockScreen(gp);
        LockScreenRunner lsr = new LockScreenRunner(ls, gp);
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
        OSTools.OSFamilies f = OSTools.getOSFamily();
        if (f != OSTools.OSFamilies.WINDOWS) {
            throw new UnsupportedOperationException("Unsupported OS!");
        }
        new LockerClient(args[0], Integer.valueOf(args[1]));
    }
}