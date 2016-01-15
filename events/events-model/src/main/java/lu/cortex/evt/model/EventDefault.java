package lu.cortex.evt.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;
import lu.cortex.endpoints.Endpoint;
import lu.cortex.endpoints.EndpointDefault;

@JsonRootName("event")
public class EventDefault implements Event {

    @JsonProperty
    EndpointDefault to;
    @JsonProperty
    EndpointDefault from;
    @JsonProperty
    EventType type;
    @JsonProperty
    EventBodyDefault body;

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
    public EndpointDefault getTo() {
        return to;
    }

    public void setTo(EndpointDefault to) {
        this.to = (EndpointDefault) to;
    }

    @Override
    public EndpointDefault getFrom() {
        return from;
    }

    public void setFrom(EndpointDefault from) {
        this.from = (EndpointDefault) from;
    }

    @Override
    public EventType getType() {
        return type;
    }

    public void setType(EventType type) {
        this.type = type;
    }

    @Override
    public EventBodyDefault getBody() {
        return body;
    }

    public void setBody(EventBodyDefault body) {
        this.body = body;
    }
}
