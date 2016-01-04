package lu.engine.core.model;

import java.util.Set;
import org.neo4j.ogm.annotation.GraphId;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Property;
import org.neo4j.ogm.annotation.Relationship;

@NodeEntity
public class ProcessStep {

    @GraphId
    private Long id;
    @Property
    private String domain;
    @Property
    private String processName;
    @Property
    private String processId;
    @Property
    private String stepName;
    @Property
    private String status;

    @Relationship(type="PARENT_CHILD", direction=Relationship.UNDIRECTED)
    private Set<ProcessStep> activities;

    @Relationship(type="CONDITION", direction=Relationship.UNDIRECTED)
    private Set<ProcessStep> conditionals;

    public ProcessStep() {}

    @Override
    public String toString() {
        final StringBuilder b = new StringBuilder("[a= ");
        b.append("id:" + id + ", ");
        b.append("domain:" + domain + ", ");
        b.append("process:" + processName + ", ");
        b.append("process-id:" + processId + ", ");
        b.append("step-name:" + stepName + ". \n ->{");
        if (activities!=null) {
            activities.stream().forEach(a -> b.append(a + "##"));
        }
        b.append("}]");
        return b.toString();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    public String getProcessName() {
        return processName;
    }

    public void setProcessName(String processName) {
        this.processName = processName;
    }

    public String getProcessId() {
        return processId;
    }

    public void setProcessId(String processId) {
        this.processId = processId;
    }

    public String getStepName() {
        return stepName;
    }

    public void setStepName(String stepName) {
        this.stepName = stepName;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Set<ProcessStep> getActivities() {
        return activities;
    }

    public void setActivities(Set<ProcessStep> activities) {
        this.activities = activities;
    }

    public Set<ProcessStep> getConditionals() { return conditionals;
    }

    public void setConditionals(final Set<ProcessStep> conditionals) { this.conditionals = conditionals;
    }
}
