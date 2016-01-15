package lu.cortex.configuration;

import lu.cortex.annotation.*;
import lu.cortex.endpoints.Endpoint;
import lu.cortex.endpoints.EndpointDefault;
import lu.cortex.endpoints.EndpointPath;
import lu.cortex.spi.DomainDefinition;
import lu.cortex.spi.ServiceSpi;
import lu.cortex.spi.ServiceSpiDefault;
import lu.cortex.spi.ServiceType;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.ListableBeanFactory;
import org.springframework.beans.factory.config.MethodInvokingFactoryBean;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.stereotype.Component;

import java.lang.annotation.Annotation;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Stream;
import com.sun.xml.internal.ws.addressing.EndpointReferenceUtil;

@Component
public class DomainDefinitionExporter implements InitializingBean, BeanFactoryAware {

    private Map<String, Object> domain = new ConcurrentHashMap<>();
    private Map<String, MethodInvokingFactoryBean> processes = new ConcurrentHashMap<>();
    protected List<ServiceSpi> services = new ArrayList<>();
    private Map<String, MethodInvokingFactoryBean> asyncProcesses = new ConcurrentHashMap<>();

    private ListableBeanFactory beanFactory;

    @Override
    public void afterPropertiesSet() throws Exception {
        domain.putAll(beanFactory.getBeansWithAnnotation(DomainConfiguration.class));
        final String domainName = getDomainName();
        initProcess(beanFactory.getBeansWithAnnotation(ProcessName.class), domainName);
        initAsyncProcess(beanFactory.getBeansWithAnnotation(AsyncProcessName.class), domainName);
    }

    protected void initProcess(final Map<String, Object> beans, final String domain) {
        beans.entrySet().stream()
            .forEach(e -> {
                processes.putAll(createInvokingBeans(e, domain, ProcessName.class, Reference.class));
            });
    }

    protected void initAsyncProcess(final Map<String, Object> beans, final String domain) {
        beans.entrySet().stream()
            .forEach(e -> {
                asyncProcesses.putAll(createInvokingBeans(e, domain, AsyncProcessName.class, OnMessage.class));
            });
    }

    public List<Endpoint> getEndpointIfExisting() {
        return new ArrayList<>();
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

    public Map<String, MethodInvokingFactoryBean> createInvokingBeans(
            Map.Entry entry,
            String domain,
            Class<? extends Annotation> pT,
            Class<? extends Annotation> rT) {
        try {
            final Map<String, MethodInvokingFactoryBean> result = new HashMap<>();
            final Annotation processAnnotation = entry.getValue().getClass().getAnnotation(pT);
            final ServiceSpi serviceSpi = new ServiceSpiDefault(getName(processAnnotation));
            Stream.of(entry.getValue().getClass().getDeclaredMethods())
                .filter(m -> m.isAnnotationPresent(rT))
                .forEach(m -> {
                    final MethodInvokingFactoryBean mBean = createFactory(entry);
                    final Annotation resultAnnotation = m.getAnnotation(rT);
                    mBean.setTargetObject(entry.getValue());
                    mBean.setTargetMethod(m.getName());
                    result.put(EndpointPath.buildPath(domain,
                                getName(processAnnotation),
                                getName(resultAnnotation)),
                            mBean);
                    serviceSpi.addReference(m.getName());
                });
            if (!serviceSpi.getReferences().isEmpty()) {
                services.add(serviceSpi);
            }
            return result;
        } catch (final Exception e) {
            throw new RuntimeException(e);
        }
    }

    static protected MethodInvokingFactoryBean createFactory(Map.Entry entry) {
        final MethodInvokingFactoryBean mBean = new MethodInvokingFactoryBean();
        final Class<?> beanType = entry.getValue().getClass();
        mBean.setTargetObject(entry.getValue());
        return mBean;
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
                return new EndpointDefault(getAlias(), getName());
            }

            @Override
            public List<ServiceSpi> getServices() {
                return services;
            }

            @Override
            public List<ServiceSpi> getServiceByType(ServiceType type) {
                return null;
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

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        this.beanFactory = (ListableBeanFactory) beanFactory;
    }
}
