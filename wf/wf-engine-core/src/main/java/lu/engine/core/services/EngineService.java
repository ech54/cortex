package lu.engine.core.services;

import java.util.HashSet;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.fasterxml.jackson.databind.ObjectMapper;

import lu.cortex.async.DomainSender;
import lu.cortex.endpoints.Endpoint;
import lu.cortex.endpoints.EndpointPath;
import lu.cortex.evt.model.Event;
import lu.cortex.evt.model.EventBuilder;
import lu.cortex.evt.model.EventType;
import lu.cortex.evts.core.EventsSender;
import lu.engine.core.model.ProcessStep;
import lu.engine.core.model.ProcessStepDefinition;
import lu.engine.core.model.StepStatus;
import lu.engine.core.repository.ProcessStepDefinitionRepository;
import lu.engine.core.repository.ProcessStepRepository;
import lu.engine.core.utils.ProcessStepUtils;
import lu.wf.model.WfEngineException;

@Component
public class EngineService {
    @Autowired
    Endpoint application; // = new EndpointDefault("wfEngine", "wf:engine"); TODO to inject.
    @Autowired
    ProcessStepRepository processStepRepository;
    @Autowired
    ProcessStepDefinitionRepository processStepDefinitionRepository;
    @Autowired
    DomainSender eventsSender;
    @Autowired
    ObjectMapper objectMapper;

    public EngineService() {
        //Empty.
    }

    public void createProcess(final Event event) {
        final String domain = EndpointPath.getDomain(event.getOrigin().getPath());
        final String process = EndpointPath.getProcess(event.getOrigin().getPath());
        final List<ProcessStepDefinition> definitions = processStepDefinitionRepository.findByProcess(domain, process);
        if (definitions == null || definitions.isEmpty()) {
            eventsSender.send(EventBuilder
                    .from(application).withType(EventType.INFO)
                    .to(event.getDestination().getSystemAlias(), event.getDestination().getPath())
                    .withPayload(String.format("{'msg': 'process definition not existing.'}"))
                    .build());
        }
        final ProcessStep step = ProcessStepUtils.createInstanceBasedOn(definitions);
        processStepRepository.save(step);
        final String ref = step.getDomain() + EndpointPath.SEPARATOR +
                step.getProcessName() + EndpointPath.SEPARATOR +
                step.getProcessId();
        eventsSender.send(EventBuilder
                .from(application).withType(EventType.UPDATE)
                .to(event.getDestination().getSystemAlias(), event.getDestination().getPath())
                .withPayload(String.format("{'ref': %s}", ref))
                .build());
    }

    public void getProcessStep(final Event event) {
        final String domain = EndpointPath.getDomain(event.getOrigin().getPath());
        final String process = EndpointPath.getProcess(event.getOrigin().getPath());
        final String ref = EndpointPath.getRef(event.getOrigin().getPath());
        final ProcessStep current = processStepRepository.findUniqueStep(domain, process, ref);
        current.setActivities(new HashSet<>());
        current.getActivities().add(processStepRepository.nextStep(current.getId()));
    }

    public void updateProcessStep(final Event event) {
        final String domain = EndpointPath.getDomain(event.getOrigin().getPath());
        final String process = EndpointPath.getProcess(event.getOrigin().getPath());
        final String ref = EndpointPath.getRef(event.getOrigin().getPath());
        final StepStatus status = extractStatus(event);
        final ProcessStep current = processStepRepository.findUniqueStep(domain, process, ref);
        updateWithStatus(current, status);
        eventsSender.send(EventBuilder
                .from(application).withType(EventType.UPDATE)
                .to(event.getDestination().getSystemAlias(), event.getDestination().getPath())
                .withPayload(toJson(current))
                .build());
    }

    protected void updateWithStatus(final ProcessStep step, final StepStatus status) {
        step.setStatus(status.name());
        processStepRepository.save(step);
    }

    protected StepStatus extractStatus(final Event event) {
        try {
            return StepStatus.valueOf(objectMapper.reader().readValue(event.getBody().getPaylodAsString()));
        } catch (final Exception exception) {
            throw new WfEngineException("Can't parse event", exception);
        }
    }

    protected String toJson(final ProcessStep step) {
        try {
            return objectMapper.writer().writeValueAsString(step);
        } catch (final Exception exception) {
            throw new WfEngineException("can't parse step=" + step, exception);
        }
    }
}
