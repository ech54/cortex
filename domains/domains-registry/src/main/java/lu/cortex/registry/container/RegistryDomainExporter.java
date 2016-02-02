package lu.cortex.registry.container;

import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import lu.cortex.configuration.DomainDefinitionManagerDefault;
import lu.cortex.endpoints.Endpoint;
import lu.cortex.registry.container.service.ServiceRegistry;
import lu.cortex.registry.container.service.ServiceRegistryDefault;
import lu.cortex.spi.DomainDefinition;

/**
 * <class_description>
 **/
@Component
@Order(2)
public class RegistryDomainExporter  implements InitializingBean {

    private static final Logger LOGGER = LoggerFactory.getLogger(RegistryDomainExporter.class);

    @Autowired
    private Endpoint registryEndpoint;

    @Autowired
    private ServiceRegistryDefault serviceRegistry;

    @Autowired
    private DomainDefinitionManagerDefault domainDefinitionManager;

    @Override
    public void afterPropertiesSet() throws Exception {
        LOGGER.info("export registry domain configuration");
        final Optional<DomainDefinition> domainRegisty = getDomainDefinitionManager().getDomainDefinitions()
                                                            .stream()
                                                            .filter(d -> d.getName().equals(registryEndpoint.getSystemAlias()))
                                                            .findFirst();
        if (!domainRegisty.isPresent()) {
            throw new RuntimeException("Registry domain is not properly initialized !");
        }
        getServiceRegistry().installDomain(domainRegisty.get());
    }

    public ServiceRegistry getServiceRegistry() {
        return serviceRegistry;
    }

    public void setServiceRegistry(final ServiceRegistryDefault serviceRegistry) {
        this.serviceRegistry = serviceRegistry;
    }

    public DomainDefinitionManagerDefault getDomainDefinitionManager() {
        return domainDefinitionManager;
    }

    public void setDomainDefinitionManager(final DomainDefinitionManagerDefault domainDefinitionManager) {
        this.domainDefinitionManager = domainDefinitionManager;
    }
}
