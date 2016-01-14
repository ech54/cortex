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
@AsyncProcessName(name = "wf-execution")
public class WfExecutionListener {
    private static Logger logger = LoggerFactory.getLogger(WfExecutionListener.class);

    @Autowired
    private EngineService engineService;

    @OnMessage(name="update")
    public void receiveEvent(final Event event) {
        logger.info("received new event= " + event);
        engineService.updateProcessStep(event);
    }

}
