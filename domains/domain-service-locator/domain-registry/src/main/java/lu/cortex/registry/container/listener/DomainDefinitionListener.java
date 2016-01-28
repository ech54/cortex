package lu.cortex.registry.container.listener;

import java.io.IOException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.fasterxml.jackson.databind.ObjectMapper;

import lu.cortex.annotation.AsyncProcessName;
import lu.cortex.annotation.OnMessage;
import lu.cortex.evt.model.Event;
import lu.cortex.registry.container.service.ServiceRegistry;
import lu.cortex.spi.DomainDefinition;

@Component
@AsyncProcessName(name = "domain-definition")
public class DomainDefinitionListener {

    @Autowired
    private ServiceRegistry serviceRegistry;

    @OnMessage(name="install")
    public void install(final Event event) {
        final ObjectMapper mapper = new ObjectMapper();
        try {
            final DomainDefinition definition = mapper.readValue(event.getBody().getPaylodAsString(), DomainDefinition.class);
            serviceRegistry.installDomain(definition);
        }catch (final IOException exception) {
            throw new RuntimeException(exception); // TODO: create dlq and sent error message.
        }
    }

    @OnMessage(name="remove")
    public void remove(final Event event) {
        try{
            serviceRegistry.removeDomain(event.getBody().getPaylodAsString());
        }
        catch (final Exception exception) {
            throw new RuntimeException(exception); // TODO: create dlq and sent error message.
        }
    }

}
