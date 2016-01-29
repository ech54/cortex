package lu.cortex.registry.container.cache;

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
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lu.cortex.DomainCommonConfiguration;
import lu.cortex.endpoints.Endpoint;
import lu.cortex.endpoints.EndpointDefault;
import lu.cortex.endpoints.EndpointPath;
import lu.cortex.evt.model.Event;
import lu.cortex.evt.model.EventBuilder;
import lu.cortex.evt.model.EventType;
import lu.cortex.registry.container.ContainerConfiguration;
import lu.cortex.registry.container.RegistryDomain;

@ContextConfiguration(classes = {CommunicationRedisTestCase.TestingConf.class})
@RunWith(SpringJUnit4ClassRunner.class)
public class CommunicationRedisTestCase {

        private Logger logger = LoggerFactory.getLogger(CommunicationRedisTestCase.class);

        @Autowired
        StringRedisTemplate template;

        @Configuration
        @Import(value = {DomainCommonConfiguration.class, ContainerConfiguration.class})
        @ComponentScan(basePackageClasses = { RegistryDomain.class})
        public static class TestingConf {

            @Bean
            public String simpleString() { return "simple string injected"; }
            @Bean(name="registry.install.endpoint")
            public Endpoint registryInstallEndpoint() {
                return new EndpointDefault("registry-domain", EndpointPath.buildPath("registry-domain", "domain-definition", "install"));
            }

        }

        @Test
        public void callAsyncProcess() throws JsonProcessingException, InterruptedException {
            ObjectMapper mapper = new ObjectMapper();
            final Event event = EventBuilder
                    .from(new EndpointDefault("test", "test:junit"))
                    .to("policy", "register")
                    .withType(EventType.INFO)
                    .withPayload("'hello':'world'")
                    .build();
            //Object to JSON in String
            String jsonInString = mapper.writeValueAsString(event);
            System.out.println(jsonInString);
            template.convertAndSend("policy", jsonInString);

            Thread.sleep(50 * 1000);
        }

}
