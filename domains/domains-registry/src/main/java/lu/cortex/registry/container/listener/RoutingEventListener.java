package lu.cortex.registry.container.listener;

import java.lang.annotation.Annotation;
import java.time.Instant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.stereotype.Component;

import lu.cortex.annotation.AsyncProcessName;
import lu.cortex.annotation.DomainConfiguration;
import lu.cortex.annotation.OnMessage;
import lu.cortex.async.DomainSender;
import lu.cortex.evt.model.Event;
import lu.cortex.evt.model.EventBuilder;
import lu.cortex.registry.container.RegistryDomain;
import lu.cortex.registry.container.service.ServiceRegistry;

@Component
@AsyncProcessName(name = "routing-event")
public class RoutingEventListener {

    @Autowired
    private RegistryDomain registry;

    @Autowired
    private DomainSender domainSender;

    @Autowired
    private ServiceRegistry serviceRegistry;

    @Autowired
    private RedisMessageListenerContainer container;

    @OnMessage(name="brodcast")
    public void broadcast(final Event event) {
        final long sessionId = Instant.now().toEpochMilli();
        final String origin = getAlias() + "-" + sessionId;
        final Event eventSendFromRegistry =
                            EventBuilder
                                .clone(event)
                                .from(getName(), origin)
                                .withSessionId(sessionId)
                                .build();

        //TODO qui doit recevoir le message ?
        domainSender.send(eventSendFromRegistry);

    }

    protected void handleCallerReturn(final Event event,
            final String origin, final long sessionId) {
        container.addMessageListener((message, pattern) -> {
            final long initialSession = sessionId;
            final Event original = event;
            final Event fromDomain = EventBuilder.fromJson(message.toString());

            //TODO a qui je dois renvoyer le message ?
        }, new ChannelTopic(origin));
    }



    protected String getName() {
        final Annotation annotation = registry.getClass().getAnnotation(DomainConfiguration.class);
        return (String) AnnotationUtils.getAnnotationAttributes(annotation).get("name");
    }

    protected String getAlias() {
        final Annotation annotation = registry.getClass().getAnnotation(DomainConfiguration.class);
        return (String) AnnotationUtils.getAnnotationAttributes(annotation).get("alias");
    }

}
