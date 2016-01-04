package lu.wf.admin.services;

import lu.cortex.spi.ResultEndpoint;
import lu.cortex.spi.ResultStatus;
import lu.wf.admin.controller.ActivityController;
import lu.wf.admin.model.Activity;
import lu.wf.admin.repository.ActivityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

@Component
public class ActivityService implements ActivityController {

    @Autowired
    ActivityRepository activityRepository;

    @Override
    public ResultEndpoint create(@RequestBody Activity activity) {
        if(activityRepository.findActivityByNameAndVersion(activity.getProcessName(), activity.getVersion()) != null){
            return new ResultEndpoint(ResultStatus.FAILURE, "Cannot create two activities with the same process name and version :("+ activity.getProcessName()+","+ activity.getVersion()+")");
        }
        activityRepository.save(activity);
        return new ResultEndpoint(ResultStatus.SUCCESS, "Activity created !");
    }

    @Override
    public void delete(@PathVariable("id") Long id) {
        if(id == null || id == 0){
            activityRepository.deleteAll();
        }
        Activity activity = activityRepository.findOne(id);
        if(activity != null){
            activityRepository.delete(activity);
        }
    }

}
