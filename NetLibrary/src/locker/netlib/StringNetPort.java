package locker.netlib;

import java.io.BufferedReader;
import java.io.PrintWriter;

/**
 * Implementation of the {@code NetPort} class to send and receive strings.
 *
 * @author Marco Cipriani
 * @version 0.1
 * @see NetPort
 * @see Server
 * @see Client
 */
@SuppressWarnings({"unused", "unchecked"})
public abstract class StringNetPort extends NetPort<BufferedReader, PrintWriter, String> {

    /**
     * Class constructor.
     *
     * @param mode the connection mode.
     * @param port the port.
     * @see Mode
     */
    public StringNetPort(Mode mode, int port) {
        super(mode, port);
    }

    /**
     * Starts the connection to the client / server.
     *
     * @return {@code true} if the connection was done successfully.
     */
    @Override
    public abstract boolean connect();

    /**
     * Sends a message to the client / server.
     *
     * @param msg the message you want to send.
     * @throws IllegalStateException if the client is not connected or something went wrong.
     */
    @Override
    public void send(final String msg) {
        if (connected) {
            try {
                if (m == Mode.TYPE_JAVA_MAIN_THREAD) {
                    out.println(msg);

                } else {
                    new Thread(() -> out.println(msg), "Socket write").start();
                }

            } catch (Exception e) {
                System.err.println("An error occurred during sending.");
                e.printStackTrace();
            }

        } else {
            throw new IllegalStateException("Client not connected while trying to send a message to client.");
        }
    }

    /**
     * Reading runnable.
     *
     * @author SquareBoot
     */
    protected class ReadRunnable implements Runnable {

        @Override
        public void run() {
            try {
                String inputLine;
                while ((inputLine = in.readLine()) != null) {
                    notifyListeners(inputLine);
                }

            } catch (Exception e) {
                System.err.println("An error occurred during reading.");
                e.printStackTrace();
            }
        }
    }
}