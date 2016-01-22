package lu.cortex.async;

import lu.cortex.evt.model.Event;

/**
 * Domain sender is the component support asynchronous exchange between
 *  components of system.
 */
public interface DomainSender {
    /**
     * Send an <code>Event</code> to the <code>Endpoint</code>.
     * @param event The event to send.
     */
    void send(final Event event);

    /**
     * Send an <code>Event</code> in broadcast mode. That means
     *  event will be sent at all system endpoints.
     * @param event The event to broadcast.
     */
    void broadcast(final Event event);
}
