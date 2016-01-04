package lu.engine.core.model;

import java.util.Set;
import org.neo4j.ogm.annotation.GraphId;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Property;
import org.neo4j.ogm.annotation.Relationship;

@NodeEntity
public class ProcessStepDefinition {
    @GraphId
    Long id;
    @Property
    private String domain;
    @Property
    private String processName;
    @Property
    private String stepName;
    @Property
    private String version;
    @Relationship(type="CONFIG", direction=Relationship.UNDIRECTED)
    private Set<ProcessStepDefinition> steps;

    public ProcessStepDefinition() {}

    @Override
    public String toString() {
        final StringBuilder b = new StringBuilder("[def= ");
        b.append("id:" + id + ", ");
        b.append("domain:" + domain + ", ");
        b.append("process:" + processName + ", ");
        b.append("version:" + version + ", ");
        b.append("step-name:" + stepName + ". \n ->{");
        if (steps!=null) {
            steps.stream().forEach(d -> b.append(d + "##"));
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

    public String getStepName() {
        return stepName;
    }

    public void setStepName(String stepName) {
        this.stepName = stepName;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public Set<ProcessStepDefinition> getSteps() {
        return steps;
    }

    public void setSteps(Set<ProcessStepDefinition> steps) {
        this.steps = steps;
    }
}
