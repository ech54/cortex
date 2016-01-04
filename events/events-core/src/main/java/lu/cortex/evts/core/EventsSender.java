package lu.cortex.evts.core;

import lu.cortex.evt.model.Event;


public interface EventsSender {

    /**
     * Executor allow to send an event.
     * @param event The event.
     */
    void sendEvent(final Event event);

}
