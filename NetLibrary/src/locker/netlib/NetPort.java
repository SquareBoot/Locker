package locker.netlib;

import locker.netlib.MessageListener;
import locker.netlib.WithListeners;

import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;

/**
 * Network client or server base class.
 *
 * @param <I>           the input stream class you want to use.
 * @param <O>           the output stream class you want to use.
 * @param <MessageType> the message type.
 * @author Marco Cipriani
 * @see MessageListener
 */
@SuppressWarnings({"unused", "unchecked"})
public abstract class NetPort<I, O, MessageType>
        implements WithListeners<MessageListener<MessageType>> {

    /**
     * In.
     */
    public I in;
    /**
     * Out.
     */
    public O out;
    /**
     * Client socket.
     */
    protected Socket socket;
    /**
     * Port.
     */
    protected int p;
    /**
     * Connection state / connection result.
     */
    protected boolean connected = false;
    /**
     * Mode.
     */
    protected Mode m;
    /**
     * List of registered listeners.
     */
    protected ArrayList<MessageListener<MessageType>> listeners = new ArrayList<>();

    /**
     * Class constructor.
     *
     * @param mode the connection mode.
     * @param port the port.
     * @see Mode
     */
    public NetPort(Mode mode, int port) {
        p = port;
        m = mode;
    }

    /**
     * Starts the connection to the client / server.
     *
     * @return {@code true} if the connection was done successfully.
     */
    public abstract boolean connect();

    /**
     * Invoke this method when you want to notify a lister.
     *
     * @param msg the received message.
     */
    protected void notifyListeners(final MessageType msg) {
        for (MessageListener l : listeners) {
            l.onMessage(msg);
        }
    }

    /**
     * Sends a message to the client / server.
     *
     * @param msg the message to send.
     */
    public abstract void send(MessageType msg);

    /**
     * Adds a new listener to the list.
     *
     * @param l the new listener.
     */
    @Override
    public void addListener(MessageListener l) {
        listeners.add(l);
    }

    /**
     * @return the current TCP port.
     */
    public int getPort() {
        return p;
    }

    /**
     * Closes the connection.
     *
     * @throws IllegalStateException if this server is not connected.
     * @throws IOException           if an I/O error occurs when closing this socket.
     */
    public void close() throws IOException {
        if (!connected) {
            throw new IllegalStateException("This server is already closed!");
        }
        socket.close();
    }

    /**
     * Returns the connection state of the current client.
     *
     * @return the connection state, connected or not.
     */
    public boolean isConnected() {
        return connected;
    }

    /**
     * Different ways to do the connection.
     *
     * @author SquareBoot
     */
    public enum Mode {
        /**
         * Type Java: all the client / server code is executed on the main thread.
         */
        TYPE_JAVA_MAIN_THREAD,
        /**
         * Type Android: all the client / server code is executed in another thread.
         */
        TYPE_ANDROID_NO_MAIN_THREAD
    }
}