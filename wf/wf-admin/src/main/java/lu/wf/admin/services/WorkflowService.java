package lu.wf.admin.services;

import lu.cortex.spi.ResultEndpoint;
import lu.cortex.spi.ResultStatus;
import lu.wf.admin.controller.WorkflowController;
import lu.wf.admin.model.Activity;
import lu.wf.admin.model.SimpleWorkflow;
import lu.wf.admin.model.Workflow;
import lu.wf.admin.model.WorkflowActivity;
import lu.wf.admin.repository.ActivityRepository;
import lu.wf.admin.repository.WorkflowRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Component
public class WorkflowService implements WorkflowController {

    @Autowired
    WorkflowRepository workflowRepository;
    @Autowired
    ActivityRepository activityRepository;

    @Override
    public ResultEndpoint create(@RequestBody SimpleWorkflow simpleWorkflow) {
        if(simpleWorkflow.getActivities().isEmpty()){
            return new ResultEndpoint(ResultStatus.FAILURE, "Cannot create an empty workflow :("+ simpleWorkflow.getName()+")");
        }
        if(!workflowRepository.findByName(simpleWorkflow.getName()).isEmpty()){
            return new ResultEndpoint(ResultStatus.FAILURE, "Cannot create two workflows with the same name :("+ simpleWorkflow.getName()+")");
        }
        Workflow workflow = new Workflow();
        workflow.setName(simpleWorkflow.getName());
        for(String activityName : simpleWorkflow.getActivities()) {
            Activity activity = activityRepository.findLastVersionByName(activityName);
            if(activity == null){
                return new ResultEndpoint(ResultStatus.FAILURE, "Cannot create a workflow with a non existing activity :("+ simpleWorkflow.getName()+","+activityName+")");
            }
            workflow.addActivity(activity);
        }
        workflowRepository.save(workflow);
        return new ResultEndpoint(ResultStatus.SUCCESS, "Workflow created !");
    }


    @Override
    public ResultEndpoint delete(@PathVariable("id") Long id) {
        if(id == null || id == 0){
            workflowRepository.deleteAll();
        }
        Workflow workflow = workflowRepository.findOne(id);
        if(workflow == null){
            return new ResultEndpoint(ResultStatus.FAILURE, "There is no existing workflow with id :("+id+")");
        }
        workflowRepository.delete(workflow);
        return new ResultEndpoint(ResultStatus.SUCCESS, "Workflow deleted !");
    }

    @Override
    public ResultEndpoint deploy(@PathVariable("id") Long id) {

        Workflow workflow = workflowRepository.findOne(id);
        if(workflow == null){
            return new ResultEndpoint(ResultStatus.FAILURE, "There is no existing workflow with id :("+id+")");
        }
        return new ResultEndpoint(ResultStatus.SUCCESS, "Workflow deployed !");
    }
}
