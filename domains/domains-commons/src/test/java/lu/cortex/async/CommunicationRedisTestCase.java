package lu.cortex.async;

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

import lu.cortex.annotation.DomainConfiguration;
import lu.cortex.DomainCommonConfiguration;
import lu.cortex.configuration.DomainDefinitionManagerDefault;
import lu.cortex.endpoints.Endpoint;
import lu.cortex.endpoints.EndpointDefault;
import lu.cortex.endpoints.EndpointPath;
import lu.cortex.evt.model.Event;
import lu.cortex.evt.model.EventBuilder;
import lu.cortex.evt.model.EventType;

@ContextConfiguration(classes = {CommunicationRedisTestCase.TestingConf.class})
@RunWith(SpringJUnit4ClassRunner.class)
public class CommunicationRedisTestCase {

        private Logger logger = LoggerFactory.getLogger(CommunicationRedisTestCase.class);

        @Autowired
        StringRedisTemplate template;

        @Configuration
        @Import(value = {DomainCommonConfiguration.class})
        @ComponentScan(basePackageClasses = {
                DomainDefinitionManagerDefault.class, AsynchronousDomainListener.class})
        public static class TestingConf {

            @Bean
            public String simpleString() { return "simple string injected"; }
            @Bean(name="registry.install.endpoint")
            public Endpoint registryInstallEndpoint() {
                return new EndpointDefault("registry-domain", EndpointPath.buildPath("registry-domain", "domain-definition", "install"));
            }

            @DomainConfiguration(name="test", alias="test")
            public class config{}

            public class async {}
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

    @Test
    public void callAsyncProcessUpdate() throws JsonProcessingException, InterruptedException {
        ObjectMapper mapper = new ObjectMapper();
        final Event event = EventBuilder
                .from(new EndpointDefault("test", "test:junit"))
                .to("policy-services", "register:update")
                .withType(EventType.INFO)
                .withPayload("'hello':'world'")
                .build();
        //Object to JSON in String
        String jsonInString = mapper.writeValueAsString(event);
        System.out.println(jsonInString);
        template.convertAndSend("policy-2", jsonInString);

        Thread.sleep(50 * 1000);
    }

    @Test
    public void launch() throws JsonProcessingException, InterruptedException {
        ObjectMapper mapper = new ObjectMapper();
        final Event event = EventBuilder
                .from(new EndpointDefault("test", "test:junit"))
                .to("registry-domain", "registry-domain:domain-definition:install")
                .withType(EventType.INFO)
                .withPayload("'hello':'world'")
                .build();
        //Object to JSON in String
        String jsonInString = mapper.writeValueAsString(event);
        System.out.println(jsonInString);
        template.convertAndSend("registry-domain", jsonInString);

        Thread.sleep(50 * 1000);
    }
}
