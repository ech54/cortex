package lu.wf.admin.model;


import org.neo4j.ogm.annotation.*;

@RelationshipEntity
public class WorkflowActivity {

    @GraphId
    private Long id;
    @Property
    private Integer order;
    @StartNode
    private Workflow workflow;
    @EndNode
    private Activity activity;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getOrder() {
        return order;
    }

    public void setOrder(Integer order) {
        this.order = order;
    }

    public Activity getActivity() {
        return activity;
    }

    public void setActivity(Activity activity) {
        this.activity = activity;
    }

    public Workflow getWorkflow() {
        return workflow;
    }

    public void setWorkflow(Workflow workflow) {
        this.workflow = workflow;
    }
}
