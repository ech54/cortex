package lu.engine.core.services;

import java.util.List;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;

import lu.cortex.endpoints.EndpointPath;
import lu.cortex.spi.ResultEndpoint;
import lu.cortex.spi.ResultStatus;
import lu.engine.core.controller.WorkflowDefinitionController;
import lu.engine.core.model.ProcessStepDefinition;
import lu.engine.core.repository.ProcessStepDefinitionRepository;
import lu.engine.core.utils.ProcessStepDefinitionUtils;
import lu.wf.model.WorkflowDefinition;

@Component
public class WorkflowDefinitionService implements WorkflowDefinitionController {

    private static final Logger LOGGER = LoggerFactory.getLogger(WorkflowDefinitionService.class);

    @Autowired
    ProcessStepDefinitionRepository processStepDefinitionRepository;

    public WorkflowDefinitionService() {
        // Empty.
    }

    @Override
    public ResultEndpoint create(final @RequestBody WorkflowDefinition workflow) {
        final String domain = EndpointPath.getDomain(workflow.getPath());
        final String process = EndpointPath.getProcess(workflow.getPath());
        final List<ProcessStepDefinition> results = processStepDefinitionRepository.findLastVersion(domain, process, workflow.getVersion());
        if (!results.isEmpty()) {
            return new ResultEndpoint(ResultStatus.FAILURE, "Workflow is existing.");
        }
        final ProcessStepDefinition allProcess = ProcessStepDefinitionUtils.buildProcessDefinition(workflow);
        processStepDefinitionRepository.save(allProcess);
        return new ResultEndpoint(ResultStatus.SUCCESS, StringUtils.EMPTY);
    }

    @Override
    public ResultEndpoint delete(final String path, final String version) {
        final String domain = EndpointPath.getDomain(path);
        final String process = EndpointPath.getProcess(path);
        final List<ProcessStepDefinition> results = processStepDefinitionRepository.findLastVersion(domain, process, version);
        if (results.isEmpty()) {
            return new ResultEndpoint(ResultStatus.SUCCESS, StringUtils.EMPTY);
        }
        results.stream().forEach(s -> processStepDefinitionRepository.delete(s));
        return new ResultEndpoint(ResultStatus.SUCCESS, StringUtils.EMPTY);
    }
}
