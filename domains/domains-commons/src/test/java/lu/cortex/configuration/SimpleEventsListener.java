package lu.cortex.configuration;

import lu.cortex.annotation.AsyncProcessName;
import lu.cortex.annotation.OnMessage;
import lu.cortex.evt.model.Event;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * <class_description>
 */
@Component
@AsyncProcessName(name = "register")
public class SimpleEventsListener{

    private static Logger logger = LoggerFactory.getLogger(SimpleEventsListener.class);

    @Autowired
    private String simpleString;

    @OnMessage
    public void receiveEvent(Event event) {
        logger.info("verified that listener is properly configured by spring: /r"
                + ">>>injected value: " + simpleString + "/r");
        logger.info("received new event= " + event);
    }

    public static void received(Event event) {logger.info("received new event= " + event);}
}
