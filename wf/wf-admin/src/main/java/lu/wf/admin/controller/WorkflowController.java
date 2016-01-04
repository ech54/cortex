package lu.wf.admin.controller;

import lu.cortex.spi.ResultEndpoint;
import lu.wf.admin.model.SimpleWorkflow;
import lu.wf.admin.model.Workflow;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
public interface WorkflowController {

    /**
     * Create a new workflow.
     * @param workflow the workflow to create
     */
    @RequestMapping(value="/workflow/create",
            method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    ResultEndpoint create(@RequestBody final SimpleWorkflow workflow);

    /**
     * Delete a workflow.
     * @param id the id of the workflow to delete.
     */
    @RequestMapping(value="/workflow/delete/{id}",
            method = RequestMethod.DELETE)
    ResultEndpoint delete(@PathVariable("id") final Long id);



    /**
     * Deploy a workflow.
     * @param id the id of the workflow to delete.
     */
    @RequestMapping(name="/workflow/deploy/{id}",
            method = RequestMethod.GET)
    ResultEndpoint deploy(@PathVariable("id") final Long id);

}
