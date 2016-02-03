package lu.cortex.domain.policy.model;

import javax.persistence.Entity;
import javax.persistence.Table;

import lu.cortex.domain.policy.api.Risk;
import lu.cortex.model.AbstractDataModel;

@Entity
@Table(name = "RISK")
public class RiskDefault  extends AbstractDataModel implements Risk {
}
