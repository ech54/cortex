package lu.cortex.registry.container.install;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import lu.cortex.DomainCommonConfiguration;
import lu.cortex.endpoints.Endpoint;
import lu.cortex.endpoints.EndpointDefault;
import lu.cortex.endpoints.EndpointPath;
import lu.cortex.registry.container.Application;
import lu.cortex.registry.container.service.ServiceRegistry;

@ContextConfiguration(classes = {InstallDomainTestCase.TestingConf.class})
@RunWith(SpringJUnit4ClassRunner.class)
public class InstallDomainTestCase {
    final static private Logger LOGGER = LoggerFactory.getLogger(InstallDomainTestCase.class);

    @Autowired
    ServiceRegistry serviceRegistry;

    @Autowired
    StringRedisTemplate stringRedisTemplate;


    @Import(DomainCommonConfiguration.class)
    @Configuration
    @ComponentScan(basePackageClasses = { DomainCommonConfiguration.class, Application.class})
    static class TestingConf {

        @Bean(name="registry.install.endpoint")
        public Endpoint registryInstallEndpoint() {
            return new EndpointDefault("registry-domain", EndpointPath.buildPath("domain-definition", "install"));
        }
    }

    @Test
    public void install() throws Exception {
        Thread.sleep(5 * 1000);
        serviceRegistry.getAllDomains().stream().forEach(d -> LOGGER.info("in registry: " + d.getName()));
        LOGGER.info("is registry-domain installed ? -> " + serviceRegistry.isDomainExisting("registry-domain"));
        LOGGER.info("is simple-domain installed ? -> " + serviceRegistry.isDomainExisting("simple"));
        Thread.sleep(60 * 100000);
    }

}
