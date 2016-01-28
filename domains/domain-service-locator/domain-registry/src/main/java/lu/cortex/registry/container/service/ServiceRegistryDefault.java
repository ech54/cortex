package lu.cortex.registry.container.service;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;

import lu.cortex.endpoints.Endpoint;
import lu.cortex.endpoints.EndpointDefault;
import lu.cortex.registry.container.service.utils.LRUinMemoryCache;
import lu.cortex.spi.DomainDefinition;
import lu.cortex.spi.ServiceSpi;

@Component
public class ServiceRegistryDefault implements ServiceRegistry {

    private static final int CACHE_TTL = 3600000; // 1 hour
    private static final int MAX_ITEMS = 1000; // 1000 items
    private static final int CACHE_EVICT_TIME = 60000; // 1 minute

    private LRUinMemoryCache<Endpoint, DomainDefinition> cache = new LRUinMemoryCache<>(CACHE_TTL, MAX_ITEMS, CACHE_EVICT_TIME);


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
        cache.put(defaultEndpoint(domain.getName()), domain);
    }

    @Override
    public void removeDomain(final String name) {
        Objects.nonNull(name);
        if (!cache.isExist(defaultEndpoint(name))) {
            throw new RuntimeException("Can't perform operation, the domain is not existing.");
        }
        cache.remove(defaultEndpoint(name));
    }

    @Override
    public boolean isDomainExisting(final String name) {
        Objects.nonNull(name);
        return cache.isExist(defaultEndpoint(name));
    }

    @Override
    public Optional<DomainDefinition> getDomain(final String name) {
        Objects.nonNull(name);
        if (!cache.isExist(defaultEndpoint(name))) {
            return Optional.empty();
        }
        return Optional.of(cache.get(defaultEndpoint(name)));
    }

    @Override
    public List<DomainDefinition> getAllDomains() {
        return cache.getAll();
    }

    @Override
    public List<ServiceSpi> getDomainServiceSpi(final String name) {
        Objects.nonNull(name);
        if (!cache.isExist(defaultEndpoint(name))) {
            return Collections.emptyList();
        }
        return cache.get(defaultEndpoint(name)).getServices();
    }
}
