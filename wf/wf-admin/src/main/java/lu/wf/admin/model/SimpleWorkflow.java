package lu.wf.admin.model;


import java.util.ArrayList;
import java.util.List;

public class SimpleWorkflow {

    private String name;

    private List<String> activities = new ArrayList<>();

    public SimpleWorkflow(){
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getActivities() {
        return activities;
    }

    public void setActivities(List<String> activities) {
        this.activities = activities;
    }
}
