package locker.netlib;

import java.io.BufferedReader;
import java.io.IOException;

/**
 * Implementation of the {@link NetPort} class to send and receive strings.
 *
 * @author Marco Cipriani
 * @version 0.1
 * @see NetPort
 * @see Server
 * @see Client
 */
@SuppressWarnings({"unused", "unchecked", "WeakerAccess"})
public abstract class StringNetPort extends NetPort<String> {

    /**
     * Class constructor. Initializes the port without attempting a connection.
     *
     * @param mode the connection mode.
     * @param port the port.
     */
    public StringNetPort(Mode mode, int port) {
        super(mode, port);
    }

    /**
     * Class constructor. Initializes the port without attempting a connection.
     *
     * @param port the port.
     */
    public StringNetPort(int port) {
        super(port);
    }

    /**
     * Invoke it to start reading.
     */
    protected void read(BufferedReader in) {
        try {
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                onMessage(inputLine);
            }

        } catch (IOException e) {
            System.err.println("An error occurred while reading.");
            e.printStackTrace();
        }
    }
}