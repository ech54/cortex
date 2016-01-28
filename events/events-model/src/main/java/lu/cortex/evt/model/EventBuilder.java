package lu.cortex.evt.model;

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
    public EventBuilder from(final String system, final String path) {
        this.event.setOrigin(new EndpointDefault(system, path));
        return this;
    }

    public static EventBuilder to(final Endpoint to) {
        final EventBuilder builder = new EventBuilder();
        builder.event.setDestination((EndpointDefault) to);
        return builder;
    }

    public EventBuilder to(final String system, final String path) {
        this.event.setDestination(new EndpointDefault(system, path));
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

    public Event build() {
        return event;
    }
}
