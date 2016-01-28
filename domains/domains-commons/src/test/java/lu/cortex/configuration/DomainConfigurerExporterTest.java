
package lu.cortex.configuration;

import lu.cortex.endpoints.EndpointDefault;
import lu.cortex.evt.model.EventBuilder;
import lu.cortex.evt.model.EventType;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * <class_description>
 */
@ContextConfiguration(classes = {DomainConfigurerExporterTest.TestingConf.class})
@RunWith(SpringJUnit4ClassRunner.class)
public class DomainConfigurerExporterTest {

    private Logger logger = LoggerFactory.getLogger(DomainConfigurerExporterTest.class);

    @Autowired
    DomainDefinitionManagerDefault exporter;

    @Configuration
    @ComponentScan(basePackageClasses = {
            //DomainConfigurerExporterTest.class,
            DomainDefinitionManagerDefault.class})
    public static class TestingConf {

        @Bean
        public String simpleString() { return "simple string injected"; }

    }

    @Test
    public void callAsyncProcess() {
        logger.info(">>>" + exporter.getDomain() );
        logger.info(">>>" + exporter.getAsyncProcesses() );
        exporter.getAsyncProcesses().entrySet().forEach(e -> {
            logger.info("k: {}, v: {}",e.getKey(), e.getValue());
            e.getValue().setArguments(new Object[]{
                    EventBuilder
                        .from(new EndpointDefault("test", "test:junit"))
                        .to("mock", "mock:listener")
                        .withType(EventType.INFO)
                        .withPayload("'hello':'world'")
                        .build()});
            try {
                e.getValue().prepare();
                e.getValue().invoke();
            }catch(final Exception ex) {
                throw new RuntimeException(ex);
            }
        });
    }

    @Test
    public void callProcess() {
        logger.info(">>>" + exporter.getDomain() );
        logger.info(">>>" + exporter.getProcesses() );
        exporter.getProcesses().entrySet().forEach(e -> {
            logger.info("k: {}, v: {}",e.getKey(), e.getValue());
            e.getValue().setArguments(new Object[]{"SimpleCriteria", "other"});
            try {
                e.getValue().prepare();
                final Object result = e.getValue().invoke();
                logger.info("result>>> " + result);
            }catch(final Exception ex) {
                throw new RuntimeException(ex);
            }
        });
    }

    @Test
    public void simple() {
        logger.info(">>>" + exporter.getDomainDefinition());
        exporter.getDomainDefinition().getServices().stream().forEach(System.out::println);
    }
}
