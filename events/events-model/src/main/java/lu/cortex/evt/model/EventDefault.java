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
        buffer.append("(from):" + this.from + "->(to):" + this.to);
        buffer.append(" , body:" + this.body);
        buffer.append("}");
        return buffer.toString();
    }

    @Override
    public EndpointDefault getOrigin() {
        return this.from;
    }

    public void setOrigin(EndpointDefault from) {
        this.from = (EndpointDefault) from;
    }

    @Override
    public EndpointDefault getDestination() {
        return this.to;
    }

    public void setDestination(EndpointDefault to) {
        this.to = (EndpointDefault) to;
    }

    @Override
    public EventBodyDefault getBody() {
        return body;
    }

    public void setBody(EventBodyDefault body) {
        this.body = body;
    }
}
