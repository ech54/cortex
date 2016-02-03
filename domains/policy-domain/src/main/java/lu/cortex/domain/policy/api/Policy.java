package lu.cortex.domain.policy.api;

import java.util.List;

import lu.cortex.model.BusinessCalendar;
import lu.cortex.model.DataModel;
import lu.cortex.model.Link;
import lu.cortex.model.Status;

public interface Policy extends DataModel {
    /**
     * Accessor in reading on the policy status.
     * @return The status.
     */
    Status getStatus();

    /**
     * Accessor in writing on the policy status.
     * @param status The status.
     */
    void setStatus(final Status status);

    /**
     * Accessor in reading on the business calendar.
     * @return The business calendar.
     */
    BusinessCalendar getCalendar();

    /**
     * Accessor in writing on the business calendar.
     * @param calendar The business calendar.
     */
    void setCalendar(final BusinessCalendar calendar);

    /**
     * Accessor in reading on the list of cover settings.
     * @return The cover settings.
     */
    List<GuaranteeSettings> getCoverSettings();

    /**
     * Accessor in writing on the list of cover settings.
     * @param coverSettings The cover settings.
     */
    void setCoverSettings(final List<GuaranteeSettings> coverSettings);

    List<Link> getPolicyValorizations();

    void setPolicyValorizations(final List<Link> valorizations);

}
