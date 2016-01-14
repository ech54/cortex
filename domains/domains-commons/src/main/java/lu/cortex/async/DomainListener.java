package lu.cortex.async;

import java.io.IOException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.fasterxml.jackson.databind.ObjectMapper;

import lu.cortex.configuration.DomainDefinitionExporter;
import lu.cortex.evt.model.Event;
import lu.cortex.evt.model.EventDefault;

@Component
public class DomainListener {

    private static final Logger LOGGER = LoggerFactory.getLogger(DomainListener.class);

    @Autowired
    private DomainDefinitionExporter domainDefinitionExporter;

    // Automatically call by redis message listener.
    public void receiveMessage(String message) {
        LOGGER.info("Received <" + message + ">");
        try {
            final ObjectMapper mapper = new ObjectMapper();
            final Event event = mapper.readValue(message, EventDefault.class);
            domainDefinitionExporter.executeAsyncProcess(event.getTo(), event);
        } catch(final IOException exception) {
            throw new RuntimeException(exception);
        }
    }
}
