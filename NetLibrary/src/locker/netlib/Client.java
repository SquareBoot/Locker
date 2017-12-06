package locker.netlib;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * Client Socket for a local LAN Server Socket with event handling.
 *
 * @author Marco Cipriani
 * @version 0.1
 */
@SuppressWarnings({"unused", "unchecked"})
public abstract class Client extends StringNetPort {

    /**
     * IP.
     */
    private String ip;

    /**
     * Class constructor. Initializes the client without trying a connection.
     *
     * @param mode the mode. See {@link NetPort.Mode}
     * @param address the IP address of your Server Socket.
     * @param port    the port of your server.
     */
    public Client(Mode mode, String address, int port) {
        super(mode, port);
        ip = address;
    }

    /**
     * Starts the socket and the connection.
     */
    @Override
    public boolean connect() {
        Runnable conn = () -> {
            try {
                connected = false;
                socket = new Socket(ip, p);
                out = new PrintWriter(socket.getOutputStream(), true);
                in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                connected = true;

            } catch (Exception e) {
                System.err.println("An error occurred during connection.");
                e.printStackTrace();
            }
        };

        if (m == Mode.TYPE_JAVA_MAIN_THREAD) {
            conn.run();

        } else {
            Thread connThread = new Thread(conn, "Client connection");
            connThread.start();
            try {
                connThread.join();

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        new Thread(new ReadRunnable(), "Client read thread").start();

        return connected;
    }

    /**
     * @return the current ip.
     */
    public String getIp() {
        return ip;
    }

    /**
     * Invoked when a new message arrives from the server.
     */
    public abstract void onMessage(final String msg);
}