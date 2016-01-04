package lu.wf.model;

import java.io.Serializable;

public class StepDefinition implements Serializable {
    private String name;
    private String type;
    private String delegatorAliasing;

    public StepDefinition() {

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDelegatorAliasing() {
        return delegatorAliasing;
    }

    public void setDelegatorAliasing(String delegatorAliasing) {
        this.delegatorAliasing = delegatorAliasing;
    }
}
