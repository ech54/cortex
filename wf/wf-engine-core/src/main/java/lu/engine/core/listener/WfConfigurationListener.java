/**
 * Project: ESENTOOL
 * Contractor: ARHS-Developments
 */
package lu.engine.core.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import lu.cortex.annotation.AsyncProcessName;
import lu.cortex.annotation.OnMessage;
import lu.cortex.evt.model.Event;
import lu.engine.core.services.EngineService;

@Component
@AsyncProcessName(name = "wf-configuration")
public class WfConfigurationListener {
    private static Logger logger = LoggerFactory.getLogger(WfConfigurationListener.class);

    @Autowired
    private EngineService engineService;

    @OnMessage
    public void create(final Event event) {
        logger.info("received new event= " + event);
        engineService.createProcess(event);
    }

}
