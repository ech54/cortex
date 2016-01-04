package lu.wf.admin.model;

import org.neo4j.ogm.annotation.*;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.annotation.QueryResult;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@NodeEntity
public class Workflow {

    @GraphId
    private Long id;
    @Property
    private String name;
    @Relationship(type="IS_COMPOSED_OF")
    private Set<WorkflowActivity> workflowActivities = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void addActivity(Activity activity) {
        WorkflowActivity workflowActivity = new WorkflowActivity();
        workflowActivity.setActivity(activity);
        workflowActivity.setWorkflow(this);
        workflowActivities.add(workflowActivity);
        workflowActivity.setOrder(workflowActivities.size());
    }
}
