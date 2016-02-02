package lu.cortex.model;

import java.time.Instant;
import java.time.LocalDate;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;
import org.apache.commons.lang3.StringUtils;

public abstract class AbstractDataModel implements DataModel {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long identifier = -1l;

    @Column(name="BUSINESS_ID")
    private String businessReference = StringUtils.EMPTY;

    @Version
    @Temporal(TemporalType.TIMESTAMP)
    private Instant version;

    @Column
    @Temporal(TemporalType.DATE)
    private LocalDate creationDate;

    @Column
    @Temporal(TemporalType.DATE)
    private LocalDate modificationDate;

    public AbstractDataModel() {

    }

    @Override
    public boolean equals(final Object obj) {
        if (!(obj instanceof  DataModel)) {
            return false;
        }
        final DataModel model = (DataModel) obj;
        return this.getIdentifier().equals(model.getIdentifier());
    }

    @Override
    public int hashCode() {
        int result = 92938283;
        return 37 * result + identifier.hashCode();
    }

    @Override
    public Long getIdentifier() {
        return identifier;
    }

    @Override
    public void setIdentifier(final Long identifier) {
        this.identifier = identifier;
    }

    @Override
    public String getBusinessReference() {
        return businessReference;
    }

    @Override
    public void setBusinessReference(final String reference) {
        this.businessReference = reference;
    }

    @Override
    public Instant getVersion() {
        return version;
    }

    @Override
    public void setVersion(final Instant version) {
        this.version = version;
    }

    @Override
    public LocalDate getCreationDate() {
        return creationDate;
    }

    @Override
    public void setCreationDate(final LocalDate date) {
        this.creationDate = date;
    }

    public LocalDate getModificationDate() {
        return modificationDate;
    }

    @Override
    public void setModificationDate(final LocalDate date) {
        this.modificationDate = date;
    }
}
