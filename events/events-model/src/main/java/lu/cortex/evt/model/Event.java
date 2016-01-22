package lu.cortex.evt.model;

import lu.cortex.endpoints.Endpoint;

public interface Event {
    /**
     * Provide the original endpoint where the event
     *  was been emitted.
     * @return The original endpoint.
     */
    Endpoint getOrigin();

    /**
     * Provide the reception endpoint which will consumes
     *  the event.
     * @return The destination endpoint.
     */
    Endpoint getDestination();

    EventBody getBody();
}
