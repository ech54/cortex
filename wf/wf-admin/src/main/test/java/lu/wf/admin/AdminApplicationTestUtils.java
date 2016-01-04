package lu.wf.admin;

import lu.wf.admin.model.Activity;
import lu.wf.admin.model.SimpleWorkflow;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

import java.util.Arrays;

public class AdminApplicationTestUtils {

    public static HttpEntity buildRequest(Object content) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        return new HttpEntity<>(content, httpHeaders);
    }


    public static Activity buildActivity(String processName) {
        return buildActivity(processName, 1);
    }
    public static Activity buildActivity(String processName, int version) {
        Activity activity = new Activity();
        activity.setVersion(version);
        activity.setProcessName(processName);
        return activity;
    }

    public static SimpleWorkflow buildWorkflow(String name, String... activities) {
        SimpleWorkflow workflow = new SimpleWorkflow();
        workflow.setName(name);
        workflow.setActivities(Arrays.asList(activities));
        return workflow;
    }
}
