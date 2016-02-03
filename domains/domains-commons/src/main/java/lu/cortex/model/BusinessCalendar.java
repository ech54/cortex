package lu.cortex.model;

import java.time.LocalTime;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Embeddable
public class BusinessCalendar {

    @Column
    @Temporal(TemporalType.DATE)
    private LocalTime effectiveDate;

    @Column
    @Temporal(TemporalType.DATE)
    private LocalTime deletedDate;

    @Column
    @Temporal(TemporalType.DATE)
    private LocalTime cancelledDate;

    public BusinessCalendar() {
        //empty.
    }

    public LocalTime getEffectiveDate() {
        return effectiveDate;
    }

    public void setEffectiveDate(final LocalTime effectiveDate) {
        this.effectiveDate = effectiveDate;
    }

    public LocalTime getDeletedDate() {
        return deletedDate;
    }

    public void setDeletedDate(final LocalTime deletedDate) {
        this.deletedDate = deletedDate;
    }

    public LocalTime getCancelledDate() {
        return cancelledDate;
    }

    public void setCancelledDate(final LocalTime cancelledDate) {
        this.cancelledDate = cancelledDate;
    }
}

