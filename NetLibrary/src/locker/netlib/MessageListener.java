package locker.netlib;

/**
 * Listener interface for generic messages.
 *
 * @param <MessageType> the type of message.
 * @author Marco Cipriani
 * @version 0.1
 */
@Deprecated
public interface MessageListener<MessageType> {

    /**
     * Invoked when a new message arrives.
     *
     * @param msg the received message.
     */
    void onMessage(MessageType msg);
}