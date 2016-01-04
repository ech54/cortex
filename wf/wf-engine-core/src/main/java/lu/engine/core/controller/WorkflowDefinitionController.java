package lu.engine.core.controller;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import lu.cortex.spi.ResultEndpoint;
import lu.wf.model.WorkflowDefinition;

@RestController
public interface WorkflowDefinitionController {

    /**
     * Create a new workflow definition.
     * @param definition The workflow definition.
     */
    @RequestMapping(name="/create",
            method = RequestMethod.POST,
            consumes={MediaType.APPLICATION_JSON_VALUE})
    ResultEndpoint create(@RequestBody final WorkflowDefinition definition);

    /**
     * Delete a workflow definition based on the path and version.
     * @param path The path.
     * @param  version The version.
     */
    @RequestMapping(name="/delete",
            method = RequestMethod.DELETE,
            consumes={MediaType.APPLICATION_JSON_VALUE})
    ResultEndpoint delete(final String path, final String version);
}
