package lu.cortex.registry.container.install;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import lu.cortex.configuration.DomainCommonConfiguration;
import lu.cortex.endpoints.Endpoint;
import lu.cortex.endpoints.EndpointDefault;
import lu.cortex.endpoints.EndpointPath;
import lu.cortex.registry.container.Application;
import lu.cortex.registry.container.service.ServiceRegistry;

@ContextConfiguration(classes = {InstallDomainTestCase.TestingConf.class})
@RunWith(SpringJUnit4ClassRunner.class)
public class InstallDomainTestCase {

    @Autowired
    ServiceRegistry serviceRegistry;

    @Import(DomainCommonConfiguration.class)
    @Configuration
    @ComponentScan(basePackageClasses = { Application.class})
    static class TestingConf {

        @Bean(name="registry.install.endpoint")
        public Endpoint registryInstallEndpoint() {
            return new EndpointDefault("registry-domain", EndpointPath.buildPath("registry-domain","domain-definition", "install"));
        }
    }
    @Test
    public void install() throws Exception {
        System.out.println(">> " + serviceRegistry.isDomainExisting("simple-domain"));

        Thread.sleep(60 * 100000);
    }

}
