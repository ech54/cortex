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

import lu.cortex.async.DomainListener;
import lu.cortex.configuration.DomainCommonConfiguration;
import lu.cortex.configuration.DomainDefinitionManagerDefault;
import lu.cortex.endpoints.EndpointDefault;
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
                DomainDefinitionManagerDefault.class, DomainListener.class})
        public static class TestingConf {

            @Bean
            public String simpleString() { return "simple string injected"; }

        }

        @Test
        public void callAsyncProcess() throws JsonProcessingException, InterruptedException {
            ObjectMapper mapper = new ObjectMapper();
            final Event event = EventBuilder
                    .from(new EndpointDefault("test", "test:junit"))
                    .to("policy-services", "register")
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
