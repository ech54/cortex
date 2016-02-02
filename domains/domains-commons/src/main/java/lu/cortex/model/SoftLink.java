package lu.cortex.model;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EmbeddedId;
import org.apache.commons.lang3.StringUtils;

@Embeddable
public class SoftLink implements Link {

    @EmbeddedId
    private String businessId = StringUtils.EMPTY;

    @Column
    String domainName = StringUtils.EMPTY;

    public SoftLink() {}

    public String getBusinessId() {
        return businessId;
    }

    public void setBusinessId(String businessId) {
        this.businessId = businessId;
    }

    public String getDomainName() {
        return domainName;
    }

    public void setDomainName(String name) {
        this.domainName = name;
    }
}
