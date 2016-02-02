package lu.cortex.domain.policy.model;

import java.time.Instant;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Id;
import javax.persistence.Version;
import org.apache.commons.lang3.StringUtils;

@Entity
@Table(name = "POLICY")
public class PolicyDefault {

    @Id
    @Column
    private Long identifier = 0l;

    @Column
    private String policyId = StringUtils.EMPTY;

    @Version
    private Instant version;

    //private

    /**
     * Default policy contstuctor.
     */
    public PolicyDefault() {

    }

}
