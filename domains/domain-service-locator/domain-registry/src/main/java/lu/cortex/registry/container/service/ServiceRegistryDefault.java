package lu.cortex.registry.container.service;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.apache.commons.lang.StringUtils;

import lu.cortex.endpoints.Endpoint;
import lu.cortex.endpoints.EndpointDefault;
import lu.cortex.spi.DomainDefinition;
import lu.cortex.spi.ServiceSpi;

public class ServiceRegistryDefault implements ServiceRegistry {

    //Local reference to the cache which contains <endpoint, domainDefinition> pair.
    private Map<Endpoint, DomainDefinition> cache = new ConcurrentHashMap<>();

    /**
     * Default.
     */
    public ServiceRegistryDefault() {
        //
    }

    @Override
    public boolean installDomain(final DomainDefinition domain) {
        cache.put(new EndpointDefault(domain.getName(), StringUtils.EMPTY), domain);
        return false;
    }

    @Override
    public boolean removeDomain(final String name) {
        return false;
    }

    @Override
    public boolean isDomainExisting(final String name) {
        return false;
    }

    @Override
    public DomainDefinition getDomain(final Endpoint endpoint) {
        return null;
    }

    @Override
    public DomainDefinition getDomain(final String name) {
        return null;
    }

    @Override
    public List<DomainDefinition> getAllDomains() {
        return null;
    }

    @Override
    public List<ServiceSpi> getDomainServiceSpi(final String name) {
        return null;
    }
}
