
package lu.cortex.configuration;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lu.cortex.async.DomainSender;
import lu.cortex.endpoints.Endpoint;
import lu.cortex.evt.model.Event;
import lu.cortex.evt.model.EventBuilder;
import lu.cortex.spi.DomainDefinition;

public class DomainDefinitionExporterTask extends Thread{

    private Endpoint registryEndpoint;

    private DomainDefinitionManagerDefault manager;

    private DomainSender sender;

    public DomainDefinitionExporterTask(
            final Endpoint registryEndpoint,
            final DomainDefinitionManagerDefault manager,
            final DomainSender sender) {
        this.registryEndpoint = registryEndpoint;
        this.manager = manager;
        this.sender = sender;
    }

    @Override
    public void run() {
        process();
    }

    public boolean process() {
        try {
            System.out.println("process");
            while (true) {
                push();
                System.out.println("end push");
                Thread.sleep(120*1000);
            }
        } catch(final Exception exception) {
            throw new RuntimeException(exception);
        }
    }

    protected void push() {
        System.out.println("DomainDefinitionExporterTask: push");
        final DomainDefinition definition = manager.getDomainDefinition();
        if (definition == null) {
            throw new RuntimeException("Domain definition is not resolved.");
        }

        System.out.println("DomainDefinitionExporterTask: definition " + definition);

        final Event event = EventBuilder
                .from(definition.getLocation())
                .to(getRegistryEndpoint())
                .withPayload(convertToJson(definition))
                .build();
        System.out.println("DomainDefinitionExporterTask: send " + event);
        getSender().send(event);
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

    public DomainDefinitionManagerDefault getManager() {
        return manager;
    }

    public void setManager(final DomainDefinitionManagerDefault manager) {
        this.manager = manager;
    }

    public DomainSender getSender() {
        return sender;
    }

    public void setSender(DomainSender sender) {
        this.sender = sender;
    }
}
