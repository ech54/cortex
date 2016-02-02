package lu.engine.core.engine;

import org.apache.commons.lang.StringUtils;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import lu.cortex.endpoints.*;
import lu.cortex.evt.model.Event;
import lu.cortex.evt.model.EventBuilder;
import lu.cortex.evt.model.EventType;
import lu.cortex.evts.core.EventsListener;
import lu.cortex.evts.core.EventsSender;
import lu.cortex.evts.core.mocks.MockEventsListener;
import lu.cortex.evts.core.mocks.MockEventsSender;
import lu.engine.core.services.EngineService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = CreateWorkflowInstanceTest.TestingConfiguration.class)
public class CreateWorkflowInstanceTest {

    @Autowired
    EngineService engine;

    @Autowired
    EventsListener eventListener;

    @Configuration
    @ComponentScan({"lu.engine.core"})
    static public class TestingConfiguration {

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
        }
    }

    @Test
    public void handleEvent() {
        final Event create = EventBuilder
                .from(new EndpointDefault("wf-engine", "policy:subscription"))
                .to("test", "policy:subscription")
                .withType(EventType.CREATE)
                .build();
        //--
        engine.createProcess(create);
        //--
        assertFalse(getListener().isEmpty());
        final Event event = getListener().last();
        System.out.println("Received event: " + event);
        assertTrue(StringUtils.contains(event.getBody().getPaylodAsString(), "'ref':"));
    }


    protected MockEventsListener getListener() {
        return (MockEventsListener) eventListener;
    }
}
