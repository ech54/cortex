package lu.engine.core.utils;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

import lu.cortex.endpoints.EndpointPath;
import lu.engine.core.model.ProcessStepDefinition;
import lu.wf.model.WorkflowDefinition;

public class ProcessStepDefinitionUtils {

    public static ProcessStepDefinition buildProcessDefinition(final WorkflowDefinition definition) {
        final String domain = EndpointPath.getDomain(definition.getPath());
        final String process = EndpointPath.getProcess(definition.getPath());
        final List<ProcessStepDefinition> steps = definition.getSteps().stream()
                .map(s -> {return buildDefinition(domain, process, definition.getVersion(), s.getName());})
                .collect(Collectors.toList());
        return chainStepsDefinition(steps);
    }

    protected static ProcessStepDefinition chainStepsDefinition(final List<ProcessStepDefinition> steps) {
        ProcessStepDefinition current = null;
        Collections.reverse(steps);
        for (ProcessStepDefinition s : steps) {
            if(current != null) {
                s.getSteps().add(current);
            }
            current = s;
        };
        return current;
    }

    protected static ProcessStepDefinition buildDefinition(final String domain, final String process, final String version, final String name) {
        ProcessStepDefinition pStep = new ProcessStepDefinition();
        pStep.setDomain(domain);
        pStep.setProcessName(process);
        pStep.setVersion(version);
        pStep.setStepName(name);
        pStep.setSteps(new HashSet<>());
        return pStep;
    };


}
