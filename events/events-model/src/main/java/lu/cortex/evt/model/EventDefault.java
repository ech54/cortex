package lu.cortex.evt.model;

import lu.cortex.endpoints.*;

public class EventDefault implements Event {

    Endpoint to;
    Endpoint from;
    EventType type;
    EventBody body;

    public EventDefault() {}

    @Override
    public String toString() {
        final StringBuffer buffer = new StringBuffer("event={");
        buffer.append("(from):" + this.from+"->(to):" + this.to);
        buffer.append(" , type:" + this.type);
        buffer.append(" , body:" + this.body);
        buffer.append("}");
        return buffer.toString();
    }

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
