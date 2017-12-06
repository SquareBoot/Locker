package locker.netlib;

/**
 * @param <C> the listeners' class.
 * @author Marco Cipriani
 * @version 0.1
 */
@SuppressWarnings("unused")
public interface WithListeners<C extends MessageListener> {

    /**
     * Adds a listener to the list.
     *
     * @param listener the new listener.
     */
    void addListener(C listener);
}