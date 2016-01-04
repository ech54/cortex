/**
 * Project: ESENTOOL
 * Contractor: ARHS-Developments
 */
package lu.cortex.evts.core.mocks;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

import lu.cortex.evt.model.Event;
import lu.cortex.evts.core.EventsSender;

@Component
public class MockEventsSender implements EventsSender {

    private ApplicationEventPublisher publisher;

    @Autowired
    public MockEventsSender(final ApplicationEventPublisher publisher) {
        this.publisher = publisher;
    }

    @Override
    public void sendEvent(final Event event) {
        this.publisher.publishEvent(event);
    }
}
