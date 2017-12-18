package locker.netlib;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * Client Socket for a local LAN Server Socket with event handling.
 *
 * @author Marco Cipriani
 * @version 0.1
 */
@SuppressWarnings({"unused", "unchecked", "WeakerAccess"})
public abstract class Client extends StringNetPort {

    /**
     * The server IP.
     */
    private String ip;
    /**
     * Client socket.
     */
    private Socket socket;
    /**
     * Output.
     */
    private PrintWriter out;

    /**
     * Class constructor. Initializes the client without attempting a connection.
     *
     * @param mode    the mode. See {@link NetPort.Mode}
     * @param address the IP address of your Server Socket.
     * @param port    the port of your server.
     */
    public Client(Mode mode, String address, int port) {
        super(mode, port);
        ip = address;
    }

    /**
     * Class constructor. Initializes the client without attempting a connection.
     *
     * @param address the IP address of your Server Socket.
     * @param port    the port of your server.
     */
    public Client(String address, int port) {
        super(port);
        ip = address;
    }

    /**
     * Starts the socket and the connection.
     */
    @Override
    public void connect0() throws IOException {
        Socket socket = new Socket(ip, port);
        out = new PrintWriter(socket.getOutputStream(), true);
        BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        new Thread(() -> read(in), "Reading thread").start();
    }

    /**
     * @return the server ip.
     */
    public String getIp() {
        return ip;
    }

    /**
     * Invoked when a new message arrives from the server.
     */
    @Override
    public abstract void onMessage(final String msg);

    /**
     * Sends a message to the server.
     *
     * @param msg the message you want to send.
     * @throws IllegalStateException if the client is not connected.
     */
    @Override
    public void send(final String msg) {
        if (connected) {
            try {
                if (m == Mode.MAIN_THREAD) {
                    out.println(msg);

                } else {
                    new Thread(() -> out.println(msg), "Socket write").start();
                }

            } catch (Exception e) {
                System.err.println("An error occurred during sending.");
                e.printStackTrace();
            }

        } else {
            throw new IllegalStateException("Client not connected while trying to send a message to server.");
        }
    }

    /**
     * Closes the connection.
     */
    @Override
    public void close0() throws IOException {
        socket.close();
    }
}