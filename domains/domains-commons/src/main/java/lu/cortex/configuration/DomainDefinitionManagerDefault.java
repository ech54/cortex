package lu.cortex.configuration;

import lu.cortex.annotation.*;
import lu.cortex.async.DomainSender;
import lu.cortex.endpoints.Endpoint;
import lu.cortex.endpoints.EndpointDefault;
import lu.cortex.endpoints.EndpointPath;
import lu.cortex.spi.DomainDefinition;
import lu.cortex.spi.DomainDefinitionDefault;
import lu.cortex.spi.ServiceSpi;
import lu.cortex.spi.ServiceSpiDefault;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.ListableBeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.config.MethodInvokingFactoryBean;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.lang.annotation.Annotation;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

/**
 * Component loads initially the domain at start-up. In using spring property,
 *  all business configurations (@see DomainConfiguration, AsyncProcessName, etc..)
 */
@Component
@Order(1)
public class DomainDefinitionManagerDefault implements InitializingBean, BeanFactoryAware, DomainDefinitionManager {

    // Reference on the applicative logger.
    private static final Logger LOGGER = LoggerFactory.getLogger(DomainDefinitionManager.class);

    // Reference on the domain configuration.
    private Map<String, Object> domain = new ConcurrentHashMap<>();

    private Map<String, Map<String, MethodInvokingFactoryBean>> processes = new ConcurrentHashMap<>();

    protected Map<String, List<ServiceSpiDefault>> services = new ConcurrentHashMap<>();

    private Map<String, Map<String, MethodInvokingFactoryBean>> asyncProcesses = new ConcurrentHashMap<>();

    private DomainDefinitionReader reader = new DomainDefinitionReader();

    private ListableBeanFactory beanFactory;

    private Map<String, Endpoint> origins = new ConcurrentHashMap<>();

    @Override
    public void afterPropertiesSet() throws Exception {
        this.beanFactory.getBeansWithAnnotation(DomainConfiguration.class).entrySet().forEach(e -> {
            this.domain.put(getAlias(e.getValue()), e.getValue());
        });

        this.domain.entrySet().forEach(e -> {
                final String domainName =  getAlias(e.getValue());
                this.processes.put(domainName, this.reader.extractProcess(this.beanFactory.getBeansWithAnnotation(ProcessName.class), domainName));
                this.asyncProcesses.put(domainName, this.reader.extractAsyncProcess(this.beanFactory.getBeansWithAnnotation(AsyncProcessName.class), domainName));
                services.put(domainName, this.reader.extractServiceSpi(this.processes.get(domainName), this.asyncProcesses.get(domainName)));
                origins.put(domainName, new EndpointDefault(getAlias(e.getValue()), domainName));
            }
        );
        this.getDomainDefinitions().stream().forEach(d -> LOGGER.info("Find domain definition: " + d));
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

    public List<DomainDefinition> getDomainDefinitions() {
        final List<DomainDefinition> definitions = new ArrayList<>();
        domain.entrySet().stream().forEach(e -> {
            final DomainDefinitionDefault definition = new DomainDefinitionDefault();
            definition.setName(e.getKey());
            definition.setLocation((EndpointDefault) origins.get(e.getKey()));
            definition.setServices(services.get(e.getKey()));
            definitions.add(definition);
        });
        return definitions;
    }

    protected Optional<MethodInvokingFactoryBean> getAsyncProcesses(final Endpoint endpoint) {
        if (!getAsyncProcesses(endpoint.getSystemAlias()).isEmpty()) {
            return Optional.of(getAsyncProcesses(endpoint.getSystemAlias()).get(EndpointPath.toString(endpoint)));
        }
        return Optional.empty();
    }

    static protected String getName(final Annotation annotation) {
        return (String) AnnotationUtils.getAnnotationAttributes(annotation).get("name");
    }

    public List<String> getAllAlias() {
        return this.domain.values().stream().map(v -> getAlias(v)).collect(Collectors.toList());
    }

    protected String getAlias(final Object object) {
        Objects.nonNull(object);
        final Annotation annotation = object.getClass().getAnnotation(DomainConfiguration.class);
        return (String) AnnotationUtils.getAnnotationAttributes(annotation).get("alias");
    }

    public Map<String, Object> getDomain() {
        return this.domain;
    }
    public Map<String, MethodInvokingFactoryBean> getProcesses(final String name) {
        return this.processes.get(name);
    }
    public Map<String, MethodInvokingFactoryBean> getAsyncProcesses(final String name) {
        return this.asyncProcesses.get(name);
    }

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        this.beanFactory = (ListableBeanFactory) beanFactory;
    }
}
