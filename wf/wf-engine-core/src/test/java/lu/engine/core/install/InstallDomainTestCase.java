package lu.engine.core.install;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import lu.cortex.DomainCommonConfiguration;
import lu.engine.core.CoreConfiguration;
//import lu.cortex.registry.container.Application;
//import lu.cortex.registry.container.service.ServiceRegistry;

@ContextConfiguration(classes = {InstallDomainTestCase.TestingConf.class})
@RunWith(SpringJUnit4ClassRunner.class)
public class InstallDomainTestCase {
    final static private Logger LOGGER = LoggerFactory.getLogger(InstallDomainTestCase.class);

    @Import({DomainCommonConfiguration.class, CoreConfiguration.class})
    @Configuration
    @ComponentScan({"lu"})
    static class TestingConf {
        /*
        @Autowired
        ApplicationEventPublisher applicationEventPublisher;

        @Bean
        Endpoint application() {
            return new EndpointDefault("wfEngine", "wf:engine");
        }

        @Bean
        EventsSender eventSender() {
            return new MockEventsSender(applicationEventPublisher);
        }

        @Bean
        EventsListener eventListener() {
            return new MockEventsListener();
        }*/
    }

    @Test
    public void install() throws Exception {
        Thread.sleep(5 * 1000);
        //serviceRegistry.getAllDomains().stream().forEach(d -> LOGGER.info("in registry: " + d.getName()));
        //LOGGER.info("is registry-domain installed ? -> " + serviceRegistry.isDomainExisting("registry-domain"));
        //LOGGER.info("is simple-domain installed ? -> " + serviceRegistry.isDomainExisting("simple"));
        Thread.sleep(60 * 100000);
    }

}
