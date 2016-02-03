package lu.cortex.domain.policy.model;

import javax.persistence.Entity;
import javax.persistence.Table;

import lu.cortex.domain.policy.api.Guarantee;
import lu.cortex.model.AbstractDataModel;

@Entity
@Table(name="GUARANTEE_SETTINGS")
public class GuaranteeSettingsDefault  extends AbstractDataModel {

    Guarantee guarantee;

    public GuaranteeSettingsDefault(){

    }

    public Guarantee getGuarantee() {
        return guarantee;
    }

    public void setGuarantee(Guarantee guarantee) {
        this.guarantee = guarantee;
    }
}
