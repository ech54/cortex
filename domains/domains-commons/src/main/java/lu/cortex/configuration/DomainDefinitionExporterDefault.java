package lu.cortex.configuration;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import lu.cortex.async.DomainSender;
import lu.cortex.endpoints.Endpoint;
import lu.cortex.spi.DomainDefinition;

@Component
@Order(1000)
public class DomainDefinitionExporterDefault implements InitializingBean {

    // The domain definition exporter logger.
    final static Logger LOGGER = LoggerFactory.getLogger(DomainDefinitionExporterDefault.class);

    @Autowired
    DomainDefinitionManagerDefault domainDefinitionManagerDefault;

    @Autowired
    private DomainSender domainSender;

    @Autowired
    @Qualifier("registry.install.endpoint")
    private Endpoint registryInstallEndpoint;

    @Override
    public void afterPropertiesSet() throws Exception {
        LOGGER.info("Load DomainDefinition exporter: (Thread: " + Thread.currentThread().getId() + ")");
        exportDomains();
    }


    protected void exportDomains() {

        exportStandardDomain();
    }

    protected void exportStandardDomain() {
        LOGGER.info("start process of exporting domain definition");
        final List<DomainDefinition> definitions =
                this.domainDefinitionManagerDefault.getDomainDefinitions()
                        .stream()
                        .filter(d -> !d.getName().equals(registryInstallEndpoint.getSystemAlias()))
                        .collect(Collectors.toList());
        if (definitions.isEmpty()) {
            return;
        }
        final ExecutorService pool = Executors.newFixedThreadPool(1);
        Runtime.getRuntime().addShutdownHook(new Thread(){
            @Override
            public void run() {
                if (pool != null) {
                    pool.shutdown();
                }
            }
        });
        final DomainDefinitionExporterTask task = new DomainDefinitionExporterTask(getRegistryInstallEndpoint(), definitions, getDomainSender());
        pool.submit(() -> task.run());
    }


    public Endpoint getRegistryInstallEndpoint() {
        return this.registryInstallEndpoint;
    }

    public void setRegistryInstallEndpoint(Endpoint registryInstallEndpoint) {
        this.registryInstallEndpoint = registryInstallEndpoint;
    }

    public DomainSender getDomainSender() {
        return this.domainSender;
    }

    public void setDomainSender(DomainSender domainSender) {
        this.domainSender = domainSender;
    }
}
