package squareboot.locker.netlib;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

/**
 * Server Socket for a local LAN server with event handling.
 *
 * @author SquareBoot
 * @version 0.1
 */
@SuppressWarnings({"unused", "unchecked", "WeakerAccess"})
public abstract class Server extends StringNetPort {

    /**
     * Server socket.
     */
    private ServerSocket serverSocket;
    /**
     * List of client sockets.
     */
    private ArrayList<Socket> clients = new ArrayList<>();

    /**
     * Class constructor. Initializes the client without attempting a connection.
     *
     * @param mode the mode. See {@link NetPort.Mode}
     * @param port the port of the new server.
     */
    public Server(Mode mode, int port) {
        super(mode, port);
    }

    /**
     * Class constructor. Initializes the client without attempting a connection.
     *
     * @param port the port of the new server.
     */
    public Server(int port) {
        super(port);
    }

    /**
     * Starts the socket and the connection.
     */
    @Override
    public void connect0() throws IOException {
        serverSocket = new ServerSocket(port);

        new Thread(() -> {
            while (true) {
                try {
                    Socket socket = serverSocket.accept();
                    clients.add(socket);
                    PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
                    BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                    new Thread(() -> read(in), "Reading thread").start();

                } catch (IOException e) {
                    System.err.println("An error occurred during connection.");
                    e.printStackTrace();
                }
            }

        }, "Server connection thread").start();
    }

    /**
     * Sends a message to the client.
     *
     * @param msg the message to send.
     */
    @Override
    public void send(String msg) {
        throw new UnsupportedOperationException(":-(");
    }

    /**
     * Closes the connection.
     */
    @Override
    public void close0() throws IOException {
        serverSocket.close();
        for (Socket s : clients) {
            s.close();
        }
    }

    /**
     * Invoked when a new message arrives from the client.
     */
    @Override
    public abstract void onMessage(final String msg);
}