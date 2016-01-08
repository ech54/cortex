package lu.cortex.evts.core.mocks;

import java.util.Stack;

import lu.cortex.evt.model.Event;
import lu.cortex.evts.core.EventsListener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class MockEventsListener implements EventsListener, InitializingBean {

    private static final Logger LOGGER = LoggerFactory.getLogger(MockEventsListener.class);

    private Stack<Event> local = new Stack<>();

    //private

    @Override
    public void afterPropertiesSet() throws Exception {

    }

    @EventListener
    public void receiveEvent(final Event event) {
        synchronized(local) {
            LOGGER.info("Mock: received event {}", event);
            local.push(event);
        }
    }

    public  boolean isEmpty() {
        synchronized(local) {
            return local.isEmpty();
        }
    }

    public synchronized<local> Event last() {
        synchronized(local) {
            return local.pop();
        }
    }
}
