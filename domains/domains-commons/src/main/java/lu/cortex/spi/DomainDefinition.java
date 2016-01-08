package lu.cortex.spi;

import lu.cortex.endpoints.Endpoint;

/**
 * Minimal information to define a business service.
 *
 * Created by echarton on 04/01/16.
 */
public interface DomainDefinition {

    /**
     * Accessor in reading on the service's name.
     * @return The service name.
     */
    String getName();

    /**
     * Accessor on the location of the service.
     * @return The endpoint.
     */
    Endpoint getLocation();
}
