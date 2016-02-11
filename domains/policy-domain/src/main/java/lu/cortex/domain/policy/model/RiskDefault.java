package lu.cortex.domain.policy.model;

import javax.persistence.Entity;
import javax.persistence.Table;
import org.apache.commons.lang3.StringUtils;

import lu.cortex.domain.policy.api.Risk;
import lu.cortex.domain.policy.api.RiskType;
import lu.cortex.model.AbstractDataModel;

@Entity
@Table(name = "RISK")
public class RiskDefault  extends AbstractDataModel implements Risk {

    private String name = StringUtils.EMPTY;

    private RiskType type = RiskType.NOT_DEFINED;

    private String description = StringUtils.EMPTY;

    public RiskDefault() {
        // empty.
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(final String name) {
        this.name = name;
    }

    @Override
    public RiskType getType() {
        return type;
    }

    @Override
    public void setType(final RiskType type) {
        this.type = type;
    }

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public void setDescription(final String description) {
        this.description = description;
    }
}
