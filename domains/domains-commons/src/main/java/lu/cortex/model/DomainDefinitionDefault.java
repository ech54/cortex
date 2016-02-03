package lu.cortex.model;

import java.util.ArrayList;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonRootName;

import lu.cortex.endpoints.Endpoint;
import lu.cortex.endpoints.EndpointDefault;
import lu.cortex.spi.ServiceSpiDefault;

@JsonRootName("domain-definition")
public class DomainDefinitionDefault implements DomainDefinition {

    private String name;
    private EndpointDefault location;
    private List<ServiceSpiDefault> service = new ArrayList<>();

    public DomainDefinitionDefault() {}

    @Override
    public String toString() {
        final StringBuffer buffer = new StringBuffer("definition={");
        buffer.append(" name: " + getName());
        buffer.append(", location: "+ getLocation());
        buffer.append(", services: "+ getServices());
        buffer.append("}");
        return buffer.toString();
    }

    @Override
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public Endpoint getLocation() {
        return location;
    }

    public void setLocation(EndpointDefault location) {
        this.location = location;
    }

    public List<ServiceSpiDefault> getServices() {
        return service;
    }

    public void setServices(final List<ServiceSpiDefault> services) {
        this.service = services;
    }
}
