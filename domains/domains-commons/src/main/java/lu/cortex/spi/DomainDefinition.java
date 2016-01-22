package lu.cortex.spi;

import lu.cortex.endpoints.Endpoint;

import java.util.List;

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

    /**
     * Accessor in reading on the domain services.
     * @return The domain services.
     */
    List<ServiceSpi> getServices();

    /**
     * Provide list of service by type.
     * @param type The service type.
     * @return All services associated with this type.
     * @deprecated (used DomainDefinition.getServices)
     */
    List<ServiceSpi> getServiceByType(final ServiceType type);

}
