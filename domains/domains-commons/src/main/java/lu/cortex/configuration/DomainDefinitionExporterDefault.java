package lu.cortex.configuration;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import lu.cortex.async.DomainSender;
import lu.cortex.endpoints.Endpoint;

@Component
@Order(1000)
public class DomainDefinitionExporterDefault implements InitializingBean {

    @Autowired
    DomainDefinitionManagerDefault domainDefinitionManagerDefault;

    @Autowired
    private DomainSender domainSender;

    @Autowired
    @Qualifier("registry.install.endpoint")
    private Endpoint registryInstallEndpoint;

    @Override
    public void afterPropertiesSet() throws Exception {
        exportDomainDefinition();
    }


    protected void exportDomainDefinition() {
        System.out.println("start exportDomainDefinition");
        final ExecutorService pool = Executors.newFixedThreadPool(1);
        Runtime.getRuntime().addShutdownHook(new Thread(){
            @Override
            public void run() {
                if (pool != null) {
                    pool.shutdown();
                }
            }
        });
        final DomainDefinitionExporterTask task = new DomainDefinitionExporterTask(getRegistryInstallEndpoint(), this.domainDefinitionManagerDefault, getDomainSender());
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
