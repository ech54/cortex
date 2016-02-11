package lu.cortex.evt.model;

import java.time.Instant;
import com.fasterxml.jackson.databind.ObjectMapper;

import lu.cortex.endpoints.*;

public class EventBuilder {

    private EventDefault event;

    public EventBuilder() {
        this.event = new EventDefault();
        this.event.setBody(new EventBodyDefault());
    }

    public static EventBuilder from(final Endpoint from) {
        final EventBuilder builder = new EventBuilder();
        builder.event.setOrigin((EndpointDefault) from);
        return builder;
    }

    public static EventBuilder returnToSender(final Event event) {
        final EventBuilder builder = new EventBuilder();
        builder.event.setOrigin((EndpointDefault) event.getDestination());
        builder.event.setDestination((EndpointDefault) event.getOrigin());
        builder.event.setSession((SessionDefault) event.getSession());
        return builder;
    }

    public static EventBuilder clone(final Event event) {
        final EventBuilder builder = new EventBuilder();
        builder.event.setOrigin((EndpointDefault) event.getOrigin());
        builder.event.setDestination((EndpointDefault) event.getDestination());
        builder.event.setSession((SessionDefault) event.getSession());
        builder.event.setBody((EventBodyDefault) event.getBody());
        return builder;
    }

    public EventBuilder from(final String system, final String path) {
        this.event.setOrigin(new EndpointDefault(system, path));
        return this;
    }

    public  EventBuilder to(final Endpoint to) {
        this.event.setDestination((EndpointDefault) to);
        return this;
    }

    public EventBuilder to(final String system, final String path) {
        this.event.setDestination(new EndpointDefault(system, path));
        return this;
    }

    public EventBuilder withSessionId(final long sessionId) {
        event.getSession().setId(sessionId);
        return this;
    }

    @Deprecated
    public EventBuilder withType(final EventType type) {
        //((EventDefault) event).setType(type);
        return this;
    }

    public EventBuilder withPayload(final String payload) {
        ((EventBodyDefault) event.getBody()).setPayload(payload);
        return this;
    }

    public EventBuilder withPayLoad(final Object body) {
        ((EventBodyDefault) event.getBody()).setPayload(toJson(body));
        return this;
    }

    public static Event fromJson(final String message) {
        try {
            final ObjectMapper mapper = new ObjectMapper();
            return  mapper.readValue(message, EventDefault.class);
        } catch(final Exception exception) {
            throw new RuntimeException("Can't transform to java object.", exception);
        }
    }

    protected String toJson(final Object body) {
        try {
            final ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.writer().writeValueAsString(body);
        } catch (final Exception exception) {
            throw new RuntimeException("can't convert to json=" + body, exception);
        }
    }


    public Event build() {
        return event;
    }
}
