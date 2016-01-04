package lu.cortex.evt.model;

import lu.cortex.endpoints.Endpoint;

public interface Event {

    Endpoint getTo();

    Endpoint getFrom();

    EventType getType();

    EventBody getBody();
}
