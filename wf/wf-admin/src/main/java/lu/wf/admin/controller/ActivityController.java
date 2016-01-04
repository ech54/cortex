package lu.wf.admin.controller;

import lu.cortex.spi.ResultEndpoint;
import lu.wf.admin.model.Activity;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
public interface ActivityController {

    /**
     * Create a new workflow activity.
     * @param activity the workflow activity to create
     */
    @RequestMapping(value="/activity/create",
            method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    ResultEndpoint create(@RequestBody final Activity activity);

    /**
     * Delete a workflow activity.
     * @param id the id of the activity to delete.
     */
    @RequestMapping(value="/activity/delete/{id}",
            method = RequestMethod.DELETE)
    void delete(@PathVariable("id") final Long id);
}
