package locker.netlib;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;

/**
 * Server Socket for a local LAN server with event handling.
 *
 * @author Marco Cipriani
 * @version 0.1
 */
@SuppressWarnings({"unused", "unchecked"})
public abstract class Server extends StringNetPort {

    /**
     * Server socket.
     */
    private ServerSocket serverSocket;

    /**
     * Class constructor. Initializes the server without trying a connection.
     *
     * @param mode the mode. See {@link NetPort.Mode}
     * @param port the port of your server.
     */
    public Server(Mode mode, int port) {
        super(mode, port);
    }

    /**
     * Starts the socket and the connection.
     */
    @Override
    public boolean connect() {
        Runnable conn = () -> {
            try {
                connected = false;
                serverSocket = new ServerSocket(p);
                socket = serverSocket.accept();
                out = new PrintWriter(socket.getOutputStream(), true);
                in = new BufferedReader(
                        new InputStreamReader(socket.getInputStream()));

                connected = true;

            } catch (Exception e) {
                System.err.println("An error occurred during connection.");
                e.printStackTrace();
                connected = false;
            }
        };
        if (m == Mode.TYPE_JAVA_MAIN_THREAD) {
            conn.run();

        } else {
            Thread connThread = new Thread(conn, "Server connection");
            connThread.start();
            try {
                connThread.join();

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        new Thread(new ReadRunnable(), "Server read thread").start();

        return connected;
    }

    /**
     * Closes the connection.
     *
     * @throws IllegalStateException if this server is not connected.
     * @throws IOException           if an I/O error occurs when closing this socket.
     */
    public void close() throws IOException {
        super.close();
        serverSocket.close();
    }

    /**
     * Invoked when a new message arrives from the client.
     */
    public abstract void onMessage(final String msg);
}