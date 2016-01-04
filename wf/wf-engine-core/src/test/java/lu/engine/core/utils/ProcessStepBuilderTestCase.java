package lu.engine.core.utils;

import org.junit.Test;
import lu.engine.core.model.ProcessStep;
import static org.junit.Assert.*;

public class ProcessStepBuilderTestCase {

    @Test
    public void createStep() {
        final ProcessStepBuilder builder = new ProcessStepBuilder();
        final ProcessStep start = builder
                .addProcessPath("policy:subscription")
                .addActivityName("start")
                .build();
        assertEquals("policy", start.getDomain());
        assertEquals("subscription", start.getProcessName());
        assertEquals("start", start.getStepName());
    }

}
