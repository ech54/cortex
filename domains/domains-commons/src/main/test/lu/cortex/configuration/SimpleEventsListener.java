/**
 * Project: ESENTOOL
 * Contractor: ARHS-Developments
 */
package lu.cortex.configuration;

import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import lu.cortex.annotation.AsyncProcessName;
import lu.cortex.evt.model.Event;
import lu.cortex.evts.core.EventsListener;

/**
 * <class_description>
 */
@Component
public class SimpleEventsListener implements EventsListener {

    @Override
    @AsyncProcessName(name = "POLICY_DOCUMENT_VALIDATION")
    public void receiveEvent(Event event) {


    }
}
