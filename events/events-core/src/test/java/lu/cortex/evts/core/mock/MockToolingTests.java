package lu.cortex.evts.core.mock;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import lu.cortex.endpoints.EndpointDefault;
import lu.cortex.evt.model.Event;
import lu.cortex.evt.model.EventBuilder;
import lu.cortex.evt.model.EventType;
import lu.cortex.evts.core.mocks.MockEventsListener;
import lu.cortex.evts.core.mocks.MockEventsSender;
import static org.junit.Assert.*;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = MockConfigurationTest.class)
public class MockToolingTests {

    @Autowired
    private MockEventsListener listener;

    @Autowired
    private MockEventsSender sender;

    @Test
    public void simpleSendReceived() {
        sender.sendEvent(
                EventBuilder
                        .from(new EndpointDefault("test", "test:junit"))
                        .to("mock", "mock:listener")
                        .withType(EventType.INFO)
                        .withPayload("'hello':'world'")
                        .build());
        assertFalse(listener.isEmpty());
        final Event last = listener.last();
        assertTrue(listener.isEmpty());
        assertNotNull(last);
        assertEquals("test:junit", last.getFrom().getPath());
        assertEquals("mock:listener", last.getTo().getPath());
    }

}
