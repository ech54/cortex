package lu.cortex.domain.policy.model;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Table;

import lu.cortex.domain.policy.api.GuaranteeSettings;
import lu.cortex.domain.policy.api.Policy;
import lu.cortex.model.AbstractDataModel;
import lu.cortex.model.BusinessCalendar;
import lu.cortex.model.Link;
import lu.cortex.model.Status;

@Entity
@Table(name = "POLICY")
public class PolicyDefault extends AbstractDataModel implements Policy {

    private Status status = Status.NEW;

    @Embedded
    private BusinessCalendar calendar = new BusinessCalendar();

    private List<GuaranteeSettings> coverSettings = new ArrayList<>();

    private List<Link> policyValorizations = new ArrayList<>();

    /**
     * Default policy constructor.
     */
    public PolicyDefault() {

    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(final Status status) {
        this.status = status;
    }

    public BusinessCalendar getCalendar() {
        return calendar;
    }

    public void setCalendar(final BusinessCalendar calendar) {
        this.calendar = calendar;
    }

    public List<GuaranteeSettings> getCoverSettings() {
        return coverSettings;
    }

    public void setCoverSettings(final List<GuaranteeSettings> coverSettings) {
        this.coverSettings = coverSettings;
    }

    public List<Link> getPolicyValorizations() {
        return policyValorizations;
    }

    public void setPolicyValorizations(final List<Link> valorizations) {
        this.policyValorizations = valorizations;
    }
}
