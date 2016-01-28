package lu.cortex.configuration;

import lu.cortex.annotation.*;
import lu.cortex.async.DomainSender;
import lu.cortex.endpoints.Endpoint;
import lu.cortex.endpoints.EndpointDefault;
import lu.cortex.endpoints.EndpointPath;
import lu.cortex.spi.DomainDefinition;
import lu.cortex.spi.ServiceSpi;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.ListableBeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.config.MethodInvokingFactoryBean;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.stereotype.Component;

import java.lang.annotation.Annotation;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Component loads initially the domain at start-up. In using spring property,
 *  all business configurations (@see DomainConfiguration, AsyncProcessName, etc..)
 */
@Component
public class DomainDefinitionManagerDefault implements InitializingBean, BeanFactoryAware, DomainDefinitionManager {

    // Reference on the domain configuration.
    private Map<String, Object> domain = new ConcurrentHashMap<>();

    private Map<String, MethodInvokingFactoryBean> processes = new ConcurrentHashMap<>();

    protected List<ServiceSpi> services = new ArrayList<>();

    private Map<String, MethodInvokingFactoryBean> asyncProcesses = new ConcurrentHashMap<>();

    private DomainDefinitionReader reader = new DomainDefinitionReader();

    private ListableBeanFactory beanFactory;

    private Endpoint origin;

    @Autowired
    private DomainSender domainSender;

    @Autowired
    @Qualifier("registry.install.endpoint")
    private Endpoint registryInstallEndpoint;

    @Override
    public void afterPropertiesSet() throws Exception {
        this.domain.putAll(this.beanFactory.getBeansWithAnnotation(DomainConfiguration.class));
        final String domainName = getDomainName();
        this.processes.putAll(this.reader.extractProcess(this.beanFactory.getBeansWithAnnotation(ProcessName.class), domainName));
        this.asyncProcesses.putAll(this.reader.extractAsyncProcess(this.beanFactory.getBeansWithAnnotation(AsyncProcessName.class), domainName));
        services.addAll(this.reader.extractServiceSpi(this.processes, this.asyncProcesses));
        origin = new EndpointDefault(getAlias(), getDomainName());
        exportDomainDefinition();
    }

    protected void exportDomainDefinition() {
        final ExecutorService pool = Executors.newFixedThreadPool(1);
        Runtime.getRuntime().addShutdownHook(new Thread(){
            @Override
            public void run() {
                if (pool != null) {
                    pool.shutdown();
                }
            }
        });
        final DomainDefinitionExporterTask task = new DomainDefinitionExporterTask(getRegistryInstallEndpoint(), this, getDomainSender());
        //Callable<?> runTask = () -> (task.run());
        pool.submit(() -> task.run());
    }

    public Object executeAsyncProcess(final Endpoint endpoint, Object...args) {
        Optional<MethodInvokingFactoryBean> optional = getAsyncProcesses(endpoint);
        if (!optional.isPresent()) {
            throw new RuntimeException("No async process for expected endpoint: " + endpoint);
        }
        final MethodInvokingFactoryBean factory = optional.get();
        factory.setArguments(null);
        try {
            factory.setArguments(args);
            factory.prepare();
            return factory.invoke();
        }catch(final Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    public DomainDefinition getDomainDefinition() {
        return new DomainDefinition() {
            @Override
            public String toString() {
                final StringBuffer buffer = new StringBuffer("definition={");
                buffer.append(" name:" + getName());
                buffer.append(", location:"+ getLocation());
                buffer.append("}");
                return buffer.toString();
            }

            @Override
            public String getName() {
                return getDomainName();
            }

            @Override
            public Endpoint getLocation() {
                return origin;
            }

            @Override
            public List<ServiceSpi> getServices() {
                return services;
            }
        };
    }

    protected Optional<MethodInvokingFactoryBean> getAsyncProcesses(final Endpoint endpoint) {
        if (getAsyncProcesses().containsKey(EndpointPath.toString(endpoint))) {
            return Optional.of(getAsyncProcesses().get(EndpointPath.toString(endpoint)));
        }
        return Optional.empty();
    }

    public String getDomainName() {
        if (domain.isEmpty()) {
            throw new RuntimeException("No configured domain ...");
        }
        final Optional<Object> firstResult = domain.values().stream().findFirst();
        if (!firstResult.isPresent()) {
            throw new RuntimeException("No configured domain ...");
        }
        return getName(firstResult.get().getClass().getAnnotation(DomainConfiguration.class));
    }

    static protected String getName(final Annotation annotation) {
        return (String) AnnotationUtils.getAnnotationAttributes(annotation).get("name");
    }

    protected String getAlias() {
        if (domain.isEmpty()) {
            throw new RuntimeException("No configured domain ...");
        }
        final Optional<Object> firstResult = domain.values().stream().findFirst();
        if (!firstResult.isPresent()) {
            throw new RuntimeException("No configured domain ...");
        }
        final Annotation annotation = firstResult.get().getClass().getAnnotation(DomainConfiguration.class);
        return (String) AnnotationUtils.getAnnotationAttributes(annotation).get("alias");
    }

    public Map<String, Object> getDomain() {
        return this.domain;
    }
    public Map<String, MethodInvokingFactoryBean> getProcesses() {
        return this.processes;
    }
    public Map<String, MethodInvokingFactoryBean> getAsyncProcesses() {
        return this.asyncProcesses;
    }

    public Endpoint getRegistryInstallEndpoint() {
        return registryInstallEndpoint;
    }

    public void setRegistryInstallEndpoint(Endpoint registryInstallEndpoint) {
        this.registryInstallEndpoint = registryInstallEndpoint;
    }

    public DomainSender getDomainSender() {
        return domainSender;
    }

    public void setDomainSender(DomainSender domainSender) {
        this.domainSender = domainSender;
    }

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        this.beanFactory = (ListableBeanFactory) beanFactory;
    }
}
