package lu.cortex.evt.model;

import lu.cortex.endpoints.*;

public class EventDefault implements Event {

    Endpoint to;
    Endpoint from;
    EventType type;
    EventBody body;

    public EventDefault() {}

    @Override
    public Endpoint getTo() {
        return to;
    }

    public void setTo(Endpoint to) {
        this.to = to;
    }

    @Override
    public Endpoint getFrom() {
        return from;
    }

    public void setFrom(Endpoint from) {
        this.from = from;
    }

    @Override
    public EventType getType() {
        return type;
    }

    public void setType(EventType type) {
        this.type = type;
    }

    @Override
    public EventBody getBody() {
        return body;
    }

    public void setBody(EventBody body) {
        this.body = body;
    }
}
