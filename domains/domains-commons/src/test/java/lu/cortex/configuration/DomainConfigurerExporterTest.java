
package lu.cortex.configuration;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.apache.commons.lang3.StringUtils;
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

import lu.cortex.async.DomainSender;
import lu.cortex.endpoints.Endpoint;
import lu.cortex.endpoints.EndpointDefault;
import lu.cortex.evt.model.Event;

/**
 * <class_description>
 */
@ContextConfiguration(classes = {DomainConfigurerExporterTest.TestingConf.class})
@RunWith(SpringJUnit4ClassRunner.class)
public class DomainConfigurerExporterTest {

    private static final Logger logger = LoggerFactory.getLogger(DomainConfigurerExporterTest.class);

    @Autowired
    DomainDefinitionManagerDefault exporter;

    @Configuration
    @ComponentScan(basePackageClasses = {
            //DomainConfigurerExporterTest.class,
            DomainDefinitionManagerDefault.class})
    public static class TestingConf {

        @Bean
        public String simpleString() { return "simple string injected"; }

        @Bean(name="registry.install.endpoint")
        public Endpoint registryInstall() {
            return new EndpointDefault("test","test");
        }

        @Bean
        public DomainSender mockDomainSender() {
            return new DomainSender() {
                @Override
                public void send(Event event) {
                    logger.info("send event: " + event);
                }

                @Override
                public void broadcast(Event event) {
                }
            };
        }
    }

    @Test
    public void loadDomain() {
        assertNotNull(exporter.getDomain());
        assertEquals(exporter.getDomain().keySet()
                .stream()
                .filter(k -> StringUtils.equals(k, "policy"))
                .count(), 1);
        assertEquals(2, exporter.getAsyncProcesses("policy").size());
    }
}
