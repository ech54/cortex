package lu.cortex.parser;

import java.util.Arrays;
import java.util.List;
import org.junit.Assert;
import org.junit.Test;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lu.cortex.endpoints.Endpoint;
import lu.cortex.endpoints.EndpointDefault;
import lu.cortex.model.DomainDefinition;
import lu.cortex.spi.ServiceSpiDefault;

public class DomainDefinitionToJsonTestCase {

    @Test
    public void toJson() throws JsonProcessingException{
        final ObjectMapper mapper = new ObjectMapper();
        final String expectedResult = "{\"name\":\"simple\",\"location\":{\"alias\":\"simple\",\"path\":\"/simple\"},"
                + "\"services\":[{\"name\":\"customMethod\",\"references\":[]},{\"name\":\"defaultMethod\",\"references\":[]}]}";
        Assert.assertEquals(expectedResult, mapper.writeValueAsString(getSimpleDomain()));
    }

    protected DomainDefinition getSimpleDomain() {
        return new DomainDefinition() {
            @Override
            public String getName() {
                return "simple";
            }

            @Override
            public Endpoint getLocation() {
                return new EndpointDefault("simple", "/simple");
            }

            @Override
            public List<ServiceSpiDefault> getServices() {
                return Arrays.asList(
                        new ServiceSpiDefault("customMethod"),
                        new ServiceSpiDefault("defaultMethod"));
            }
        };
    }

}
