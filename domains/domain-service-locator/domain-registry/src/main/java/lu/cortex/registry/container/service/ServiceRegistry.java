package lu.cortex.registry.container.service;

import java.util.List;
import java.util.Optional;

import lu.cortex.endpoints.Endpoint;
import lu.cortex.spi.DomainDefinition;
import lu.cortex.spi.ServiceSpi;

/**
 * Main interface to operate on the local <code>DomainDefinition</code>
 *  registry.
 *
 */
public interface ServiceRegistry {
    /**
     * Method installs a <code>DomainDefinition</code> into the registry.
     * @param domain The <code>DomainDefinition</code> to install.
     * @return <code>Indicate</code> if the domain has been installed.
     */
    void installDomain(final DomainDefinition domain);

    /**
     * Method indicates if the domain's name is existing.
     * @param name The domain's name.
     * @return <code>true</code> if the domain has been removed,
     *  else <code>false</code>.
     */
    void removeDomain(final String name);

    /**
     * Method indicates if the domain is existing in the registry.
     * @param name The domain's name.
     * @return <code>true</code> if the domain is existing,
     *  else <code>false</code>.
     */
    boolean isDomainExisting(final String name);

    /**
     * Provide the <code>DomainDefinition</code> based on its name.
     * @param name The domain name.
     * @return The corresponding domain definition.
     */
    Optional<DomainDefinition> getDomain(final String name);

    /**
     * Provide the full domain list available in the registry.
     * @return The domain list.
     */
    List<DomainDefinition> getAllDomains();

    /**
     * Provide the full lu.cortex.registry.container.cache spi list based on the domain
     *  name provide in argument.
     * @param name The domain name.
     * @return The list of services.
     */
    List<ServiceSpi> getDomainServiceSpi(final String name);


}
