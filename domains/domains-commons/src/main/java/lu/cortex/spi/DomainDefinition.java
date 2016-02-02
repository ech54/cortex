package lu.cortex.spi;

import lu.cortex.endpoints.Endpoint;

import java.util.List;

/**
 * Minimal information to define a business lu.cortex.registry.container.cache.
 *
 * Created by echarton on 04/01/16.
 */
public interface DomainDefinition {

    /**
     * Accessor in reading on the lu.cortex.registry.container.cache's name.
     * @return The lu.cortex.registry.container.cache name.
     */
    String getName();

    /**
     * Accessor on the location of the lu.cortex.registry.container.cache.
     * @return The endpoint.
     */
    Endpoint getLocation();

    /**
     * Accessor in reading on the domain services.
     * @return The domain services.
     */
    List<ServiceSpiDefault> getServices();
}
