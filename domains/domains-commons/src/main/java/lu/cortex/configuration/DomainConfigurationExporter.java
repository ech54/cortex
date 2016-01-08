/**
 * Project: ESENTOOL
 * Contractor: ARHS-Developments
 */
package lu.cortex.configuration;

import java.io.ObjectInputStream;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.ListableBeanFactory;
import org.springframework.core.annotation.AnnotationUtils;

import lu.cortex.annotation.AsyncProcessName;
import lu.cortex.annotation.DomainName;

/**
 * <class_description>
 */
public class DomainConfigurationExporter implements InitializingBean, BeanFactoryAware {

    private Map<String, Object> domains = new ConcurrentHashMap<>();

    private Map<String, Object> domainProcesses = new ConcurrentHashMap<>();


    private ListableBeanFactory beanFactory;

    @Override
    public void afterPropertiesSet() throws Exception {
        domainProcesses.putAll(beanFactory.getBeansWithAnnotation(AsyncProcessName.class));
        domains.putAll(beanFactory.getBeansWithAnnotation(DomainName.class));

    }

    /*
    public Object getProcess() {

    }*/

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        this.beanFactory = (ListableBeanFactory) beanFactory;
    }
}
