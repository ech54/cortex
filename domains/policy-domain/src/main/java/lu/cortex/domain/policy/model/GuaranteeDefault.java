package lu.cortex.domain.policy.model;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.Table;

import lu.cortex.domain.policy.api.Guarantee;
import lu.cortex.domain.policy.api.Risk;
import lu.cortex.model.AbstractDataModel;

@Entity
@Table(name = "GUARANTEE")
public class GuaranteeDefault  extends AbstractDataModel implements Guarantee {

    private List<Risk> guaranteeRisks = new ArrayList<>();

    public GuaranteeDefault() {}

    @Override
    public List<Risk> getGuaranteeRisks() {
        return guaranteeRisks;
    }

    @Override
    public void setGuaranteeRisks(List<Risk> guaranteeRisks) {
        this.guaranteeRisks = guaranteeRisks;
    }
}
