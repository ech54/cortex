
package lu.cortex.configuration;

import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lu.cortex.async.DomainSender;
import lu.cortex.endpoints.Endpoint;
import lu.cortex.evt.model.Event;
import lu.cortex.evt.model.EventBuilder;
import lu.cortex.model.DomainDefinition;

public class DomainDefinitionExporterTask extends Thread{

    private static final Logger LOGGER = LoggerFactory.getLogger(DomainDefinitionExporterTask.class);

    private Endpoint registryEndpoint;

    private List<DomainDefinition> definitions = new ArrayList<>();

    private DomainSender sender;

    public DomainDefinitionExporterTask(
            final Endpoint registryEndpoint,
            final List<DomainDefinition> definitions,
            final DomainSender sender) {
        this.registryEndpoint = registryEndpoint;
        this.definitions.addAll(definitions);
        this.sender = sender;
    }

    @Override
    public void run() {
        try {
            Thread.sleep(3 * 1000);
            push();
        } catch (final Exception e) {
            throw new RuntimeException(e);
        }
    }


    protected void push() {
        LOGGER.info("Export configuration");
        final List<DomainDefinition> definitions = this.definitions;
        if (definitions == null || definitions.isEmpty()) {
            throw new RuntimeException("Domain definition is not resolved.");
        }
        definitions.stream().forEach(definition -> {
            final Event event = EventBuilder
                    .from(definition.getLocation())
                    .to(getRegistryEndpoint())
                    .withPayload(convertToJson(definition))
                    .build();
            LOGGER.info("Configuration to export: " + event);
            getSender().send(event);
        });
    }

    private String convertToJson(final DomainDefinition definition) {
        final ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.writeValueAsString(definition);
        } catch(final JsonProcessingException exception) {
            throw new RuntimeException(exception);
        }
    }

    public Endpoint getRegistryEndpoint() {
        return registryEndpoint;
    }

    public void setRegistryEndpoint(Endpoint registryEndpoint) {
        this.registryEndpoint = registryEndpoint;
    }

    public DomainSender getSender() {
        return sender;
    }

    public void setSender(DomainSender sender) {
        this.sender = sender;
    }
}
