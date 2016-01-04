/**
 * Project: ESENTOOL
 * Contractor: ARHS-Developments
 */
package lu.engine.core.utils;

import java.util.Calendar;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;
import org.apache.commons.lang.StringUtils;

import lu.engine.core.model.ProcessStep;
import lu.engine.core.model.ProcessStepDefinition;
import lu.engine.core.model.StepStatus;

public class ProcessStepUtils {

    public static final String START_PROCESS = "start";
    public static final String END_PROCESS = "end";

    public static final boolean isProcessEnded(final ProcessStep step) {
        return StringUtils.equalsIgnoreCase(step.getProcessName(), END_PROCESS);
    }

    public static ProcessStep createInstanceBasedOn(final List<ProcessStepDefinition> definitions) {
        final String domain = definitions.get(0).getDomain();
        final String process = definitions.get(0).getProcessName();
        final String uniqueRef = generatRef(domain, process);
        final ProcessStep newInstance = buildStep(domain, process, uniqueRef, START_PROCESS);
        final List<ProcessStep> steps = definitions.stream().map(s -> {return buildStep(domain, process, uniqueRef, s.getStepName());}).collect(Collectors.toList());
        newInstance.getActivities().add(chainSteps(buildStep(domain, process, uniqueRef, END_PROCESS), steps));
        return newInstance;
    }

    protected static String generatRef(final String domain, final String process) {
        return domain + "-" + process + "-" + Long.toHexString(Calendar.getInstance().getTimeInMillis());
    }

    protected static ProcessStep chainSteps(final ProcessStep end, final List<ProcessStep> steps) {
        ProcessStep current = end;
        Collections.reverse(steps);
        for (ProcessStep s : steps) {
            s.getActivities().add(current);
            current = s;
        };
        return current;
    }

    protected static ProcessStep buildStep(final String domain, final String process, final String uniqueRef, final String name) {
        ProcessStep pStep = new ProcessStep();
        pStep.setDomain(domain);
        pStep.setProcessName(process);
        pStep.setProcessId(uniqueRef);
        pStep.setStatus(StepStatus.CREATED.name());
        pStep.setStepName(name);
        pStep.setActivities(new HashSet<>());
        return pStep;
    };

}
