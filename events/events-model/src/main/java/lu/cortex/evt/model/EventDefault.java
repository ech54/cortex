package lu.cortex.evt.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import lu.cortex.endpoints.EndpointDefault;

@JsonRootName("event")
public class EventDefault implements Event {

    @JsonProperty
    EndpointDefault to;
    @JsonProperty
    EndpointDefault from;
    @JsonProperty
    EventBodyDefault body;

    public EventDefault() {}

    @Override
    public String toString() {
        final StringBuffer buffer = new StringBuffer("event={");
        buffer.append("(from):" + this.from+"->(to):" + this.to);
        buffer.append(" , body:" + this.body);
        buffer.append("}");
        return buffer.toString();
    }

    @Override
    public EndpointDefault getOrigin() {
        return to;
    }

    public void setOrigin(EndpointDefault to) {
        this.to = (EndpointDefault) to;
    }

    @Override
    public EndpointDefault getDestination() {
        return from;
    }

    public void setDestination(EndpointDefault from) {
        this.from = (EndpointDefault) from;
    }

    @Override
    public EventBodyDefault getBody() {
        return body;
    }

    public void setBody(EventBodyDefault body) {
        this.body = body;
    }
}
