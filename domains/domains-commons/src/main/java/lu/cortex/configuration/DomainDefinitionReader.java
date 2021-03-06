package lu.cortex.configuration;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.springframework.beans.factory.config.MethodInvokingFactoryBean;
import org.springframework.core.annotation.AnnotationUtils;

import lu.cortex.annotation.AsyncProcessName;
import lu.cortex.annotation.OnMessage;
import lu.cortex.annotation.ProcessName;
import lu.cortex.annotation.Reference;
import lu.cortex.endpoints.EndpointPath;
import lu.cortex.spi.ServiceSpi;
import lu.cortex.spi.ServiceSpiDefault;

public class DomainDefinitionReader {

    public DomainDefinitionReader(){
        //Empty.
    }

    public Map<String, MethodInvokingFactoryBean> extractProcess(final Map<String, Object> beans, final String domain) {
        final Map<String, MethodInvokingFactoryBean> results = new ConcurrentHashMap<>();
        beans.entrySet().stream()
                .forEach(e -> {
                    results.putAll(createInvokingBeans(e, domain, ProcessName.class, Reference.class));
                });
        return results;
    }

    public Map<String, MethodInvokingFactoryBean> extractAsyncProcess(final Map<String, Object> beans, final String domain) {
        final Map<String, MethodInvokingFactoryBean> results = new ConcurrentHashMap<>();
        beans.entrySet().stream()
                .forEach(e -> {
                    results.putAll(createInvokingBeans(e, domain, AsyncProcessName.class, OnMessage.class));
                });
        return results;
    }

    public List<ServiceSpiDefault> extractServiceSpi(final Map<String, MethodInvokingFactoryBean> syncs, final Map<String, MethodInvokingFactoryBean> asyncs) {
        final List<ServiceSpiDefault> result = new ArrayList<>();
        result.addAll(extractServiceSpi(ProcessName.class ,syncs));
        result.addAll(extractServiceSpi(AsyncProcessName.class ,asyncs));
        return result;
    }

    private List<ServiceSpiDefault> extractServiceSpi(Class<? extends Annotation> annotation, final Map<String, MethodInvokingFactoryBean> allConfiguration) {
        final List<ServiceSpiDefault> results = new ArrayList<>();
        results.addAll(allConfiguration.values().stream()
                .map(mBean -> {
                    try {
                        final Annotation processAnnotation = mBean.getTargetObject().getClass().getAnnotation(annotation);
                        final ServiceSpiDefault serviceSpi = new ServiceSpiDefault(getName(processAnnotation));
                        serviceSpi.addReference(mBean.getTargetMethod());
                        return serviceSpi;
                    } catch (final Exception e) {
                        throw new RuntimeException(e);
                    }
                }).collect(Collectors.toList()));
        return results;
    }

    private Map<String, MethodInvokingFactoryBean> createInvokingBeans(
            Map.Entry entry, String domain,
            Class<? extends Annotation> pT,
            Class<? extends Annotation> rT) {
        try {
            final Map<String, MethodInvokingFactoryBean> result = new HashMap<>();
            final Annotation processAnnotation = entry.getValue().getClass().getAnnotation(pT);
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
                    });
            return result;
        } catch (final Exception e) {
            throw new RuntimeException(e);
        }
    }

    static private MethodInvokingFactoryBean createFactory(Map.Entry entry) {
        final MethodInvokingFactoryBean mBean = new MethodInvokingFactoryBean();
        final Class<?> beanType = entry.getValue().getClass();
        mBean.setTargetObject(entry.getValue());
        return mBean;
    }

    static private String getName(final Annotation annotation) {
        return (String) AnnotationUtils.getAnnotationAttributes(annotation).get("name");
    }
}
