package lu.cortex.registry.container.service;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;

import lu.cortex.endpoints.Endpoint;
import lu.cortex.endpoints.EndpointDefault;
import lu.cortex.registry.container.service.utils.LRUinMemoryCache;
import lu.cortex.model.DomainDefinition;
import lu.cortex.spi.ServiceSpiDefault;

@Component
public class ServiceRegistryDefault implements ServiceRegistry {

    private static final int CACHE_TTL = 3600000; // 1 hour
    private static final int MAX_ITEMS = 1000; // 1000 items
    private static final int CACHE_EVICT_TIME = 60000; // 1 minute

    private LRUinMemoryCache<String, DomainDefinition> cache = new LRUinMemoryCache<>(CACHE_TTL, MAX_ITEMS, CACHE_EVICT_TIME);


    /**
     * Default.
     */
    public ServiceRegistryDefault() {
        // Empty.
    }

    protected Endpoint defaultEndpoint(final String name) {
        Objects.nonNull(name);
        return new EndpointDefault(name, StringUtils.EMPTY);
    }

    @Override
    public void installDomain(final DomainDefinition domain) {
        Objects.nonNull(domain);
        cache.put(domain.getName(), domain);
    }

    @Override
    public void removeDomain(final String name) {
        Objects.nonNull(name);
        if (!cache.isExist(name)) {
            throw new RuntimeException("Can't perform operation, the domain is not existing.");
        }
        cache.remove(name);
    }

    @Override
    public boolean isDomainExisting(final String name) {
        Objects.nonNull(name);
        return cache.isExist(name);
    }

    @Override
    public Optional<DomainDefinition> getDomain(final String name) {
        Objects.nonNull(name);
        if (!cache.isExist(name)) {
            return Optional.empty();
        }
        return Optional.of(cache.get(name));
    }

    @Override
    public List<DomainDefinition> getAllDomains() {
        return cache.getAll();
    }

    @Override
    public List<ServiceSpiDefault> getDomainServiceSpi(final String name) {
        Objects.nonNull(name);
        if (!cache.isExist(name)) {
            return Collections.emptyList();
        }
        return cache.get(name).getServices();
    }
}
