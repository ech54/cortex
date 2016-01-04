package lu.cortex.evts.core;

import lu.cortex.evt.model.Event;

public interface EventsListener {

    /**
     * Method used to received an simple event.
     * @param event The event.
     */
    void receiveEvent(final Event event);

}
