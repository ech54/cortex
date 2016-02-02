package lu.cortex.async;

import java.io.IOException;
import java.util.Objects;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.fasterxml.jackson.databind.ObjectMapper;

import lu.cortex.configuration.DomainDefinitionManagerDefault;
import lu.cortex.evt.model.Event;
import lu.cortex.evt.model.EventDefault;

/**
 * Domain listener is main entry point for all asynchronous messages.
 * Then routing message is applied on each event to dedicated business process.
 */
@Component
public class DomainListenerDefault {
    // Default applicative logger.
    private static final Logger LOGGER = LoggerFactory.getLogger(DomainListenerDefault.class);

    @Autowired
    private DomainDefinitionManagerDefault domainDefinitionManagerDefault;

    /**
     * Method is automatically calls by redis message listener.
     * This method delegates to asynchronous process which have been
     *  discovered when domain has been loaded.
     **/
    public void receiveMessage(final String message) {
        LOGGER.info("Receive message : <" + message + ">");
        try {
            Objects.nonNull(message);
            final ObjectMapper mapper = new ObjectMapper();
            final Event event = mapper.readValue(message, EventDefault.class);
            Objects.nonNull(event);
            LOGGER.info("Translate to event: <" + event + ">");
            domainDefinitionManagerDefault.executeAsyncProcess(event.getDestination(), event);
        } catch (final IOException exception) {
            throw new RuntimeException(exception);
        }
    }
}
