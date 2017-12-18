package locker.netlib;

import java.io.BufferedReader;
import java.io.IOException;

/**
 * Network client or server base class.
 *
 * @param <MessageType> the message type.
 * @author Marco Cipriani
 * @see StringNetPort
 */
@SuppressWarnings({"unused", "unchecked", "WeakerAccess"})
public abstract class NetPort<MessageType> {

    /**
     * Port.
     */
    protected int port;
    /**
     * Connection state / connection result.
     */
    protected boolean connected = false;
    /**
     * Mode.
     */
    protected Mode m;

    /**
     * Class constructor. Initializes the port without attempting a connection.
     *
     * @param mode the connection mode.
     * @param port the port.
     */
    public NetPort(Mode mode, int port) {
        this.port = port;
        m = mode;
    }

    /**
     * Class constructor. Initializes the port without attempting a connection.
     *
     * @param port the port.
     */
    public NetPort(int port) {
        this.port = port;
        m = Mode.MAIN_THREAD;
    }

    /**
     * Starts the connection to the client / server.
     *
     * @return {@code true} if the connection was done successfully.
     */
    public boolean connect() {
        Runnable conn = () -> {
            try {
                if (connected) {
                    throw new IllegalStateException("Already connected!");
                }
                connect0();
                connected = true;

            } catch (Exception e) {
                System.err.println("An error occurred while connecting...");
                e.printStackTrace();
            }
        };
        if (m == Mode.MAIN_THREAD) {
            conn.run();

        } else {
            Thread thread = new Thread(conn, "Connection thread");
            thread.start();
            try {
                thread.join();

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return connected;
    }

    /**
     * Starts the connection to the client / server.
     */
    protected abstract void connect0() throws IOException;

    /**
     * Sends a message to the client / server.
     *
     * @param msg the message to send.
     */
    public abstract void send(MessageType msg);

    /**
     * Invoked when a new message arrives from the server / client.
     */
    protected abstract void onMessage(final MessageType msg);

    /**
     * Invoke it to start reading.
     */
    protected abstract void read(BufferedReader in);

    /**
     * @return the current TCP port.
     */
    public int getPort() {
        return port;
    }

    /**
     * Closes the connection.
     *
     * @return {@code true} if everything worked fine and the disconnection was successfully.
     */
    public boolean close() {
        if (!connected) {
            throw new IllegalStateException("This port has already been closed!");
        }
        try {
            close0();
            connected = false;

        } catch (IOException e) {
            System.err.println("Could not disconnect!");
            e.printStackTrace();
        }
        return !connected;
    }

    /**
     * Closes the connection.
     */
    public abstract void close0() throws IOException;

    /**
     * Returns the connection state of the current client.
     *
     * @return the connection state, connected or not.
     */
    public boolean isConnected() {
        return connected;
    }

    /**
     * Different ways to start the connection.
     *
     * @author Marco Cipriani
     * @version 0.1
     */
    public enum Mode {
        /**
         * Type Java: all the client / server code is executed on the main thread.
         */
        MAIN_THREAD,
        /**
         * Type Android: all the client / server code is executed in another thread.
         */
        ANDROID_NO_MAIN_THREAD
    }
}