package lu.wf.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class WorkflowDefinition implements Serializable {

    private String path;
    private String version;
    private List<StepDefinition> steps = new ArrayList<>();

    public WorkflowDefinition() {

    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public List<StepDefinition> getSteps() {
        return steps;
    }

    public void setSteps(List<StepDefinition> steps) {
        this.steps = steps;
    }
}
